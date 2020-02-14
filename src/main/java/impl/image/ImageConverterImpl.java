package impl.image;

import api.image.ImageConverter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Function;

public class ImageConverterImpl implements ImageConverter {

    private Function<Color, Integer> colorValueGetter;


    private int set8Bits(int value, int pos) {
        return value << (24 - pos);
    }

    private int getBits(int value, int pos) {
        return (value >> (24 - pos)) & 0xFF;
    }

    private int deconstructColor(Color pixel) {
        return set8Bits(pixel.getAlpha(), 0) | set8Bits(pixel.getRed(), 8) |
                set8Bits(pixel.getGreen(), 16) | set8Bits(pixel.getBlue(), 24);
    }

    private Color constructColor(int pixel) {
        return new Color(getBits(pixel, 8), getBits(pixel, 16), getBits(pixel, 24), getBits(pixel, 0));
    }

    @NotNull
    private Function<Color, Integer> getColorValueGetterFunction() {
        Function<Color, Integer> colorValueGetter;
        try {
            Field privagteValueField = Color.class.getDeclaredField("value");
            privagteValueField.setAccessible(true);
            colorValueGetter = (Color color) -> {
                try {
                    return (int) privagteValueField.get(color);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return 0;
                }
            };
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
//            Yeah yeah. I can do bit stuff, bit that's no fun
            colorValueGetter = this::deconstructColor;
        }
        return colorValueGetter;
    }

    private static class ArrayTransformer<S, T> {
        public T[][] transform(S[][] source, Function<S, T> converter, Class<T> targetClass) {
//            T[][] result = new T[source.length][]; //oh, yeah, type erasure, I can't do this...
//            Ok, there's sorta workaround we can pass target class obj...
            Class targetArrayClass = Array.newInstance(targetClass, 0).getClass();
            T[][] result = (T[][]) Array.newInstance(targetArrayClass, source.length);
            int rowIndex = 0;
            for (var row : source) {
                result[rowIndex] = (T[]) Array.newInstance(targetClass, row.length);
                int colIndex = 0;
                for (var item : row) {
                    result[rowIndex][colIndex] = converter.apply(item);
                    colIndex += 1;
                }
                rowIndex += 1;
            }
            return result;
        }
    }

    private Integer[][] box2dArray(int[][] arr) {
        return Arrays.stream(arr).map(
                (line) -> Arrays.stream(line).boxed().toArray(Integer[]::new)
        ).toArray(Integer[][]::new);
    }


    private int[][] unbox2dArray(Integer[][] arr) {
        return Arrays.stream(arr).map(
                line -> Arrays.stream(line).mapToInt(Integer::intValue).toArray()
        ).toArray(int[][]::new);
    }

    @Override
    public Color[][] convertToColor(int[][] image) {
//        return new ArrayTransformer<Integer, Color>().transform(image, this::constructColor, Color.class);
//        DAMN, primitives can't be generic params! second bump =(
//        How do I squeeze damn ints into all the generic thing without second n^2 iteration...
//        screw this, I'm doing copy-paste... OR double iterating for boxing-unboxing... how do you...
        return new ArrayTransformer<Integer, Color>().transform(box2dArray(image), this::constructColor, Color.class);
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        if (colorValueGetter == null)
            colorValueGetter = getColorValueGetterFunction();
        return unbox2dArray(new ArrayTransformer<Color, Integer>().transform(image, colorValueGetter, Integer.class));
    }
}
