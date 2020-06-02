package com.preibisch.pinna2d.tools;

import com.preibisch.pinna2d.util.DEFAULTKt;
import com.preibisch.pinna2d.utils.ImpHelpers;
import ij.CompositeImage;
import ij.ImageJ;
import javafx.scene.image.Image;
import net.imglib2.img.ImagePlusAdapter;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.view.IntervalView;
import net.imglib2.view.Views;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//< T extends RealType< T > & NativeType< T >>
public class Imp extends ImpHelpers {
    private List<Integer> category_vals;
    private final static int CLICK_VALUE = 255;
    public final static int WITH_CLICK = 2, WITHOUT_CLICK = 1;
    private static Imp instance;
    private final int mode;
    private final int clickViewChannel;
    private final int maskChannel;
    private final int categoryChannel;
    private UnsignedByteType min;
    private UnsignedByteType max;
    private CompositeImage gui_image;
    private Img<UnsignedByteType> img, original, mask, annotation;


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
        gui_image.updateAllChannelsAndDraw();
        return ImpHelpers.toImage(gui_image);
    }

    private Imp(String imagePath, String maskPath, int mode) throws IOException {
        category_vals = new ArrayList<>();
        int categories = DEFAULTKt.getCATEGORIES().size() - 1;
        int step = 255 / categories;
        for (int i = 0; i < categories; i++) {
            category_vals.add(step + step * i);
        }
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
        gui_image = ImpHelpers.getComposite(f1, f2, mode);

//        gui_image.setChannelLut(LUT.createLutFromColor(Color.CYAN),4);
        final int nchannels = gui_image.getNChannels();
        Log.info("Channels: " + nchannels);
        this.clickViewChannel = nchannels - 1;
        this.categoryChannel = nchannels - 2;
        this.maskChannel = nchannels - 3;
//        gui_image.setDisplayRange(0,100,7);
        img = ImagePlusAdapter.wrap(gui_image);

        this.min = img.firstElement().createVariable();
        this.max = img.firstElement().createVariable();
        computeMinMax(img, min, max);
        Log.info("Min : " + getMin() + "  Max: " + getMax());
        gui_image.draw();
    }

    public int getValue(int x, int y) {
        return getValue(img, x, y, maskChannel);
    }

    public void set(int value) {
        int dims = img.numDimensions() - 1;

        IntervalView<UnsignedByteType> masks = Views.hyperSlice(img, dims, maskChannel);
        IntervalView<UnsignedByteType> results = Views.hyperSlice(img, dims, clickViewChannel);
        setOnly(masks, results, value, CLICK_VALUE);
    }

    public void add(int value, int category) {
        int dims = img.numDimensions() - 1;
        IntervalView<UnsignedByteType> masks = Views.hyperSlice(img, dims, maskChannel);
        IntervalView<UnsignedByteType> results = Views.hyperSlice(img, dims, categoryChannel);
        add(masks, results, value, category_vals.get(category-1));
    }

    public static void main(String[] args) throws IOException {
        new ImageJ();
        final String FOLDER = "/Users/Marwan/Desktop/Irimia Project/Data/Example/";
        final String[] FILES = {"ND8_DIV0+4h_20x_noConfinment_7_ch_4.tif",
                "ND8_DIV0+4h_20x_noConfinment_7_ch_4_masks.tif"};
        File f1 = new File(FOLDER, FILES[0]);
        File f2 = new File(FOLDER, FILES[1]);

        if (!(f1.exists() && f2.exists()))
            throw new IOException("File not found");

        CompositeImage comp = ImpHelpers.getComposite(f1, f2, 2);

        comp.show();
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
