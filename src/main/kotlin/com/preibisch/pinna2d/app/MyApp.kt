package com.preibisch.pinna2d.app

import com.preibisch.pinna2d.controllers.AnnotationController
import com.preibisch.pinna2d.controllers.KeyController
import com.preibisch.pinna2d.util.createTables
import com.preibisch.pinna2d.util.enableConsoleLogger
import com.preibisch.pinna2d.view.MainAnnotationView
import javafx.scene.input.KeyEvent
import javafx.stage.Stage
import org.jetbrains.exposed.sql.Database
import tornadofx.*

class MyApp: App(MainAnnotationView::class, Styles::class){

    val keyController : KeyController by inject()


    override fun start(stage: Stage) {

        with(stage){
            width = 1000.0
            height = 800.0
//            scene.setOnKeyPressed {
//                println("pressed:" + it.character)
//            }
            addEventFilter(KeyEvent.ANY) {
                println("pressed:" + it.character)
                keyController.keyPressed(it)
            }
            super.start(stage)
        }
    }

    init {
        // Initialize DB
        enableConsoleLogger()
        Database.connect("jdbc:sqlite:./pinny-annotations.db", "org.sqlite.JDBC")
        createTables()

        // controller(es)
        AnnotationController()
    }


}