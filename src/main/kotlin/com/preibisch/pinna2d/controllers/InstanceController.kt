package com.preibisch.pinna2d.controllers

import com.preibisch.pinna2d.model.AnnotationEntryTbl.imageName
import com.preibisch.pinna2d.model.Instance
import com.preibisch.pinna2d.tools.Imp
import tornadofx.*
import java.io.File

class InstanceController() : Controller() {

    val annotationController: AnnotationController by inject()
    val fileController: FilesAnalyzeManager by inject()
    private val imageController: ImageController by inject()

    fun start(model : Instance) {
        val imageName = File(model.inputPath).name
        println(imageName)
        imageController.start(model.inputPath, model.maskPath,imageName,model.lutFile)
        fileController.startedImage(imageName)
        annotationController.start(model.imageId,model.projectFolder,imageName, Imp.get().min+1, Imp.get().max)
    }
}