package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.controllers.ImageController
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseDragEvent
import tornadofx.*

class ImageAnnotationView : View("Image View") {
    private val imageController : ImageController by inject()
    private  var myLabel : Label by singleAssign()
    override val root = borderpane  {

        addEventFilter(KeyEvent.KEY_PRESSED){

        }
        center =  imageview(imageController.image)
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

        addEventFilter(MouseDragEvent.MOUSE_DRAGGED){
            imageController.addCircle(it,this)
        }
        addEventFilter(MouseDragEvent.MOUSE_CLICKED){
            imageController.addCircle(it,this)
//            annotationController.addRandomText()
        }

    }
}
