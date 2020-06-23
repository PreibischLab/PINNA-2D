package com.preibisch.pinna2d.tools;

import com.preibisch.pinna2d.util.DEFAULTKt;
import com.preibisch.pinna2d.utils.ImpHelpers;
import com.preibisch.pinna2d.utils.Utils;
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
    private FloatType min;
    private FloatType max;
    private CompositeImage gui_image;
    private Img<FloatType> img, mask;


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
        int position = categoryChannel+1;
        Log.info("Position : "+position);
        Log.info( Utils.toString(gui_image.getDimensions()));
        gui_image.setChannelLut(lut, position);
        return ImpHelpers.toImage(gui_image);
    }

    private Imp(String imagePath, String maskPath, int mode) throws IOException {
        lut = LutLoader.openLut(DEFAULTKt.getLUT_PATH());

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

    public void set(float value) {
        int dims = img.numDimensions() - 1;
        IntervalView<FloatType> results = Views.hyperSlice(img, dims, clickViewChannel);
        setOnly(mask, results, value, CLICK_VALUE);
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
