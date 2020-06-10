package com.preibisch.pinna2d.test

import com.preibisch.pinna2d.util.INPUT_PATH
import ij.ImagePlus
import ij.io.Opener
import java.io.File

class BioImage(val path: File, val name:String){
    fun open() {
        return Opener().open(path.absolutePath)
    }
}

fun main(args: Array<String>) {
    Opener().openImage(INPUT_PATH).show()
}