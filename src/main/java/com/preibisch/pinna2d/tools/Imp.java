package com.preibisch.pinna2d.tools;

import com.preibisch.pinna2d.utils.ImpHelpers;
import ij.CompositeImage;
import ij.ImagePlus;
import ij.plugin.LutLoader;
import ij.process.LUT;
import javafx.scene.image.Image;
import net.imglib2.img.ImagePlusAdapter;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.IntervalView;
import net.imglib2.view.Views;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//< T extends RealType< T > & NativeType< T >>
public class Imp extends ImpHelpers {
    private LUT lut;
    //    private List<Integer> category_vals;
    private final static int CLICK_VALUE = 255;
    public final static int WITH_CLICK = 2, WITHOUT_CLICK = 1;
    private static Imp instance;
    private final int mode;
    private final int clickViewChannel;
    //    private final int maskChannel;
    private final int categoryChannel;
    private FloatType min;
    private FloatType max;
    private CompositeImage gui_image;
    private Img<FloatType> img, mask;


    public static Imp get() throws Exception {
        if (instance == null)
            throw new Exception("Not initialized");
        return instance;
    }

    public static Imp init(String imagePath, String maskPath,String lutPath) throws IOException {
        return init(imagePath, maskPath, lutPath, WITH_CLICK);
    }

    public static Imp init(String imagePath, String maskPath, String lutPath, int mode) throws IOException {
        instance = new Imp(imagePath, maskPath,lutPath, mode);
        return instance;
    }

    public Image toImage(Point point) {
        gui_image.updateImage();
        int position = categoryChannel+1;
        if(lut!=null)
            gui_image.setChannelLut(lut, position);
        BufferedImage buff = gui_image.getBufferedImage();
        Rectangle rect = getRectangle(point,30,new Point(buff.getWidth(),buff.getHeight()));
        Log.info("rectangle: " +rect.toString());
        addRectangle(buff,rect);
        Image fxImage = ImpHelpers.toImage(buff);
        return fxImage;
    }

    public Image toImage() {
        gui_image.updateImage();
        int position = categoryChannel+1;
        if(lut!=null)
            gui_image.setChannelLut(lut, position);
        BufferedImage buff = gui_image.getBufferedImage();
        Image fxImage = ImpHelpers.toImage(buff);
        return fxImage;
    }

    private void addRectangle(BufferedImage buff, Rectangle rectangle) {
        Graphics2D graph = buff.createGraphics();
        graph.setColor(Color.RED);
        graph.draw(rectangle);
//        graph.fill(rectangle);
        graph.dispose();
    }

    private Rectangle getRectangle(Point point, int dim, Point maxPoint) {
        int x1 = Math.max(point.x-dim,0);
        int y1 = Math.max(point.y-dim,0);

        int x2 = Math.min(point.x+dim,maxPoint.x-1);
        int y2 = Math.min(point.y+dim,maxPoint.y-1);
        return  new Rectangle(x1,y1,x2-x1,y2-y1);
    }

    private Imp(String imagePath, String maskPath,String lutPath, int mode) throws IOException {
//        String p = Imp.class.getClassLoader().getResource("glasbey_inverted.lut").getPath();
        File LUT_PATH = new File(lutPath);
        Log.info("Lut path: "+LUT_PATH.getAbsolutePath());
        if (LUT_PATH.exists())
        lut = LutLoader.openLut(LUT_PATH.getAbsolutePath());
        else
            Log.error("Lut not found !");

        switch (mode) {
            case WITH_CLICK:
                Log.info("Extra layer for click");
                break;
            case WITHOUT_CLICK:
                Log.info("No layer for click");
                break;
            default:
                throw new IOException("Invalid mode :" + mode);
        }
        this.mode = mode;
        final ImagePlus imp = openImp(new File(imagePath));
        printInfos(imp);
        ImagePlus impMask = openImp(new File(maskPath));
        printInfos(impMask);
        mask = ImagePlusAdapter.wrap(impMask);

        int nChannels = imp.getNChannels();
        this.categoryChannel = nChannels++;
        this.clickViewChannel = nChannels++;
        Log.info("Channels: " + nChannels);
//        imp.getStack().addSlice(impMask.getProcessor());
        gui_image = ImpHelpers.getComposite(imp, mode);

        img = ImagePlusAdapter.wrap(gui_image);

        this.min = img.firstElement().createVariable();
        this.max = img.firstElement().createVariable();
        computeMinMax(mask, min, max);
        Log.info("Min : " + getMin() + "  Max: " + getMax());
        gui_image.draw();
    }

    public float getValue(int x, int y) {
        return getValue(mask, x, y);
    }

    public Point set(float value) {
        int dims = img.numDimensions() - 1;
        IntervalView<FloatType> results = Views.hyperSlice(img, dims, clickViewChannel);
        return setOnly(mask, results, value, CLICK_VALUE);
    }

    public long add(float value, int category) {
        int dims = img.numDimensions() - 1;
        IntervalView<FloatType> results = Views.hyperSlice(img, dims, categoryChannel);
        Log.info(String.format("Set category %d to instance %.2f",category,value));
        return add(mask, results, value, category+2);
    }

    public float getMin() {
        return min.get();
    }

    public float getMax() {
        return max.get();
    }

    public boolean save(@NotNull File file) {
        int dims = img.numDimensions() - 1;
        IntervalView<FloatType> view = Views.hyperSlice(img, dims, categoryChannel);
       return save(view, file);
    }
}
