package com.preibisch.pinna2d.app

import com.preibisch.pinna2d.controllers.AnnotationController
import com.preibisch.pinna2d.util.createTables
import com.preibisch.pinna2d.util.enableConsoleLogger
import com.preibisch.pinna2d.view.MainAnnotationView
import javafx.scene.control.TabPane
import org.jetbrains.exposed.sql.Database
import tornadofx.*

class AnnotationWorkspace: Workspace("Budget Tracker Workspace", NavigationMode.Tabs) {
    init {
        // Initialize DB
        enableConsoleLogger()
        Database.connect("jdbc:sqlite:./pinny-annotations.db", "org.sqlite.JDBC")
        createTables()

        // controller(es)
        AnnotationController()

        // doc our views
        dock<MainAnnotationView>()

        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }
}
