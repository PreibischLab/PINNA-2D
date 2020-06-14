package com.preibisch.pinna2d.util

import com.preibisch.pinna2d.view.MainAnnotationView

val INPUT_PATH = MainAnnotationView::class.java.getResource("/img.tif").path
val MASK_PATH = MainAnnotationView::class.java.getResource("/mask.tif").path
val LUT_PATH = MainAnnotationView::class.java.getResource("/glasbey_inverted.lut").path

val CATEGORIES = listOf(0, 1, 2, 3)