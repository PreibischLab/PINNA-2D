package com.preibisch.pinna2d.controllers

import com.preibisch.pinna2d.model.AnnotationEntryTbl
import com.preibisch.pinna2d.model.ImageEntry
import com.preibisch.pinna2d.model.ImageEntryModel
import com.preibisch.pinna2d.model.ImageEntryTbl
import com.preibisch.pinna2d.util.assembleInputWithMasks
import com.preibisch.pinna2d.util.execute
import com.preibisch.pinna2d.util.getFiles
import com.preibisch.pinna2d.util.toDate
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update
import tornadofx.*
import java.io.File
import java.time.LocalDate

class FilesAnalyzeManager : Controller() {

    var files: ObservableList<ImageEntryModel> by singleAssign()
    val selectedFile = SimpleObjectProperty<ImageEntryModel>()

    init {
        files = FXCollections.observableArrayList()
    }

    fun start(input: String, mask: String) {

        val inputFiles = getFiles(File(input), ".tif")
        val maskFiles = getFiles(File(mask), ".tif")
        newEntry(inputFiles, maskFiles)
    }

    private fun newEntry(inputFiles: Array<File>, maskFiles: Array<File>) {
        val assembledData: Map<File, List<File>> = assembleInputWithMasks(inputFiles, maskFiles)

        assembledData.forEach { (t, u) ->
            add(LocalDate.now(), t.name, u.size, getMaxCells(u), 0,0)
        }
    }

    fun add(newEntryDate: LocalDate, newImageName: String, newNbMasks: Int, newNbCells: Int, newNbClassifiedCells: Int, newStatus: Int): ImageEntry {
        val newEntry = execute {
            ImageEntryTbl.insert {
                it[entryDate] = newEntryDate.toDate()
                it[fileName] = newImageName
                it[nbMasks] = newNbMasks
                it[nbCells] = newNbCells
                it[nbClassifiedCells] = newNbClassifiedCells
                it[status] = newStatus
            }
        }
        files.add(ImageEntryModel().apply {
            item = ImageEntry(newEntry[ImageEntryTbl.id], newEntryDate, newImageName, newNbMasks, newNbCells, newNbClassifiedCells, newStatus)
        })
        return ImageEntry(newEntry[ImageEntryTbl.id], newEntryDate, newImageName, newNbMasks, newNbCells, newNbClassifiedCells, newStatus)
    }

    fun update(updatedItem: ImageEntryModel): Int {
        return execute {
            ImageEntryTbl.update({
                AnnotationEntryTbl.id eq (updatedItem.id.value.toInt())
            }) {
                it[entryDate] = updatedItem.entryDate.value.toDate()
                it[fileName] = updatedItem.fileName.value
                it[nbMasks] = updatedItem.nbMasks.value.toInt()
                it[nbCells] = updatedItem.nbCells.value.toInt()
                it[nbClassifiedCells] = updatedItem.nbClassifiedCells.value.toInt()
                it[status] = updatedItem.status.value.toInt()
            }
        }
    }

    private fun getMaxCells(maskFiles: List<File>): Int {
        var max = 0
        maskFiles.forEach {
            val tmp = getNbCells(it)
            if (tmp > max) max = tmp
        }
        return max
    }

    private fun getNbCells(maskFile: File): Int {
        val elms = maskFile.name.split(".")[0].split("_")
        return elms.last().toInt()
    }
}

