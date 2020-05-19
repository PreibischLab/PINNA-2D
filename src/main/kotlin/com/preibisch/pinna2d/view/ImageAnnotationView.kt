package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.controllers.EventControllers
import com.preibisch.pinna2d.controllers.ImageController
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.input.MouseDragEvent
import javafx.scene.input.MouseEvent
import tornadofx.*

class ImageAnnotationView : View("Image View") {
    private val imageController: ImageController by inject()
    private var myLabel: Label by singleAssign()
    override val root = borderpane {
        EventControllers().addEventsListners(this)


        center = imageview(imageController.image)
//        borderpane {
//        center{
//            label {
//                myLabel = this
//                style {
//                    fontSize = 21.px
//                }
//                bind(imageController.mText)
//            }
//        }
//
//        bottom{
//            label("Click anywhere .."){
//                alignment = Pos.BOTTOM_CENTER
//                paddingAll = 19.0
//            }.apply {
//                style{
//                    opacity = 0.3
//                    fontSize = 25.px
//                }
//            }
//        }}
    }.apply {
        style {
            backgroundColor += c("#E0EEEE")
        }


//        addEventFilter(MouseDragEvent.MOUSE_CLICKED){
////            annotationController.addRandomText()
//        }

    }


}

