package com.preibisch.pinna2d.tools;

import com.preibisch.pinna2d.utils.ImgHelpers;
import ij.CompositeImage;
import ij.ImageJ;

import java.io.File;
import java.io.IOException;

public class Imp {

    public static void main(String[] args) throws IOException {
        new ImageJ();
        final String FOLDER = "/Users/Marwan/Desktop/Irimia Project/Data/Example/";
        final String[] FILES = {"ND8_DIV0+4h_20x_noConfinment_7_ch_4.tif",
                "ND8_DIV0+4h_20x_noConfinment_7_ch_4_masks.tif"};
        File f1 = new File(FOLDER, FILES[0]);
        File f2 = new File(FOLDER, FILES[1]);

        if (!(f1.exists() && f2.exists()))
            throw new IOException("File not found");

        CompositeImage comp =  ImgHelpers.getComposite(f1, f2);
        comp.show();

    }
}
