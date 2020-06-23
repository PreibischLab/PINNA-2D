package com.preibisch.pinna2d.controllers

import com.preibisch.pinna2d.tools.Imp
import tornadofx.*
import java.io.File

class InstanceController : Controller() {

    val annotationController: AnnotationController by inject()
    val fileController: FilesAnalyzeManager by inject()
    private val imageController: ImageController by inject()

    private var projectPath = ""
    var inputPath: String = ""
    var maskPath: String = ""
    var imageName: String = ""

    fun start(imageId: Int, projectFolder: String, inputPath: String, maskPath: String) {
        this.projectPath = projectFolder
        this.inputPath = inputPath
        this.maskPath = maskPath
        this.imageName = File(inputPath).name
        println(imageName)
        imageController.start(inputPath, maskPath,imageName)
        fileController.startedImage(imageName)
        annotationController.start(imageId,projectPath,imageName, Imp.get().min+1, Imp.get().max)


    }
}