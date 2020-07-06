package com.preibisch.pinna2d.view.FileSelection

import com.preibisch.pinna2d.controllers.FilesAnalyzeManager
import com.preibisch.pinna2d.controllers.InstanceController
import com.preibisch.pinna2d.model.ImageEntryModel
import com.preibisch.pinna2d.model.InputConfiguration
import com.preibisch.pinna2d.model.Instance
import com.preibisch.pinna2d.model.toInstance
import com.preibisch.pinna2d.util.CURRENT_IMAGE
import com.preibisch.pinna2d.util.getFileStatusColor
import com.preibisch.pinna2d.util.getStatus
import com.preibisch.pinna2d.view.MainAnnotationView
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import tornadofx.*
import java.io.File

class ListInputPane : View("My View") {
    private val controller: FilesAnalyzeManager by inject()
    private val instanceController: InstanceController by inject()

    override val root = form {
        fieldset {
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
        }
        fieldset {
            button("Next") {
                prefWidth = 1200.0
                alignment = Pos.CENTER
                enableWhen {
                    controller.selectedFile.isNotNull
                }
                setOnAction {
                    startAnnotatingImg(controller.selectedFile.value)
                }
            }
        }
    }

    private fun startAnnotatingImg(img: ImageEntryModel) {
        CURRENT_IMAGE = img.fileName.value
        instanceController.start(controller.model.toInstance(controller.selectedFile.value))
        initAnnotationView()
    }

    private fun initAnnotationView() {
        workspace.dock<MainAnnotationView>()
    }
}
