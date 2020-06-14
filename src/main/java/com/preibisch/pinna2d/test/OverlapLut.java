package com.preibisch.pinna2d.test;

import com.preibisch.pinna2d.tools.Log;
import com.preibisch.pinna2d.util.ColorUtilsKt;
import com.preibisch.pinna2d.util.DEFAULTKt;
import com.preibisch.pinna2d.utils.ImpHelpers;
import ij.CompositeImage;
import ij.ImageJ;
import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.ChannelSplitter;
import ij.plugin.Colors;
import ij.plugin.LutLoader;
import ij.process.LUT;

import java.awt.*;

public class OverlapLut {
    public static void main(String[] args) {
        new ImageJ();
        final ImagePlus imp = new Opener().openImage(DEFAULTKt.getINPUT_PATH());
        final ImagePlus impMask = new Opener().openImage(DEFAULTKt.getMASK_PATH());

        final LUT lut = LutLoader.openLut(DEFAULTKt.getLUT_PATH());
        ImpHelpers.printInfos(imp);
        ImpHelpers.printInfos(impMask);


        imp.getStack().addSlice(impMask.getProcessor());
        imp.setDimensions(imp.getStackSize(), 1, 1);
//        ImagePlus imp2 = RGBStackMerge.mergeChannels(listOfChannels(imp,impMask), false);
//        imp2.show();
//        imp.updateImage();

        // IMP Convert to float

//        Img<ARGBType> img = ImageJFunctions.wrapRGBA(imp);
//        ImageJFunctions.show(img);
//        imp.show();
        //Code from Stephan
//

        CompositeImage comp = new CompositeImage(imp,CompositeImage.COMPOSITE);
        comp.setChannelLut(lut,5);
        Log.info(String.valueOf(comp.getNChannels()));
        comp.show();
//        CompositeImage comp = new CompositeImage(imp, CompositeImage.COLOR_RGB);
//
//        comp.show();


    }

    private static ImagePlus[] listOfChannels(ImagePlus imp, ImagePlus impMask) {
        final LUT lut = LutLoader.openLut(DEFAULTKt.getLUT_PATH());
        ImagePlus[] channels1 = ChannelSplitter.split(imp);
        ImagePlus[] channels2 = ChannelSplitter.split(impMask);
//        for (ImagePlus ch : channels1) {
//            ch.getProcessor().setColor(Colors.getColor(ColorUtilsKt.randomColor(), Color.BLUE));
////            ch.updateImage();
////            comp.getStack().getProcessor(i + 1).setMinAndMax(10, 100);
//        }
//        impMask.setLut(lut);
//        imp.setLut(lut);
        ImagePlus[] all = new ImagePlus[channels1.length+channels2.length];
        int i = 0;
        while(i<channels1.length){
            all[i]= channels1[i];
//            all[i].setLut(lut);
            i++;
        }
        while(i<(channels1.length+channels2.length)){
            all[i]= channels2[i-channels1.length];

            all[i].getProcessor().setColor(Colors.getColor(ColorUtilsKt.randomColor(), Color.BLUE));
            i++;
        }

//        for (ImagePlus ch : channels) {
//            ch.getProcessor().setColor(Colors.getColor(ColorUtilsKt.randomColor(), Color.BLUE));
//            ch.updateImage();
////            comp.getStack().getProcessor(i + 1).setMinAndMax(10, 100);
//        }
        return all;
    }
}
