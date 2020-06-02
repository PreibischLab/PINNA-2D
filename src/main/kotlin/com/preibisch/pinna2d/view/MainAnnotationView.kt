package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.controllers.ImageController
import com.preibisch.pinna2d.controllers.InstanceController
import com.preibisch.pinna2d.tools.Log
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File


class MainAnnotationView : View("Annotation") {
    private val imageView: ImageAnnotationView by inject()
    private val annotationEditorView: AnnotationEditorView by inject()
    override val root = borderpane() {
        setPrefSize(1000.0, 500.0)

        center = imageView.root
        left = annotationEditorView.root
    }

    override fun onCreate() {
        val inputView = InputView()
        inputView.openWindow()
//        inputView.onBeforeShow()

    }

    override fun onRefresh() {
        super.onRefresh()
    }

    override fun onDelete() {
        super.onDelete()
    }
    override fun onSave() {
        Log.info("save")
        var chooser = FileChooser()
        val name = "Hello"
        chooser.initialFileName = name
        chooser.extensionFilters.add(FileChooser.ExtensionFilter("TIFF file ", "*.tiff"))
        chooser.extensionFilters.add(FileChooser.ExtensionFilter("PNG file ", "*.png"))
        val file = chooser.showSaveDialog(this.currentWindow)
        Log.info(file.toString())
        if (file.toString()!=null)
            imageView.save(File(file.toString()))
    }
}