package impl.image;

import api.image.ImageConverter;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;

public class ImageConverterImpl implements ImageConverter {

    private Function<Color, Integer> colorValueGetter;
    private Function<Integer, Color> colorValueSetter;
    private boolean valueIsPrivate = true;

    private int set8Bits(int value, int pos) {
        return value << (24 - pos);
    }

    private int get8Bits(int value, int pos) {
        return (value >> (24 - pos)) & 0xFF;
    }

    private int deconstructColor(Color pixel) {
        return set8Bits(pixel.getAlpha(), 0) | set8Bits(pixel.getRed(), 8) |
                set8Bits(pixel.getGreen(), 16) | set8Bits(pixel.getBlue(), 24);
    }

    private Color constructColor(int pixel) {
        return new Color(get8Bits(pixel, 8), get8Bits(pixel, 16), get8Bits(pixel, 24), get8Bits(pixel, 0));
    }

    /**
     * Works sorta like a one-time generated property with backing field
     * Gets a function that gets value field of a Color object.
     * If reflection access was OK - it's direct getting value of the private field of a given Color object
     * The fallback just does what's obvious approach - sets various bit parts of a number.
     */
    private Function<Color, Integer> getColorValueGetterFunction() {
        if (colorValueGetter == null) {
            try {
                var field = Color.class.getDeclaredField("value");

                if (valueIsPrivate) {
//                  Yeah, the fun part about java is that if you get the field, `setAccessible` will never throw
                    field.setAccessible(true);
                    valueIsPrivate = false;
                }

                colorValueGetter = (Color color) -> {
                    try {
                        return (int) field.get(color);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
//            Weird fallback, for some reason there's no way here to use it as a functor and apply like w'd really want.
                        return deconstructColor(color);
                    }
                };
            } catch (NoSuchFieldException e) {
                colorValueGetter = this::deconstructColor;
            }
        }
        return colorValueGetter;
    }

    /**
     * Works sorta like a one-time generated property with backing field
     * Gets a function that sets value field of a Color object.
     * If reflection access was OK - it's direct setting to the private field of a freshly created Color object
     * The fallback just does what's obvious approach - gets different bit parts and constructs proper object
     */
    private Function<Integer, Color> getColorValueSetterFunction() {
        if (colorValueSetter == null) {

            try {
                var field = Color.class.getDeclaredField("value");

                if (valueIsPrivate) {
//                  Yeah, the fun part about java is that if you get the field, `setAccessible` will never throw
                    field.setAccessible(true);
                    valueIsPrivate = false;
                }

                colorValueSetter = (Integer value) -> {
                    try {
                        var color = new Color(0);
                        field.set(color, value);
                        return color;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
//            Weird fallback, for some reason there's no way here to use it as a functor and apply like w'd really want.
                        return constructColor(value);
                    }
                };
            } catch (NoSuchFieldException e) {
                colorValueSetter = this::constructColor;
            }
        }
        return colorValueSetter;
    }


    private Integer[][] box2dArray(int[][] array) {
        return Arrays.stream(array).map(
                (line) -> Arrays.stream(line).boxed().toArray(Integer[]::new)
        ).toArray(Integer[][]::new);
    }


    private int[][] unbox2dArray(Integer[][] array) {
        return Arrays.stream(array).map(
                line -> Arrays.stream(line).mapToInt(Integer::intValue).toArray()
        ).toArray(int[][]::new);
    }

    @Override
    public Color[][] convertToColor(int[][] image) {
//        return new ArrayTransformer<Integer, Color>().transform(image, this::constructColor, Color.class);
//        DAMN, primitives can't be generic params! second bump =(
//        How do I squeeze damn ints into all the generic thing without second n^2 iteration...
//        screw this, I'm doing copy-paste... OR double iterating for boxing-unboxing... how do you...
//        That's why I don't like java that much... Too many inconveniences
        colorValueSetter = getColorValueSetterFunction();
        return new ArrayTransformer<Integer, Color>().transform(box2dArray(image), colorValueSetter, Color.class);
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        colorValueGetter = getColorValueGetterFunction();
        return unbox2dArray(new ArrayTransformer<Color, Integer>().transform(image, colorValueGetter, Integer.class));
    }

    /**
     * Transforms 2d array of type S to 2d array of type T
     * @param <S> Source type
     * @param <T> Target type
     */
    private static class ArrayTransformer<S, T> {

        /**
         * Transforms 2d array of type S to 2d array of type T using given converter function that is applied element-wise
         * @param source source 2d array of type S
         * @param converter converter function that is applied to each element
         * @param targetClass Class object of target type, needed to be able to access target ctor cause of type erasure.
         * @return converted 2d array of type T
         * @implNote probably not the best looking impl since I can imagine same s possible using streams but
         * I couldn't get that right...
         */
        @SuppressWarnings("unchecked")
        public T[][] transform(S[][] source, Function<S, T> converter, Class<T> targetClass) {
//            T[][] result = new T[source.length][]; //oh, yeah, type erasure, I can't do this...
//            Ok, there's sorta workaround we can pass target class obj...
//            And since we need nested Arrays... and Class<T[]> is not a thing.....
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
}
