package com.preibisch.pinna2d.util

import com.preibisch.pinna2d.view.MainAnnotationView

val TEST_IMAGE = MainAnnotationView::class.java.getResource("/cat.jpg").path
val INPUT_PATH = MainAnnotationView::class.java.getResource("/img.tif").path
val MASK_PATH = MainAnnotationView::class.java.getResource("/mask.tif").path
val MASK2_PATH = MainAnnotationView::class.java.getResource("/mask2.tif").path
val LUT_PATH = MainAnnotationView::class.java.getResource("/glasbey_inverted.lut").path

val CATEGORIES = listOf(0, 1, 2, 3)

val INPUT_FOLDER = "/Users/Marwan/Desktop/Irimia Project/Data/raw_Tiff"
val MASKS_FOLDER = "/Users/Marwan/Desktop/Irimia Project/Data/Mask/masks"
var PROJECT_FOLDER = "/Users/Marwan/Desktop/Irimia Project/"
var CURRENT_IMAGE = ""