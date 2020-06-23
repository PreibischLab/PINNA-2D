package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.app.Styles
import com.preibisch.pinna2d.controllers.FilesAnalyzeManager
import com.preibisch.pinna2d.controllers.InstanceController
import com.preibisch.pinna2d.model.ImageEntryModel
import com.preibisch.pinna2d.util.*
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import tornadofx.*
import java.io.File

class FileSelectionView : View("Init Project") {

    private val instanceController: InstanceController by inject()
    private val controller: FilesAnalyzeManager by inject()
    var projectFolder = SimpleStringProperty(PROJECT_FOLDER)
    var input = SimpleStringProperty(INPUT_FOLDER)
    var mask = SimpleStringProperty(MASKS_FOLDER)
    var lut = SimpleStringProperty("")

    override val root = form {
            paddingAll = 10.0
            fieldset {
                spacing = 20.0
                field("Project Folder") {
                    textfield(projectFolder)
                    button("Select") {
                        setOnAction {
                            projectFolder.value = openFolderChooser()
                            checkProjectFolder(projectFolder.value)
                        }
                        addClass(Styles.inputButtonStyle)
                    }
                }
                field("Input Folder") {
                    textfield(input)
                    button("Select") {
                        setOnAction {
                            input.value =  openFolderChooser()
                        }
                        addClass(Styles.inputButtonStyle)
                    }
                }
                field("Mask Folder") {
                    textfield(mask)
                    button("Select") {
                        setOnAction {
                            mask.value = openFolderChooser()
                        }
                        addClass(Styles.inputButtonStyle)
                    }
                }

                field("Lut Color") {
                    textfield(lut)
                    button("Select") {
                        setOnAction {
                            lut.value = openLutFileChooser(null)?.path ?: ""
                            lut_path = lut.value
                        }
                        addClass(Styles.inputButtonStyle)
                    }
                }


                button("Scan") {
                    prefWidth = 1200.0
                    alignment = Pos.CENTER
                    action {
                        initDB(projectFolder.value)
                        PROJECT_FOLDER = projectFolder.value
                        controller.start(input.value, mask.value)
                    }
                    enableWhen { input.isNotEmpty.and(mask.isNotEmpty) }
                }


                tableview(controller.files) {
                    columnResizePolicy = SmartResize.POLICY
//            columnResizePolicy = CONSTRAINED_RESIZE_POLICY
                    vgrow = Priority.ALWAYS
                    bindSelected(controller.selectedFile)
                    column("Status", ImageEntryModel::status).cellFormat {
                        val circle = Circle(10.0)
                        circle.stroke = Color.BLACK
                        circle.strokeWidth = 1.0
                        circle.fill = c(getFileStatusColor(item.toInt()))
                        graphic = circle
                        text = getStatus(it.toInt())
                    }
                    column("File Name", ImageEntryModel::fileName)
                    column("Cells", ImageEntryModel::nbCells)
                    column("Classified", ImageEntryModel::nbClassifiedCells)
                }
                button("Next") {
                    prefWidth = 1200.0
                    alignment = Pos.CENTER
                    enableWhen {
                        controller.selectedFile.isNotNull
                    }
                    setOnAction {
                        next(controller.selectedFile.value)
                    }
                }
            }
    }



    private fun next(value: ImageEntryModel) {
        when {
            projectFolder.value == "" ->  Alert(Alert.AlertType.ERROR,"Invalid Project Folder").show()
            input.value == "" ->  Alert(Alert.AlertType.ERROR,"Invalid input").show()
            mask.value == "" -> Alert(Alert.AlertType.ERROR,"Invalid mask").show()
            else -> start(value)
        }
    }

    private fun start(img: ImageEntryModel) {
        val selected = controller.selectedFile.value
        val inputFile = File(input.value,selected.fileName.value).absolutePath
        val maskFile = File(mask.value,selected.maskFile.value).absolutePath
        CURRENT_IMAGE = img.fileName.value
        instanceController.start(img.id.value.toInt(),projectFolder.value,inputFile,maskFile)
        initAnnotationView()
    }

    private fun initAnnotationView() {
        workspace.dock<MainAnnotationView>()
    }

    private fun checkProjectFolder(folder: String) {
        val subFiles =  File(folder).listFiles()
        for (f in subFiles){
            if (f.isDirectory){
                if (f.name == "Input")
                    input.value = f.absolutePath
                if (f.name == "Masks")
                    input.value = f.absolutePath
            }
        }
    }

}
