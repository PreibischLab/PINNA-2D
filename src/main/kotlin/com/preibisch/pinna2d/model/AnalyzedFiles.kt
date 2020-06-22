package com.preibisch.pinna2d.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
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

    var status : Int

    init {
        status = 0
    }

//    override fun toString(): String = "$voltage (time=$time)"
}

