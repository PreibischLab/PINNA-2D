package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.app.AnnotationWorkspace
import com.preibisch.pinna2d.app.Styles
import com.preibisch.pinna2d.controllers.FilesAnalyzeManager
import com.preibisch.pinna2d.model.ImageEntryModel
import com.preibisch.pinna2d.util.INPUT_FOLDER
import com.preibisch.pinna2d.util.MASKS_FOLDER
import com.preibisch.pinna2d.util.getFileStatusColor
import com.preibisch.pinna2d.util.getStatus
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import tornadofx.*

class FileSelectionView : View("Select Inputs") {
    private val controller: FilesAnalyzeManager by inject()
    var input = SimpleStringProperty(INPUT_FOLDER)
    var mask = SimpleStringProperty(MASKS_FOLDER)

    override val root = form {
            paddingAll = 10.0
            fieldset {
                spacing = 20.0
                field("Input Folder") {
                    textfield(input)
                    button("Select") {
                        setOnAction {
//                            input.value  = openChooser()
                        }
                        addClass(Styles.inputButtonStyle)
                    }
                }
                field("Mask Folder") {
                    textfield(mask)
                    button("Select") {
                        setOnAction {
//                            mask.value  = openChooser()
                        }
                        addClass(Styles.inputButtonStyle)
                    }
                }

            }
            button("Scan") {
                prefWidth = 600.0
                alignment = Pos.CENTER
                action {
                    controller.start(input.value,mask.value)
                }
                enableWhen{  input.isNotEmpty.and(mask.isNotEmpty)        }

            }

        tableview(controller.files) {
            columnResizePolicy = SmartResize.POLICY
//            columnResizePolicy = CONSTRAINED_RESIZE_POLICY
            vgrow = Priority.ALWAYS
            bindSelected(controller.selectedFile)
            column("Status",ImageEntryModel::status).cellFormat {
                val circle = Circle(10.0)
                circle.stroke = Color.BLACK
                circle.strokeWidth = 1.0
                circle.fill = c(getFileStatusColor(item.toInt()))
                graphic = circle
                text = getStatus(it.toInt())
            }
            column("File Name",ImageEntryModel::fileName)
//            column("Masks Found",FileAnalyzeData::nbMasks)
            column("Cells",ImageEntryModel::nbCells)
            column("Classified",ImageEntryModel::nbClassifiedCells)
        }
        button("Next") {
            prefWidth = 600.0
            alignment = Pos.CENTER
            enableWhen {
                controller.selectedFile.isNotNull
            }
            setOnAction {
//                AnnotationWorkspace().
            }
        }
    }
}
