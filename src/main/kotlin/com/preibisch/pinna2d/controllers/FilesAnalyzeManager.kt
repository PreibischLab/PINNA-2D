package com.preibisch.pinna2d.controllers

import com.preibisch.pinna2d.model.*
import com.preibisch.pinna2d.tools.Log
import com.preibisch.pinna2d.util.assembleInputWithMasks
import com.preibisch.pinna2d.util.execute
import com.preibisch.pinna2d.util.getFiles
import com.preibisch.pinna2d.util.toDate
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import tornadofx.*
import java.io.File
import java.time.LocalDate

class FilesAnalyzeManager : Controller() {
    var model = InputConfiguration()

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
        val listOfItems: List<ImageEntryModel> = execute {
            ImageEntryTbl.selectAll().map {
                ImageEntryModel().apply {
                    item = it.toImageEntry()
                }
            }
        }
        val assembledData: Map<File, List<File>> = assembleInputWithMasks(inputFiles, maskFiles)
        files.addAll(listOfItems)
        assembledData.forEach { (t, u) ->
            if (!checkExist(t.name, listOfItems)) {
                val bestMask = getMaxCells(u)
                val status = if (u.isNotEmpty()) 0 else 1
                add(LocalDate.now(), t.name, bestMask.name, u.size, getNbCells(bestMask), 0, status)
            }
        }


    }

    fun add(newEntryDate: LocalDate, newImageName: String, newMaskFile: String, newNbMasks: Int, newNbCells: Int, newNbClassifiedCells: Int, newStatus: Int): ImageEntry {
        val newEntry = execute {
            ImageEntryTbl.insert {
                it[entryDate] = newEntryDate.toDate()
                it[fileName] = newImageName
                it[maskFile] = newMaskFile
                it[nbMasks] = newNbMasks
                it[nbCells] = newNbCells
                it[nbClassifiedCells] = newNbClassifiedCells
                it[status] = newStatus
            }
        }
        files.add(ImageEntryModel().apply {
            item = ImageEntry(newEntry[ImageEntryTbl.id], newEntryDate, newImageName, newMaskFile, newNbMasks, newNbCells, newNbClassifiedCells, newStatus)
        })
        return ImageEntry(newEntry[ImageEntryTbl.id], newEntryDate, newImageName, newMaskFile, newNbMasks, newNbCells, newNbClassifiedCells, newStatus)
    }

    fun updateStatus(id: Int, newStatus: Int){
        return execute {
            ImageEntryTbl.update({
                AnnotationEntryTbl.id eq (id)
            }) {
                it[status] = newStatus
            }
        }

    }

    fun update(updatedItem: ImageEntryModel): Int {
        return execute {
            ImageEntryTbl.update({
                ImageEntryTbl.id eq (updatedItem.id.value.toInt())
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

    private fun getMaxCells(maskFiles: List<File>): File {
        var f = maskFiles.first()
        var max = 0
        maskFiles.forEach {
            val tmp = getNbCells(it)
            if (tmp > max) {
                max = tmp
                f = it
            }
        }
        return f
    }

    private fun checkExist(img: String, all: List<ImageEntryModel>): Boolean {
        for (item in all) {
            if (item.fileName.value == img) {
                Log.info("Image $img exist in database")
                return true
            }
        }
        return false;
    }

    private fun getNbCells(maskFile: File): Int {
        val elms = maskFile.name.split(".")[0].split("_")
        return elms.last().toInt()
    }

    fun startedImage(name: String) {
        val newStatus = 1
        for (f in files){
            if (f.fileName.value == name){
                Log.info("Update id: ${f.id.value}")
                f.status.value = newStatus
                update(f)
                break
            }
        }


    }
}

