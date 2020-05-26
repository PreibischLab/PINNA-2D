package com.preibisch.pinna2d.tools;

import com.preibisch.pinna2d.utils.CV2Helpers;
import com.preibisch.pinna2d.utils.Utils;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

public class CV2 {
    public static final int COLOR_IMAGE = Imgcodecs.CV_LOAD_IMAGE_COLOR, GRAY_IMAGE = Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
    private static Mat gui_image, original, mask, annotation;
    private static Core.MinMaxLocResult minMaxLoc;


    public static float getValue(int x, int y) {
        double[] val = original.get(y, x);
        Log.info(Utils.toString(val));
        CV2Helpers.changeValueColor(val[0], Color.CYAN);
        return 0;
    }

    public static void init(String inputPath, String maskPath) {
        original = CV2Helpers.readImg(inputPath, COLOR_IMAGE);
        mask = CV2Helpers.readImg(maskPath, GRAY_IMAGE);
        gui_image = CV2Helpers.overlap(original, mask);
    }

    @NotNull
    public static Image getGUIImage() {
        Image image = CV2Helpers.toBufferedImage(gui_image);
//        convertedImage.createGraphics().drawRenderedImage(image, null);
//        Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
        return image;

    }

    public static void main(String[] args) {
        Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
        System.out.println("OpenCV Mat: " + m);
        Mat mr1 = m.row(1);
        mr1.setTo(new Scalar(1));
        Mat mc5 = m.col(5);
        mc5.setTo(new Scalar(5));
        System.out.println("OpenCV Mat data:\n" + m.dump());
    }

}
