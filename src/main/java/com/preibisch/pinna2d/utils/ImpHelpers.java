package com.preibisch.pinna2d.utils;

import com.preibisch.pinna2d.tools.Log;
import ij.CompositeImage;
import ij.ImagePlus;
import ij.VirtualStack;
import ij.io.FileInfo;
import ij.io.FileSaver;
import ij.io.Opener;
import ij.io.TiffEncoder;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.imglib2.Cursor;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.ImagePlusAdapter;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.Type;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.IntervalView;
import net.imglib2.view.Views;

import java.io.*;
import java.util.Iterator;

public class ImpHelpers {

    public static void copy(final RandomAccessibleInterval<FloatType> source,
                            final RandomAccessibleInterval<FloatType> target) {
        final RandomAccess<FloatType> in = source.randomAccess();
        IterableInterval<FloatType> iterableTarget = Views.iterable(target);
        final Cursor<FloatType> out = iterableTarget.cursor();
        while (out.hasNext()) {
            out.fwd();
            in.setPosition(out);
            out.get().set(in.get());
        }
    }

    public static RandomAccessibleInterval<FloatType> concat(final RandomAccessibleInterval<FloatType> source1,
                                                             final RandomAccessibleInterval<FloatType> source2) {
        final FloatType type = new FloatType();
        final ArrayImgFactory<FloatType> factory = new ArrayImgFactory<FloatType>();
        final int n1 = getNChannels(source1);
        final int n2 = getNChannels(source2);
        final long[] dim = new long[3];
        for (int d = 0; d < 2; ++d)
            dim[d] = source1.dimension(d);
        dim[2] = n1 + n2;
        final RandomAccessibleInterval<FloatType> all = factory.create(dim, type);
        if (n1 == 1)
            copy(source1, Views.hyperSlice(all, 2, 0));
        else
            for (int i = 0; i < n1; i++) {
                copy(Views.hyperSlice(source1, 2, i), Views.hyperSlice(all, 2, i));
            }
        if (n2 == 1)
            copy(source2, Views.hyperSlice(all, 2, n1));
        else
            for (int i = 0; i < n2; i++) {
                copy(Views.hyperSlice(source2, 2, i), Views.hyperSlice(all, 2, n1 + i));
            }
        return all;
    }

    public static int getNChannels(RandomAccessibleInterval<FloatType> source) {
        if (source.numDimensions() == 2)
            return 1;
        else
            return (int) (source.dimension(2));
    }

    public static <T> long[] getDims(RandomAccessibleInterval<T> img) {
        long dims[] = new long[img.numDimensions()];
        for (int i = 0; i < img.numDimensions(); i++) {
            dims[i] = img.dimension(i);
        }
        return dims;
    }

    public static String toString(long[] dims) {
        String result = "(";
        for (int i = 0; i < dims.length; i++) {
            result += String.valueOf(dims[i]);
            if (i < dims.length - 1) {
                result += "-";
            }
        }
        result += ")";
        return result;
    }

    public static <T> void printDims(RandomAccessibleInterval<T> img) {
        long[] dims = getDims(img);
        String string = toString(dims);
        System.out.println("Dims: " + img.numDimensions() + "-" + string);

    }

    public static ImagePlus concat(ImagePlus imp1, ImagePlus imp2) {
        Img<FloatType> img1 = ImageJFunctions.wrap(imp1);
        Img<FloatType> img2 = ImageJFunctions.wrap(imp2);
        RandomAccessibleInterval<FloatType> imgAll = concat(img1, img2);
        return ImageJFunctions.wrap(imgAll, "");
    }

    public static void printDims(ImagePlus imp) {
        Img<FloatType> img = ImageJFunctions.wrap(imp);
        printDims(img);
    }

    public static void printInfos(ImagePlus imp) {
        System.out.println("Stack: " + imp.getStack().size());
        System.out.println("Channel: " + imp.getChannel());
        printDims(imp);
        System.out.println("Type: " + imp.getType());
    }

    public static <T extends Type<T>> CompositeImage getComposite(File original, File mask) {
        return getComposite(original, mask, 0);
    }

