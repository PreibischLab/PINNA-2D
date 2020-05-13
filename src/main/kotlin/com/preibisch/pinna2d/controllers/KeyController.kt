package com.preibisch.pinna2d.controllers

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import tornadofx.*

class KeyController : Controller() {

    fun keyPressed(it: KeyEvent) {
        println("Not implimented yet")
        if (it.code == KeyCode.ENTER) {
            println("Hello")
        }
    }

}