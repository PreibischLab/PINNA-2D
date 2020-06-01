package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.controllers.AnnotationController
import com.preibisch.pinna2d.controllers.ImageController
import com.preibisch.pinna2d.model.AnnotationEntryModel
import com.preibisch.pinna2d.util.getColor
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import tornadofx.*

class AnnotationEditorView : View("Annotations") {

    val model = AnnotationEntryModel()
    val controller: AnnotationController by inject()
    private val imageController: ImageController by inject()
    var mTableView: TableViewEditModel<AnnotationEntryModel> by singleAssign()

    override val root = form {
        fieldset {
            controller.tableview = tableview<AnnotationEntryModel> {
                items = controller.items
                mTableView = editModel
                column("ID", AnnotationEntryModel::annotationId)
                column("Category", AnnotationEntryModel::annotationVal).cellFormat {
                    val circle = Circle(10.0)
                    circle.stroke = Color.BLACK
                    circle.strokeWidth = 1.0
                    circle.fill = c(getColor(item.toInt()))
                    graphic = circle
                    text = if (item.toInt() < 0) "" else it.toString()
//                    style {
//                        backgroundColor += c(getColor(item.toInt()))
//                    }
                }

                selectionModel.select(10)

                onSelectionChange {
                    if (it != null) {
                        if(it.annotationId.value!=null){
                            println("Here " + it.annotationId.value)
                            imageController.select(it.annotationId.value.toInt())}

                    }
                }
//                bindSelected()
//                sortOrder.add(xx)
            }
        }
    }

}

