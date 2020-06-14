package com.preibisch.pinna2d.tools;

import com.preibisch.pinna2d.util.DEFAULTKt;
import com.preibisch.pinna2d.utils.ImpHelpers;
import ij.CompositeImage;
import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.LutLoader;
import ij.process.LUT;
import javafx.scene.image.Image;
import net.imglib2.img.ImagePlusAdapter;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.view.IntervalView;
import net.imglib2.view.Views;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

//< T extends RealType< T > & NativeType< T >>
public class Imp extends ImpHelpers {
    private final LUT lut;
    //    private List<Integer> category_vals;
    private final static int CLICK_VALUE = 255;
    public final static int WITH_CLICK = 2, WITHOUT_CLICK = 1;
    private static Imp instance;
    private final int mode;
    private final int clickViewChannel;
    //    private final int maskChannel;
    private final int categoryChannel;
    private UnsignedByteType min;
    private UnsignedByteType max;
    private CompositeImage gui_image;
    private Img<UnsignedByteType> img, mask;


    public static Imp get() throws Exception {
        if (instance == null)
            throw new Exception("Not initialized");
        return instance;
    }

    public static Imp init(String imagePath, String maskPath) throws IOException {
        return init(imagePath, maskPath, WITH_CLICK);
    }

    public static Imp init(String imagePath, String maskPath, int mode) throws IOException {
        instance = new Imp(imagePath, maskPath, mode);
        return instance;
    }

    public Image toImage() {
        gui_image.updateImage();
        gui_image.setChannelLut(lut, gui_image.getNChannels() - 1);
        return ImpHelpers.toImage(gui_image);
    }

    private Imp(String imagePath, String maskPath, int mode) throws IOException {
        lut = LutLoader.openLut(DEFAULTKt.getLUT_PATH());
        File f1 = new File(imagePath);
        File f2 = new File(maskPath);
        if (!(f1.exists() && f2.exists()))
            throw new IOException("File not found");

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
        final ImagePlus imp = new Opener().openImage(f1.getAbsolutePath());
        ImagePlus impMask = new Opener().openImage(f2.getAbsolutePath());
        Log.info(imp.getFileInfo().toString());
        Log.info(impMask.getFileInfo().toString());
        printInfos(impMask);
        printInfos(imp);
        mask = ImagePlusAdapter.wrap(impMask);

        int nChannels = imp.getNChannels();
        this.categoryChannel = nChannels++;
        this.clickViewChannel = nChannels++;
        Log.info("Channels: " + nChannels);
        gui_image = ImpHelpers.getComposite(imp, mode);

        img = ImagePlusAdapter.wrap(gui_image);

        this.min = img.firstElement().createVariable();
        this.max = img.firstElement().createVariable();
        computeMinMax(img, min, max);
        Log.info("Min : " + getMin() + "  Max: " + getMax());
        gui_image.draw();
    }

    public int getValue(int x, int y) {
        return getValue(mask, x, y);
    }

    public void set(int value) {
        int dims = img.numDimensions() - 1;
        IntervalView<UnsignedByteType> results = Views.hyperSlice(img, dims, clickViewChannel);
        setOnly(mask, results, value, CLICK_VALUE);
    }

    public void add(int value, int category) {
        int dims = img.numDimensions() - 1;
        IntervalView<UnsignedByteType> results = Views.hyperSlice(img, dims, categoryChannel);
        add(mask, results, value, category);
    }

    public int getMin() {
        return min.getInteger();
    }

    public int getMax() {
        return max.getInteger();
    }

    public void save(@NotNull File file) {
        int dims = img.numDimensions() - 1;
        IntervalView<UnsignedByteType> view = Views.hyperSlice(img, dims, categoryChannel);
        save(view, file);
    }
}
