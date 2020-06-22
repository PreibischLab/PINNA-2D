package com.preibisch.pinna2d.app

import com.preibisch.pinna2d.controllers.AnnotationController
import com.preibisch.pinna2d.util.createTables
import com.preibisch.pinna2d.util.enableConsoleLogger
import com.preibisch.pinna2d.view.FileSelectionView
import com.preibisch.pinna2d.view.MainAnnotationView
import javafx.application.Platform
import javafx.scene.control.Menu
import javafx.scene.control.TabPane
import org.jetbrains.exposed.sql.Database
import tornadofx.*

class AnnotationWorkspace: Workspace("Budget Tracker Workspace", NavigationMode.Tabs) {
    init {
//        menubar {
//            menu("File") {
//                item("New").action {
//                    //workspace.dock(mainView, true)
//                    log.info("Opening text file")
//                    workspace.dock(FileSelectionView(), true)
//                }
//                separator()
//                item("Exit").action {
//                    log.info("Leaving workspace")
//                    Platform.exit()
//                }
//            }
//            menu("Window"){
//                item("Close all").action {
//                    workspace.dock(MainAnnotationView(),true)
//                }
//                separator()
//            }
//            menu("Help") {
//                item("About...")
//            }
//
//        }
        // doc our views
        dock<FileSelectionView>()
        dock<MainAnnotationView>()
        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }

}
