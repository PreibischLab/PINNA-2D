package com.preibisch.pinna2d.util

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