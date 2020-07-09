package com.preibisch.pinna2d.controllers

import com.preibisch.pinna2d.model.AnnotationEntryModel
import com.preibisch.pinna2d.model.Instance
import com.preibisch.pinna2d.tools.Imp
import tornadofx.*
import java.awt.Point
import java.io.File

class InstanceController : Controller() {
    var model = AnnotationEntryModel()
    var started = false
    val annotationController: AnnotationController by inject()
    val fileController: FilesAnalyzeManager by inject()
    private val imageController: ImageController by inject()
    var point = Point(0,0);

    fun start(model: Instance) {
        val imageName = File(model.inputPath).name
        println(imageName)
        imageController.start(model.inputPath, model.maskPath, imageName, model.lutFile)
        fileController.startedImage(imageName)
        annotationController.start(model.imageId, model.projectFolder, imageName, Imp.get().min + 1, Imp.get().max)
//        started = true
    }

    fun clickOnImage(x: Double, y: Double) {
        point = Point(x.toInt(),y.toInt())
        imageController.clickOnImage(point)
    }

    fun numberClicked(category: Int) {
        if(started)
            setCategory(category)
    }

    fun setCategory(category: Int) {
        val size = Imp.get().add(model.annotationId.value.toFloat(), category)
        println("change category $category")
        model.annotationVal.value = category
        model.spaceDims.value = size
        for (it in annotationController.items) {
            if (it.id.value == model.id.value) {
                it.annotationVal.value = category
                it.spaceDims.value = size
            }
        }
        annotationController.update(model)
        annotationController.tableview.selectionModel.selectNext()
    }

    fun selectAreaInImage(it: AnnotationEntryModel) {
        if (it.annotationId.value != null)
            imageController.select(it.annotationId.value.toFloat())
    }

    fun saveImage(file: File?) {
        if (file.toString() != null)
            imageController.save(File(file.toString()))
    }

    fun exportStatistics() {
        annotationController.exportStatistics()
    }

    fun setStart() {
        started = true
    }
}
