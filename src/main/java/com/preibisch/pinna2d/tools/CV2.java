package com.preibisch.pinna2d.tools;

import javafx.scene.image.Image;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayInputStream;

public class CV2 {
    public static Mat img;
    static {
        loadOpenCVNativeLibrary();
        System.out.println("Welcome to OpenCV " + Core.VERSION);
    }
    public static void loadOpenCVNativeLibrary() {
        nu.pattern.OpenCV.loadShared();
    }

//    Mat >> BufferedImage >> Image >> ImageView
//    Assuming you know how to do the 1st conversion, the rest would be something like this:
//import javafx.embed.swing.SwingFXUtils;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//Image image = SwingFXUtils.toFXImage(bufImage, null);

    public static Image matToImg(Mat mat){
        MatOfByte byteMat = new MatOfByte();
        Imgcodecs.imencode(".bmp", mat, byteMat);
        return new Image(new ByteArrayInputStream(byteMat.toArray()));
    }

    public static Mat readImg(String path){
        img = Imgcodecs.imread(path);
        return img;
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
