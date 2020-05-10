package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.controllers.AnnotationController
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.input.MouseDragEvent
import tornadofx.*

class MainView : View("Annotation") {
    private val annotationController : AnnotationController by inject()
    private  var myLabel : Label by singleAssign()
    override val root = borderpane() {
        setPrefSize(1000.0,500.0)
        center{
            label {
                myLabel = this
                style {
                    fontSize = 21.px
                }
                bind(annotationController.mText)
            }
        }

        bottom{
            label("Click anywhere .."){
                alignment = Pos.BOTTOM_CENTER
                paddingAll = 19.0
            }.apply {
                style{
                    opacity = 0.3
                    fontSize = 25.px
                }
            }
        }
    }.apply {
        style {
            backgroundColor += c("#E0EEEE")
        }

        addEventFilter(MouseDragEvent.MOUSE_DRAGGED){
            annotationController.addCircle(it,this)
        }
        addEventFilter(MouseDragEvent.MOUSE_CLICKED){
            annotationController.addCircle(it,this)
//            annotationController.addRandomText()
        }
    }
}