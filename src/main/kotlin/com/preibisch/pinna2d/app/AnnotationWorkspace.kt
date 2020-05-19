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
        // doc our views
        dock<MainAnnotationView>()
        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }

    override fun onCreate() {
        super.onCreate()

    }
}
