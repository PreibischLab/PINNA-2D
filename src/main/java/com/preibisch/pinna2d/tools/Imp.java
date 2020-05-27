package com.preibisch.pinna2d.tools;

import com.preibisch.pinna2d.utils.ImpHelpers;
import ij.CompositeImage;
import ij.ImageJ;
import javafx.scene.image.Image;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.img.ImagePlusAdapter;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.view.IntervalView;
import net.imglib2.view.Views;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.IOException;

//< T extends RealType< T > & NativeType< T >>
public class Imp {
    private static Imp instance;
    public final int COLOR_IMAGE = Imgcodecs.CV_LOAD_IMAGE_COLOR, GRAY_IMAGE = Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
    private CompositeImage gui_image;
    private Img<UnsignedByteType> img, original, mask, annotation;

    public static Imp get() throws Exception {
        if (instance == null)
            throw new Exception("Not initialized");
        return instance;
    }

    public static Imp init(String imagePath, String maskPath) throws IOException {
        instance = new Imp(imagePath, maskPath);
        return instance;
    }

    public Image toImage() {

       gui_image.updateImage();
       gui_image.updateAllChannelsAndDraw();
        return ImpHelpers.toImage(gui_image);
    }

    private Imp(String imagePath, String maskPath) throws IOException {
        File f1 = new File(imagePath);
        File f2 = new File(maskPath);

        if (!(f1.exists() && f2.exists()))
            throw new IOException("File not found");

        gui_image = ImpHelpers.getComposite(f1, f2,true);
        img = ImagePlusAdapter.wrap(gui_image);
        gui_image.draw();
    }

    public int getValue(int x, int y) {
        int maskPosition = gui_image.getNChannels() - 2;
        RandomAccess<UnsignedByteType> cursor = img.randomAccess();
        cursor.setPosition(x, 0);
        cursor.setPosition(y, 1);
        cursor.setPosition(maskPosition, 2);
        UnsignedByteType val = cursor.get();

        return val.getInteger();
    }

    public void set(int value, int category){
        int maskPosition = gui_image.getNChannels() - 2;

        int categoriesPosition = gui_image.getNChannels() - 1;
        int dims = img.numDimensions()-1;
        IntervalView<UnsignedByteType> masks = Views.hyperSlice(img, dims, maskPosition);
        IntervalView<UnsignedByteType> results = Views.hyperSlice(img, dims, categoriesPosition);

        // create a cursor for both images
        Cursor< UnsignedByteType > cursorInput = masks.cursor();

        RandomAccess< UnsignedByteType > randomAccess = results.randomAccess();

        // iterate over the input
        while ( cursorInput.hasNext())
        {
            // move both cursors forward by one pixel
            cursorInput.fwd();
            if(cursorInput.get().getInteger() == value){
                randomAccess.setPosition( cursorInput );
                randomAccess.get().set(category);
            }
        }
//        gui_image.updateImage();
//        gui_image.repaintWindow();
//        gui_image.updateAllChannelsAndDraw();
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

        CompositeImage comp = ImpHelpers.getComposite(f1, f2, true);

        comp.show();

    }
}
