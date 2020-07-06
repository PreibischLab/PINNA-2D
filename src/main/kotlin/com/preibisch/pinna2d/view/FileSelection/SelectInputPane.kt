package com.preibisch.pinna2d.view.FileSelection

import com.preibisch.pinna2d.app.Styles
import com.preibisch.pinna2d.controllers.FilesAnalyzeManager
import com.preibisch.pinna2d.util.*
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import tornadofx.*
import java.io.File

class SelectInputPane : View("My View") {

    private val controller: FilesAnalyzeManager by inject()

    override val root = form {

        add(addFieldSet("Project Folder", "Select Project Folder - Get or create", controller.model.projectFolderProperty))
        add(addFieldSet("Input Folder", "Select Input Folder - Folder of Raw Tiffs", controller.model.inputFolderProperty))
        add(addFieldSet("Mask Folder", "Select Mask Folder - Folder of Masks generated ", controller.model.maskFolderProperty))
        add(addFieldSet("Lut File", "Select Lut File - Lut file is used for GUI colors ", controller.model.lutFileProperty,SelectionType.LutFile))

        fieldset {
            button("Scan") {
                prefWidth = 1200.0
                alignment = Pos.CENTER
//                enableWhen {
////                    controller.model.isValid().observable()
//                }
                action {
                    initDB(controller.model.projectFolderProperty.value)
                    PROJECT_FOLDER = controller.model.projectFolderProperty.value
                    controller.start(controller.model.inputFolderProperty.value, controller.model.maskFolderProperty.value)
                }
            }
        }
    }

    private fun addFieldSet(labelText: String, promptLabel: String, fieldProperty: SimpleStringProperty, selectionType : SelectionType = SelectionType.Folder): Fieldset {
        return fieldset {
            field(labelText) {
                textfield(fieldProperty) {
                    promptText = promptLabel
                }
                button("Select") {
                    setOnAction {
                        fieldProperty.value = if (selectionType == SelectionType.Folder) openFolderChooser() else openLutFileChooser()
//                        checkProjectFolder(fieldProperty.value)
                    }
                    addClass(Styles.inputButtonStyle)
                }
            }
        }
    }

    private fun checkProjectFolder(folder: String) {
        val subFiles = File(folder).listFiles()
        for (f in subFiles) {
            if (f.isDirectory) {
                if (f.name == "Input")
                    controller.model.inputFolderProperty.value = f.absolutePath
                if (f.name == "Masks")
                    controller.model.maskFolderProperty.value = f.absolutePath
            }
        }
    }
}

enum class SelectionType { ImageFile, LutFile, Folder }