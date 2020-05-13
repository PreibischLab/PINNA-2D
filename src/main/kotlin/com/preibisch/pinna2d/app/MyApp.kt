package com.preibisch.pinna2d.app

import com.preibisch.pinna2d.controllers.KeyController
import javafx.stage.Stage
import tornadofx.App

class MyApp: App(AnnotationWorkspace::class, Styles::class){

    val keyController : KeyController by inject()

    override fun start(stage: Stage) {
        with(stage){
            width = 1000.0
            height = 800.0
            super.start(stage)
            scene.setOnKeyPressed {
                keyController.keyPressed(it)
            }
        }
    }
}