package com.preibisch.pinna2d.model

import java.io.File

fun InputConfiguration.toInstance(selected: ImageEntryModel): Instance {
    val inputFile = File(this.inputFolderProperty.value, selected.fileName.value).absolutePath
    val maskFile = File(this.maskFolderProperty.value, selected.maskFile.value).absolutePath
    return Instance(selected.id.value.toInt(), this.projectFolderProperty.value, inputFile, maskFile, this.lutFileProperty.value)
}

class Instance(imageId: Int, projectFolder: String, inputPath: String, maskPath: String, lutFile: String) {
    val imageId = imageId
    val projectFolder = projectFolder
    val inputPath = inputPath
    val maskPath = maskPath
    val lutFile = lutFile

}