package com.preibisch.pinna2d.controllers

import javafx.scene.Node
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseDragEvent
import javafx.scene.input.MouseEvent
import tornadofx.*
import java.util.*

class EventControllers  : Controller(){

    var pressedKeys: ArrayList<KeyCode> = arrayListOf()

    private val imageController: ImageController by inject()

    public fun addEventsListners(node: Node) {
        with(node) {
            addEventFilter(MouseEvent.ANY) {
//            if (imageController.pressedKeys.contains(KeyCode.CONTROL)) {
//                println("Control")
//            } else
                when (it.eventType) {
                    MouseDragEvent.MOUSE_CLICKED -> imageController.addCircle(it, node)
                    MouseDragEvent.MOUSE_DRAGGED -> imageController.addCircle(it, node)
//                    else -> imageController.positionCicle(it, this)
//                MouseEvent.MOUSE_MOVED
                }
            }
            setOnScroll {
                mouseScroll(it.deltaY)
            }
        }
    }

    fun mouseScroll(deltaY: Double) {
        imageController.circleRadius += (imageController.scrollFactor * deltaY)
        if(imageController.circleRadius<1) imageController.circleRadius = 1.0
    }

    fun keyPressed(key: KeyCode){
        println(key.toString())
        pressedKeys.add(key)
    }

    fun keyReleased(key: KeyCode){
        pressedKeys.remove(key)
    }
}