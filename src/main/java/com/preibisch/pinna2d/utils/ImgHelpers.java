package com.preibisch.pinna2d.utils;

import ij.CompositeImage;
import ij.ImagePlus;
import ij.io.Opener;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.imglib2.Cursor;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.Views;

import java.io.File;

public class ImgHelpers {

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
        System.out.println("Dims: " +img.numDimensions() + "-" + string);

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
        System.out.println("Stack: "+ imp.getStack().size());
        System.out.println("Channel: "+ imp.getChannel());
        ImgHelpers.printDims(imp);
        System.out.println("Type: "+ imp.getType());
    }

    public static CompositeImage getComposite(File original, File mask) {

        final ImagePlus impOriginal = new Opener().openImage(original.getAbsolutePath());
        final ImagePlus impMask = new Opener().openImage(mask.getAbsolutePath());
        ImgHelpers.printInfos(impOriginal);
        ImgHelpers.printInfos(impMask);

        impOriginal.getStack().addSlice(impMask.getProcessor());

        CompositeImage comp = new CompositeImage(impOriginal, CompositeImage.COMPOSITE);
        return comp;
    }

    public static Image toImage(ImagePlus imp){
        Image fxImage = SwingFXUtils.toFXImage(imp.getBufferedImage(), null);
        return fxImage;
    }
}
