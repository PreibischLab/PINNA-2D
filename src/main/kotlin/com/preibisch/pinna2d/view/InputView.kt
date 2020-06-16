package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.app.AnnotationWorkspace
import com.preibisch.pinna2d.app.Styles
import com.preibisch.pinna2d.controllers.InstanceController
import com.preibisch.pinna2d.util.INPUT_PATH
import com.preibisch.pinna2d.util.MASK_PATH
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.stage.FileChooser
import tornadofx.*

class InputView : View("Select Input") {

    private val instanceController: InstanceController by inject()
    var input = SimpleStringProperty(INPUT_PATH)
    var mask = SimpleStringProperty(MASK_PATH)
    override val root = form {
        setPrefSize(600.0,170.0)
        paddingAll = 10.0

        fieldset {
            spacing = 20.0
            field("Input image"){
                textfield(input) {
                    addClass(Styles.fieldStyle)
                    isEditable = false
                }
                button("Select"){
                    setOnAction {
                        input.value  = openChooser()
                    }
                    addClass(Styles.inputButtonStyle)
                }
            }
            field("Mask image"){
                textfield(mask){
                    addClass(Styles.fieldStyle)
                    isEditable = false
                }
                button("Select"){
                    setOnAction {
                        mask.value  = openChooser()
                    }
                    addClass(Styles.inputButtonStyle)
                }
            }
            button("Next"){
                prefWidth = 600.0
                alignment = Pos.CENTER
                setOnAction {
                    when {
                        input.value == "" ->  Alert(Alert.AlertType.ERROR,"Invalid input").show()

                        mask.value == "" -> Alert(Alert.AlertType.ERROR,"Invalid mask").show()
                        else -> start(input.value,mask.value)
                        
                    }
                }
            }

        }

    }

    private fun start(input: String, mask: String) {
        instanceController.start(input,mask)
        close()
//        find(InputView::class).replaceWith(AnnotationWorkspace::class,sizeToScene = true,centerOnScreen = true)
    }

    private fun openChooser(): String {
        var chooser = FileChooser()
        chooser.extensionFilters.add(FileChooser.ExtensionFilter("TIFF file ", "*.tiff"))
        chooser.extensionFilters.add(FileChooser.ExtensionFilter("TIF file ", "*.tif"))
        chooser.extensionFilters.add(FileChooser.ExtensionFilter("PNG file ", "*.png"))
        val file = chooser.showOpenDialog(null)
        return file?.toString() ?: ""
    }
}
