package com.preibisch.pinna2d.util

import com.google.common.io.Resources
import com.preibisch.pinna2d.view.MainAnnotationView

//val LUT_PATH = MainAnnotationView::class.java.getResource("/glasbey_inverted.lut").path
var lut_path = ""
val CATEGORIES = listOf(0, 1, 2, 3)

val INPUT_FOLDER = ""
val MASKS_FOLDER = ""
var PROJECT_FOLDER = ""
//val INPUT_FOLDER = "/Users/Marwan/Desktop/Irimia Project/Data/raw_Tiff"
//val MASKS_FOLDER = "/Users/Marwan/Desktop/Irimia Project/Data/Mask/masks"
//var PROJECT_FOLDER = "/Users/Marwan/Desktop/Irimia Project/"
var CURRENT_IMAGE = ""