package com.preibisch.pinna2d.util

import com.preibisch.pinna2d.tools.Log
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType
import javafx.stage.DirectoryChooser
import javafx.stage.FileChooser
import javafx.stage.Window
import tornadofx.*
import java.io.File
import java.io.IOException


fun assembleInputWithMasks(inputFiles: Array<File>, maskFiles: Array<File>): Map<File, List<File>> {
    var result = HashMap<File, List<File>>()
    inputFiles.forEach {
        result[it] = getMasksForFile(it, maskFiles)
    }
    return result
}

fun getMasksForFile(input: File, maskFiles: Array<File>): List<File> {
    var masks = ArrayList<File>()
    val basename = input.name.split(".")[0]
    maskFiles.forEach { if (basename in it.name) masks.add(it) }
    return masks
}

fun getFiles(folder: File, extension: String): Array<File> {
    if (!folder.exists())
        throw IOException("Folder ${folder.absolutePath} not Found!")
    return folder.listFiles { _, name -> name.toLowerCase().endsWith(extension) }
            ?: throw IOException("No files with extension $extension in ${folder.absolutePath}")
}

fun openFileChooser(): String {
    var chooser = FileChooser()
    chooser.extensionFilters.add(FileChooser.ExtensionFilter("TIF file ", "*.tif"))
    chooser.extensionFilters.add(FileChooser.ExtensionFilter("TIFF file ", "*.tiff"))
    chooser.extensionFilters.add(FileChooser.ExtensionFilter("PNG file ", "*.png"))
    val file = chooser.showOpenDialog(null)
    return file?.toString() ?: ""
}

fun openFolderChooser(): String {
    var chooser = DirectoryChooser()
    val file = chooser.showDialog(null)
    return file?.toString() ?: ""
}

fun openLutFileChooser(currentWindow: Window?): File? {
    Log.info("save")
    var chooser = FileChooser()
    chooser.extensionFilters.add(FileChooser.ExtensionFilter("LUT file ", "*.lut"))
    val file = chooser.showOpenDialog(currentWindow)
    Log.info(file.toString())
    return file
}

fun imageSaverBox(currentWindow: Window?): File? {
    Log.info("save")
    var chooser = FileChooser()
    val name = String.format("%s_instances.tif", CURRENT_IMAGE.split(".")[0])
    chooser.initialDirectory = File(PROJECT_FOLDER)
    chooser.initialFileName = name
    chooser.extensionFilters.add(FileChooser.ExtensionFilter("TIF file ", "*.tif"))
    chooser.extensionFilters.add(FileChooser.ExtensionFilter("TIFF file ", "*.tiff"))
    chooser.extensionFilters.add(FileChooser.ExtensionFilter("PNG file ", "*.png"))
    val file = chooser.showSaveDialog(currentWindow)
    Log.info(file.toString())
    return file
}

fun showPopup(condition: Boolean,title: String, msg: String){
    if (condition)
        information(title,msg)
    else
        error(title,msg)
}