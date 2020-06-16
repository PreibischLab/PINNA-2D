package com.preibisch.pinna2d.test;

import com.preibisch.pinna2d.tools.Log;
import com.preibisch.pinna2d.util.DEFAULTKt;
import ij.ImagePlus;
import ij.io.Opener;

public class ImpType {
    public static void main(String[] args) {

        final ImagePlus imp = new Opener().openImage(DEFAULTKt.getINPUT_PATH());
        final ImagePlus imp2 = new Opener().openImage(DEFAULTKt.getMASK2_PATH());
        Log.info(String.valueOf(imp.getType()));
        Log.info(imp.getFileInfo().toString());
        Log.info(String.valueOf(imp2.getType()));
        Log.info(imp2.getFileInfo().toString());
        final ImagePlus imp3 = new ImagePlus("float", imp.getProcessor().convertToFloatProcessor());

        Log.info(String.valueOf(imp3.getType()));
        Log.info(imp3.getFileInfo().toString());

    }
}
