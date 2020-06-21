package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Predicate;

public class FollowersStatsImpl implements FollowersStats {

    private final SocialNetwork network;

    public FollowersStatsImpl(SocialNetwork network) {
        this.network = network;
    }

    @Override
    public Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate) {
        return computeFollowersCount(id, depth, predicate, new HashSet<Integer>());
    }

    private CompletableFuture<Integer> computeFollowersCount(int id, int depth,
                                                             Predicate<UserInfo> predicate,
                                                             Set<Integer> visitedFollowers) {
        /*
        Задача заключается в том, чтобы посчитать общее количество подписчиков до заданной глубины дерева,
        которые удовлетворяют определенному условию. Поэтому каждого пользователя можно охарактеризовать целым числом,
        представляющем собой сумму из количества подписчиков пользователя, удовлетворяющих данному условию и значением
        функции условия для самого пользователя: если пользователь удовлетворяет функции, то это значение равно 1, если
        не удовлетворяет, то 0.

        Поэтому сначала вычисляем значение функции условия для самого пользователя. Если мы уже достигли максимальной
        глубины обхода дерева, то возвращаем только это значение, а если глубина еще неисчерпана, то запускаем рекурсивный
        алгоритм подсчета подписчиков, удовлетворяющих условию.

        Чтобы не посещать одного и того же пользователя по несколько раз, используем Set для проверки посещения.
        */


        if (!visitedFollowers.add(id)) {
            return CompletableFuture.completedFuture(0);
        }

        CompletableFuture<Integer> currentUserFuture = network.getUserInfo(id)
                .thenApply(userInfo -> predicate.test(userInfo) ? 1 : 0);

        if (depth == 0) {
            return currentUserFuture;
        }

        CompletableFuture<Integer> followersCountFuture = network.getFollowers(id)
                .thenCompose(followers -> followers.stream()
                        .map(followerId -> computeFollowersCount(followerId, depth - 1, predicate, visitedFollowers))
                        .reduce((left, right) -> left.thenCombine(right, Integer::sum))
                        .orElse(CompletableFuture.completedFuture(0)));

        return currentUserFuture.thenCombine(followersCountFuture, Integer::sum);
    }

}
