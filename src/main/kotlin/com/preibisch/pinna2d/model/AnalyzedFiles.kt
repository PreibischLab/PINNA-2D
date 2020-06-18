package com.preibisch.pinna2d.model

import com.preibisch.pinna2d.app.Styles
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.Alert
import tornadofx.*

class FileAnalyzeData(name: String, nbMasks: Int, nbClassifiedCells: Int, nbTotalCells: Int) {
    val fileNameProperty = SimpleStringProperty(name)
    var fileName by fileNameProperty
    val nbMasksProperty = SimpleIntegerProperty(nbMasks)
    var nbMasks by nbMasksProperty


    val nbAnalyzedProperty = SimpleIntegerProperty(nbClassifiedCells)
    var nbAnalyzed by nbAnalyzedProperty

    val nbTotalCellsProperty = SimpleIntegerProperty(nbTotalCells)
    var totalCells by nbTotalCellsProperty


//    override fun toString(): String = "$voltage (time=$time)"
}

