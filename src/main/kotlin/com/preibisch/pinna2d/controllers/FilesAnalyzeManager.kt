package com.preibisch.pinna2d.controllers

import com.preibisch.pinna2d.model.FileAnalyzeData
import com.preibisch.pinna2d.util.assembleInputWithMasks
import com.preibisch.pinna2d.util.getFiles
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.io.File

class FilesAnalyzeManager : Controller() {

    var files: ObservableList<FileAnalyzeData> by singleAssign()
    val selectedFile = SimpleObjectProperty<FileAnalyzeData>()

    init {
        files = FXCollections.observableArrayList()
    }

    fun start(input: String, mask: String) {
        val inputFiles = getFiles(File(input), ".tif")
        val maskFiles = getFiles(File(mask), ".tif")
        val assembledData: Map<File, List<File>> = assembleInputWithMasks(inputFiles, maskFiles)
        assembledData.forEach { (t, u) -> files.add(FileAnalyzeData(t.name, u.size, 0, getMaxCells(u))) }
    }

    private fun getMaxCells(maskFiles: List<File>): Int {
        var max = 0
        maskFiles.forEach {
            val tmp = getNbCells(it)
            if(tmp>max ) max = tmp }
        return max
    }

    private fun getNbCells(maskFile: File): Int {
        val elms = maskFile.name.split(".")[0].split("_")
        return elms.last().toInt()
    }
}

