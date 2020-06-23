package com.preibisch.pinna2d.app

import com.preibisch.pinna2d.tools.Log
import com.preibisch.pinna2d.view.FileSelectionView
import javafx.scene.control.TabPane
import tornadofx.*

class AnnotationWorkspace : Workspace("PINNA-2D", NavigationMode.Tabs) {
    init {
        scope.workspace = this
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
        val select = dock<FileSelectionView>()

        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }



}
