package com.preibisch.pinna2d.controllers

import javafx.scene.Node
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseDragEvent
import javafx.scene.input.MouseEvent
import tornadofx.*
import java.util.*

class EventControllers : Controller() {
    public val ANNOTATION_MODE = 0
    public val SEGMENTATION_MODE = 1
    var mode = ANNOTATION_MODE
    var pressedKeys: ArrayList<KeyCode> = arrayListOf()

    private val imageController: ImageController by inject()

    public fun addEventsListners(node: ImageView) {
        with(node) {
            addEventFilter(MouseEvent.ANY) {
                when (mode) {
                    ANNOTATION_MODE -> annotationEvent(it, node)
                    SEGMENTATION_MODE -> segmentationEvent(it, node)
                }
            }
//            setOnScroll {
//                if (pressedKeys.contains(KeyCode.SHIFT)) {
//                    mouseScroll(it.deltaY)
//                }
//            }
        }
    }

    private fun segmentationEvent(it: MouseEvent, node: Node) {
        when (it.eventType) {
            MouseDragEvent.MOUSE_CLICKED -> imageController.addCircle(it, node)
            MouseDragEvent.MOUSE_DRAGGED -> imageController.addCircle(it, node)
//                    else -> imageController.positionCicle(it, this)
//                MouseEvent.MOUSE_MOVED
        }
    }

    private fun annotationEvent(it: MouseEvent, node: Node) {
        when (it.eventType) {
            MouseDragEvent.MOUSE_CLICKED -> {imageController.clickOnImage(it.x, it.y)

                println("X: ${it.x} - Y: ${it.y} - sceneX: ${it.sceneX} - sceneY: ${it.sceneY} - screenX: ${it.screenX} - screenY: ${it.screenY}")

            }
        }

    }

    fun mouseScroll(deltaY: Double) {
        imageController.circleRadius += (imageController.scrollFactor * deltaY)
        if (imageController.circleRadius < 1) imageController.circleRadius = 1.0
    }

    fun keyPressed(key: KeyCode) {
        println(key.toString())
        pressedKeys.add(key)
    }

    fun keyReleased(key: KeyCode) {
        println("removed ${key.toString()}")
        pressedKeys.remove(key)
    }
}