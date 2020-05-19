package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.controllers.EventControllers
import com.preibisch.pinna2d.controllers.ImageController
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.ScrollPane.ScrollBarPolicy
import javafx.scene.image.ImageView
import tornadofx.*


class ImageAnnotationView : View("Image View") {
    private val imageController: ImageController by inject()
    private var myLabel: Label by singleAssign()
    private var imageView = ImageView()
    var scrollPane = ScrollPane()
    override val root = borderpane {
        EventControllers().addEventsListners(this)
        imageView.image = imageController.image
        imageView.setOnMouseClicked {
            println("X: ${it.x} - Y: ${it.y} - sceneX: ${it.sceneX} - sceneY: ${it.sceneY} - screenX: ${it.screenX} - screenY: ${it.screenY}")
           imageController.clickOnImage(it.x,it.y)
        }
        scrollPane.setPrefSize(300.0, 250.0)
        scrollPane.vbarPolicy = ScrollBarPolicy.AS_NEEDED
        scrollPane.hbarPolicy = ScrollBarPolicy.AS_NEEDED
        scrollPane.content =  imageView
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


}

