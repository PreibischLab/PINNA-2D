package com.preibisch.pinna2d.model

import com.preibisch.pinna2d.util.INPUT_FOLDER
import com.preibisch.pinna2d.util.LUT_PATH
import com.preibisch.pinna2d.util.MASKS_FOLDER
import com.preibisch.pinna2d.util.PROJECT_FOLDER
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class InputConfiguration(projectFolderSelected: String = PROJECT_FOLDER, inputFolderSelected: String = INPUT_FOLDER, maskFolderSelected: String = MASKS_FOLDER, lutFileSelected: String = LUT_PATH) {


    val projectFolderProperty = SimpleStringProperty(projectFolderSelected)
    var projectFolder by projectFolderProperty

    val inputFolderProperty = SimpleStringProperty(inputFolderSelected)
    var inputFolder by inputFolderProperty

    val maskFolderProperty = SimpleStringProperty(maskFolderSelected)
    var maskFolder by maskFolderProperty

    val lutFileProperty = SimpleStringProperty(lutFileSelected)
    var lutFile by lutFileProperty

    fun isValid(): Boolean {
        return projectFolder.isNotEmpty().and(inputFolder.isNotEmpty()).and(projectFolder.isNotEmpty())
    }
}

class InputConfigurationModel(property: ObjectProperty<InputConfiguration>) : ItemViewModel<InputConfiguration>(itemProperty = property) {
    val projectFolder = bind(autocommit = true) { item?.projectFolderProperty }
    val inputFolder = bind(autocommit = true) { item?.inputFolderProperty }
    val maskFolder = bind(autocommit = true) { item?.maskFolderProperty }
    val lutFile = bind(autocommit = true) { item?.lutFileProperty }
}