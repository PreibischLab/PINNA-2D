package com.preibisch.pinna2d.app

import com.preibisch.pinna2d.controllers.AnnotationController
import com.preibisch.pinna2d.controllers.EventControllers
import com.preibisch.pinna2d.util.createTables
import com.preibisch.pinna2d.util.enableConsoleLogger
import javafx.scene.input.KeyEvent
import javafx.stage.Stage
import org.jetbrains.exposed.sql.Database
import tornadofx.*

class MyApp: App(AnnotationWorkspace::class, Styles::class){

    val eventControllers : EventControllers by inject()


    override fun start(stage: Stage) {

        with(stage){
            width = 1000.0
            height = 800.0
//            scene.setOnKeyPressed {
//                println("pressed:" + it.character)
//            }
            addEventFilter(KeyEvent.ANY) {
               when(it.eventType){
                   KeyEvent.KEY_PRESSED -> eventControllers.keyPressed(it.code)
                   KeyEvent.KEY_RELEASED -> eventControllers.keyReleased(it.code)
               }
            }
            super.start(stage)
        }
    }

    init {

        // Initialize DB
        enableConsoleLogger()
    }


}