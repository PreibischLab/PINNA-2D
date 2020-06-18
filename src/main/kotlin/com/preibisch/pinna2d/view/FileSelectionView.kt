package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.app.Styles
import com.preibisch.pinna2d.controllers.FilesAnalyzeManager
import com.preibisch.pinna2d.model.FileAnalyzeData
import com.preibisch.pinna2d.util.INPUT_FOLDER
import com.preibisch.pinna2d.util.INPUT_PATH
import com.preibisch.pinna2d.util.MASKS_FOLDER
import com.preibisch.pinna2d.util.MASK_PATH
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import tornadofx.*

class FileSelectionView : View("List Select App") {
    private val controller: FilesAnalyzeManager by inject()
    var input = SimpleStringProperty(INPUT_FOLDER)
    var mask = SimpleStringProperty(MASKS_FOLDER)

    override val root = form {
//            setPrefSize(600.0,170.0)
            paddingAll = 10.0

            fieldset {
                spacing = 20.0
                field("Input Folder") {
                    textfield(input) {
                        addClass(Styles.fieldStyle)
                        isEditable = false
                    }
                    button("Select") {
                        setOnAction {
//                            input.value  = openChooser()
                        }
                        addClass(Styles.inputButtonStyle)
                    }
                }
                field("Mask Folder") {
                    textfield(mask) {
                        addClass(Styles.fieldStyle)
                        isEditable = false
                    }
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

        listview(controller.files) {
            bindSelected(controller.selectedFile)
            cellFormat {
                text = "${it.fileName} masks:${it.nbMasks}"  // the toString() doesn't have units
            }
        }

        hbox {

            button("Add") {
//                enableWhen { newVoltage.isNotEmpty.and(newTime.isNotEmpty) }
//                action {
////                    selectedReading.add(SensorData(newVoltage.value.toDouble(), newTime.value.toLong()))
//                }
            }
            button("Clear") {
//                enableWhen { newVoltage.isNotEmpty.or(newTime.isNotEmpty) }
//                action {
//                    newVoltage.value = ""
//                    newTime.value = ""
//                }
            }
            separator(Orientation.VERTICAL)
            button("Delete") {
                enableWhen { controller.selectedFile.isNotNull }
                action {
//                    val obj = selectedReading.value
//
//                    sensorReadings.remove(obj)
//
//                    alert(Alert.AlertType.INFORMATION
//                            "Deleted",
//                            "Deleted ${obj.voltage} at t=${obj.time}")
                }
            }
            alignment = Pos.CENTER_LEFT
            padding = Insets(10.0)
            spacing = 4.0
        }
        button("Next") {
            prefWidth = 600.0
            alignment = Pos.CENTER
            setOnAction {
                when {
//                            input.value == "" ->  Alert(Alert.AlertType.ERROR,"Invalid input").show()
//
//                            mask.value == "" -> Alert(Alert.AlertType.ERROR,"Invalid mask").show()
//                            else -> start(input.value,mask.value)

                }
            }
        }
    }
}
