package com.preibisch.pinna2d.test;

import com.preibisch.pinna2d.util.DEFAULTKt;
import ij.ImageJ;
import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.LutLoader;
import ij.process.LUT;

public class LutMask {
    public static void main(final String[] args) {
        new ImageJ();
        final ImagePlus imp = new Opener().openImage(DEFAULTKt.getMASK_PATH());
        LUT lut = LutLoader.openLut(DEFAULTKt.getLUT_PATH());
        imp.setLut(lut);
        imp.show();
    }



}
