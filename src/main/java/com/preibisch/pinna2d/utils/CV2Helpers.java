package com.preibisch.pinna2d.utils;

import com.preibisch.pinna2d.tools.Log;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;

public class CV2Helpers {

    static {
        loadOpenCVNativeLibrary();
        System.out.println("Welcome to OpenCV " + Core.VERSION);
    }
    public static void loadOpenCVNativeLibrary() {
        nu.pattern.OpenCV.loadShared();
    }

    public static void changeValueColor(double v, Color cyan) {

//        Core.inRange(, new Scalar(0,0,0), new Scalar(0,0,255), dst);
    }
    public static Image toBufferedImage(Mat m){
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( m.channels() > 1 ) {
            Log.info("Multi channel buffered: "+m.channels());
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte [] b = new byte[bufferSize];
        m.get(0,0,b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);

        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        Image fxImage = SwingFXUtils.toFXImage(image, null);
        return fxImage;
    }

    public static Image matToImg(Mat mat){
        MatOfByte byteMat = new MatOfByte();
        Imgcodecs.imencode(".bmp", mat, byteMat);
        return new Image(new ByteArrayInputStream(byteMat.toArray()));
    }

    private static void printImgInfos(Mat img, String path) {
        Log.info("Path: "+ path);
        Log.info("Rows: "+ img.rows());
        Log.info("Cols: "+ img.cols());
        Log.info("Channels: "+img.channels());
        Log.info("Type: "+img.type());
    }

    public static Mat readImg(String path, int colorType){
        Mat img = Imgcodecs.imread(path, colorType);
        printImgInfos(img,path);
        return img;
    }

    public static Mat overlap(Mat original, Mat mask) {
        Mat destination = new Mat(original.rows(), original.cols(), original.type());
        Core.add(original,mask,destination);
        return original;
    }

}
