package com.preibisch.pinna2d.tools;

import com.preibisch.pinna2d.utils.ImgHelpers;
import ij.CompositeImage;
import ij.ImageJ;
import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.Concatenator;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

public class ImpHelper {


    public static CompositeImage getComposite(File original, File mask) {
//        final FloatType type = new FloatType();

        final ImagePlus impOriginal = new Opener().openImage(original.getAbsolutePath());
        final ImagePlus impMask = new Opener().openImage(mask.getAbsolutePath());
        ImgHelpers.printInfos(impOriginal);
        ImgHelpers.printInfos(impMask);

//		 impOriginal.show();
//		 impMask.show();
//		 impOriginal.getStack().addSlice(impMask.getStack().getProcessor(0));
//		 impOriginal.show();
        final ImagePlus all = Concatenator.run(impMask, impOriginal);
//        all.show();

        CompositeImage comp = new CompositeImage(all, CompositeImage.COMPOSITE);

//        comp.show();
//		all.setImage(comp.flatten());


//        System.out.println(all.isComposite());
//		final ImagePlus all =  new Concatenator().concatenate(impOriginal, impMask, true);
//		all.show();
//		final ImagePlus all = ImgHelpers.concat(impOriginal, impMask);

        return comp;
    }

    public static Image toImage(ImagePlus imp){
        Image fxImage = SwingFXUtils.toFXImage(imp.getBufferedImage(), null);
        return fxImage;
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

        CompositeImage comp =  getComposite(f1, f2);

    }
}
