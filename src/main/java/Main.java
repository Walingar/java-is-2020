import impl.image.ImageUtil;
import impl.image.KernelConverter;

import java.awt.*;

public class Main {

    private static final double[][] IDENTITY_KERNEL = {
            {0.0,0.0,0.0},
            {0.0,1.0,0.0},
            {0.0,0.0,0.0},
    };

    private static final double[][] BLUR_KERNEL = {
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
    };

    public static void main(String[] args) {
        var kv = new KernelConverter();
        Color[][] image = ImageUtil.readOriginImage("pic3.png");
        Color[][] dfd = kv.apply(image,IDENTITY_KERNEL);
        ImageUtil.writeOutputImage("pic3.png",dfd);
    }
}
