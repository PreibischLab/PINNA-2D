package com.preibisch.pinna2d.tools;

import com.preibisch.pinna2d.utils.Utils;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;

public class CV2 {
    public static final int COLOR_IMAGE =  Imgcodecs.CV_LOAD_IMAGE_COLOR , GRAY_IMAGE =  Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE ;
    private static Mat gui_image,  original, mask, annotation;
    private static Core.MinMaxLocResult minMaxLoc ;


    static {
        loadOpenCVNativeLibrary();
        System.out.println("Welcome to OpenCV " + Core.VERSION);
    }
    public static void loadOpenCVNativeLibrary() {
        nu.pattern.OpenCV.loadShared();
    }



    public static Mat readImg(String path, int colorType){
        Mat img = Imgcodecs.imread(path, colorType);

//        minMaxLoc = Core.minMaxLoc(img);
//        Log.info("Min image: "+minMaxLoc.minVal+ " At " + minMaxLoc.minLoc.toString());
//        Log.info("Max image: "+minMaxLoc.maxVal+ " At " + minMaxLoc.maxLoc.toString());
//        Log.info("Loc image: "+img.channels());
//        annotation = img.clone();
        printImgInfos(img,path);
        return img;
    }

    private static void printImgInfos(Mat img, String path) {
        Log.info("Path: "+ path);
        Log.info("Rows: "+ img.rows());
        Log.info("Cols: "+ img.cols());
        Log.info("Channels: "+img.channels());
        Log.info("Type: "+img.type());
    }

    public static void init(String inputPath,String maskPath){
        original = readImg(inputPath,COLOR_IMAGE);
        mask = readImg(maskPath,GRAY_IMAGE);
//        gui_image = original;
        gui_image = overlap(original,mask);

//        image = new Image(maskPath);

    }

    private static Mat overlap(Mat original, Mat mask) {
        Mat destination = new Mat(original.rows(), original.cols(), original.type());
        Core.add(original,mask,destination);
        return original;
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
    public static float getValue(int x, int y) {
        double[] val = original.get(y, x);
        Log.info(Utils.toString(val));
        changeValueColor(val[0], Color.CYAN);
        return 0;
    }

    private static void changeValueColor(double v, Color cyan) {

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

    @NotNull
    public static Image getGUIImage() {
        Image image = toBufferedImage(gui_image);

//        convertedImage.createGraphics().drawRenderedImage(image, null);
//        Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
        return image;

    }
}
