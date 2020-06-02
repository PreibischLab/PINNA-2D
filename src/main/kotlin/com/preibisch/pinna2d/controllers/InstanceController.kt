package com.preibisch.pinna2d.controllers

import com.preibisch.pinna2d.tools.Imp
import com.preibisch.pinna2d.view.MainAnnotationView
import tornadofx.*
import java.io.File

class InstanceController : Controller() {

    val annotationController: AnnotationController by inject()
    private val imageController: ImageController by inject()

    var input_path: String = ""
    var mask_path: String = ""
    var image_name: String = ""


    fun start() {
        val inputPath = MainAnnotationView::class.java.getResource("/img.tif").path
        val maskPath = MainAnnotationView::class.java.getResource("/mask.tif").path
       start(input_path,mask_path)
    }

    fun start(inputPath: String, maskPath: String) {
        input_path = inputPath
        mask_path = maskPath
        image_name = File(inputPath).name
        println(image_name)
        imageController.start(input_path, mask_path,image_name)
//        annotationController.imgAnnotation(imageName, Imp.get().min, Imp.get().max)
        annotationController.start(image_name, Imp.get().min+1, Imp.get().max)
    }
}