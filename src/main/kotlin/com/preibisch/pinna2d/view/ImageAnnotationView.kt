package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.controllers.EventControllers
import com.preibisch.pinna2d.controllers.ImageController
import com.preibisch.pinna2d.tools.Log
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.ScrollPane.ScrollBarPolicy
import javafx.scene.control.ToggleButton
import javafx.scene.layout.VBox
import tornadofx.*
import java.io.File


class ImageAnnotationView : View("Image View") {

    private val imageController: ImageController by inject()
    var scrollPane = ScrollPane()

    override val root = borderpane {
        top = hbox {
            paddingAll = 10.0
            spacing = 20.0
            for (elm in imageController.getActivationMap())
                add(togglebutton {
                    text = elm.key
                    isSelected = elm.value
                    action {
                        imageController.changeActivationValueFor(text,isSelected)
                    }
                })
        }
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

