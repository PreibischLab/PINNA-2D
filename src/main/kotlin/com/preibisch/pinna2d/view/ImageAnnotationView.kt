package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.controllers.AnnotationController
import com.preibisch.pinna2d.controllers.EventControllers
import com.preibisch.pinna2d.controllers.ImageController
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.ScrollPane.ScrollBarPolicy
import tornadofx.*
import java.io.File


class ImageAnnotationView : View("Image View") {

    private val imageController: ImageController by inject()

    var scrollPane = ScrollPane()
    override val root = borderpane {
        EventControllers().addEventsListners(imageController.imageView)

        scrollPane.setPrefSize(500.0, 400.0)
        scrollPane.vbarPolicy = ScrollBarPolicy.AS_NEEDED
        scrollPane.hbarPolicy = ScrollBarPolicy.AS_NEEDED
        scrollPane.content =  imageController.imageView
        center = scrollPane

    }.apply {
        style {
            backgroundColor += c("#E0EEEE")
        }

    }
    fun save(file: File) {
        imageController.save(file)
    }

}