    public static <T extends Type<T>> CompositeImage getComposite(File original, File mask, int extras) {

        final ImagePlus impOriginal = new Opener().openImage(original.getAbsolutePath());
        final ImagePlus impMask = new Opener().openImage(mask.getAbsolutePath());
        printInfos(impOriginal);
        printInfos(impMask);

        impOriginal.getStack().addSlice(impMask.getProcessor());

        if (extras > 0) {
            Img<UnsignedByteType> input = ImagePlusAdapter.wrap(impMask);
            Img<UnsignedByteType> output = input.factory().create(input);
            for (int i = 0; i < extras; i++)
                impOriginal.getStack().addSlice(ImageJFunctions.wrap(output.copy(), String.valueOf(i)).getProcessor());
        }

        CompositeImage comp = new CompositeImage(impOriginal, CompositeImage.COMPOSITE);
        return comp;
    }


    public static Image toImage(ImagePlus imp) {
        Image fxImage = SwingFXUtils.toFXImage(imp.getBufferedImage(), null);
        return fxImage;
    }

    public static int getValue(Img<UnsignedByteType> img, int x, int y, int channel) {
        RandomAccess<UnsignedByteType> cursor = img.randomAccess();
        cursor.setPosition(x, 0);
        cursor.setPosition(y, 1);
        cursor.setPosition(channel, 2);
        UnsignedByteType val = cursor.get();
        return val.getInteger();
    }

    public static void add(IntervalView<UnsignedByteType> masks, IntervalView<UnsignedByteType> result, int value, int category) {
        Cursor<UnsignedByteType> cursorInput = masks.cursor();

        RandomAccess<UnsignedByteType> randomAccess = result.randomAccess();

        while (cursorInput.hasNext()) {
            cursorInput.fwd();
            if (cursorInput.get().getInteger() == value) {
                randomAccess.setPosition(cursorInput);
                randomAccess.get().set(category);
            }
        }
    }


    public static void setOnly(IntervalView<UnsignedByteType> masks, IntervalView<UnsignedByteType> result, int value, int setValue) {
        Cursor<UnsignedByteType> cursorInput = masks.cursor();
        RandomAccess<UnsignedByteType> randomAccess = result.randomAccess();
        while (cursorInput.hasNext()) {
            cursorInput.fwd();
            randomAccess.setPosition(cursorInput);
            if (cursorInput.get().getInteger() == value)
                randomAccess.get().set(setValue);
            else
                randomAccess.get().set(0);
        }
    }

    public static <T extends Comparable<T> & Type<T>> void computeMinMax(
            final Iterable<T> input, final T min, final T max) {
        // create a cursor for the image (the order does not matter)
        final Iterator<T> iterator = input.iterator();

        // initialize min and max with the first image value
        T type = iterator.next();

        min.set(type);
        max.set(type);

        // loop over the rest of the data and determine min and max value
        while (iterator.hasNext()) {
            // we need this type more than once
            type = iterator.next();

            if (type.compareTo(min) < 0)
                min.set(type);

            if (type.compareTo(max) > 0)
                max.set(type);
        }
    }

    public static void save(IntervalView<UnsignedByteType> view, File file) {
        ImagePlus imp = ImageJFunctions.wrap(view, "Categories");
        imp.show();
        saveTiffStack(imp,file);

    }

    /*
     * Reimplementation from ImageJ FileSaver class. Necessary since it traverses
     * the entire virtual stack once to collect some slice labels, which takes
     * forever in this case.
     */
    public static boolean saveTiffStack(final ImagePlus imp, final File f) {
        FileInfo fi = imp.getFileInfo();
        boolean virtualStack = imp.getStack().isVirtual();
        if (virtualStack)
            fi.virtualStack = (VirtualStack) imp.getStack();
        fi.info = imp.getInfoProperty();
        fi.description = new FileSaver(imp).getDescriptionString();
        DataOutputStream out = null;
        try {
            TiffEncoder file = new TiffEncoder(fi);
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
            file.write(out);
            out.close();
        } catch (IOException e) {
            Log.error(": ERROR: Cannot save file '" + f.getAbsolutePath() + "':" + e);
            return false;
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                    Log.error(e.toString());
                }
        }
        return true;
    }
}
