package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.tools.Log
import com.preibisch.pinna2d.util.CURRENT_IMAGE
import com.preibisch.pinna2d.util.PROJECT_FOLDER
import com.preibisch.pinna2d.util.imageSaverBox
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File


class MainAnnotationView : View("Annotation") {
    private val imageView: ImageAnnotationView by inject()
    private val annotationEditorView: AnnotationEditorView by inject()
    override val root = borderpane() {
//        setPrefSize(1000.0, 800.0)

        center = imageView.root
        left = annotationEditorView.root
    }

//    override fun onCreate() {
//        val inputView = InputView()
//        inputView.openWindow()
////        inputView.onBeforeShow()
//
//    }

//    override fun onRefresh() {
//        super.onRefresh()
//    }

    //    override fun onDelete() {
//        super.onDelete()
//    }
    override fun onSave() {
        val file = imageSaverBox(this.currentWindow)
        if (file.toString() != null)
            imageView.save(File(file.toString()))
    }
}