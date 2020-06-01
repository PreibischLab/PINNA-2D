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

    private var myLabel: Label by singleAssign()

    var scrollPane = ScrollPane()
    override val root = borderpane {
        EventControllers().addEventsListners(imageController.imageView)
//        imageView.image = imageController.image

        scrollPane.setPrefSize(300.0, 250.0)
        scrollPane.vbarPolicy = ScrollBarPolicy.AS_NEEDED
        scrollPane.hbarPolicy = ScrollBarPolicy.AS_NEEDED
        scrollPane.content =  imageController.imageView
        center = scrollPane

//            label {
//                myLabel = this
//                bind(imageController.mText)
//        }

    }.apply {
        style {
            backgroundColor += c("#E0EEEE")
        }


//        addEventFilter(MouseDragEvent.MOUSE_CLICKED){
////            annotationController.addRandomText()
//        }

    }
    fun save(file: File) {
        imageController.save(file)
    }

}

