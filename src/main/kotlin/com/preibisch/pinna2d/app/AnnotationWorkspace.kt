package com.preibisch.pinna2d.app

import com.preibisch.pinna2d.view.FileSelection.FileSelectionView
import javafx.scene.control.TabPane
import tornadofx.*

class AnnotationWorkspace : Workspace("PINNA-2D", NavigationMode.Tabs) {
    init {
        scope.workspace = this
        // doc our views
        val select = dock<FileSelectionView>()

        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }



}
