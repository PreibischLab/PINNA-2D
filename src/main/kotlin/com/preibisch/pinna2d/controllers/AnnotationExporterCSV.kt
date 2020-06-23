package com.preibisch.pinna2d.controllers

import com.preibisch.pinna2d.model.AnnotationEntryModel
import com.preibisch.pinna2d.tools.Log
import javafx.collections.ObservableList
import java.io.File
import java.io.FileWriter

fun File.toAnnotationCSV(list: ObservableList<AnnotationEntryModel>) {
    try {
        var fileWriter = FileWriter(this)
        val head = String.format("%s,%s,%s", AnnotationEntryModel::annotationId.name, AnnotationEntryModel::annotationVal.name, AnnotationEntryModel::spaceDims.name)
        fileWriter.append(head)
        for (annotation in list) {
            fileWriter.append("\n")
            fileWriter.append(annotation.toRow())
        }
        fileWriter.flush()
        fileWriter.close()
    } catch (e: Exception) {
        Log.error("Error writing file !")
        Log.error(e.toString())
    }

}