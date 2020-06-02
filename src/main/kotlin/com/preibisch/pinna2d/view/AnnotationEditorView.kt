package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.controllers.AnnotationController
import com.preibisch.pinna2d.controllers.ImageController
import com.preibisch.pinna2d.model.AnnotationEntryModel
import com.preibisch.pinna2d.util.CATEGORIES
import com.preibisch.pinna2d.util.getColor
import javafx.scene.control.Button
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import tornadofx.*

class AnnotationEditorView : View("Annotations") {

    var model = AnnotationEntryModel()
    val controller: AnnotationController by inject()
    private val imageController: ImageController by inject()
    var mTableView: TableViewEditModel<AnnotationEntryModel> by singleAssign()

    override val root = vbox {
        form {
            fieldset {
                field("image:") {
                    label(model.imageName)
//                    textfield(model.imageName){
//                        isEditable = false
//                    }
                }
            }
            fieldset {
                field("ID: ") {
                    label(model.annotationId)
//                    textfield(model.annotationId)
                }
            }
            fieldset {
                hbox(spacing = 10.0) {
                    for (c in CATEGORIES)
                       add(getButton(c))
                }
//                field("Annotation") {
//                    textfield(model.annotationVal)
//                }
            }

            fieldset {

                controller.tableview = tableview<AnnotationEntryModel> {
                    items = controller.items
//                    mTableView = editModel
                    column("ID", AnnotationEntryModel::annotationId)
                    column("Category", AnnotationEntryModel::annotationVal).cellFormat {
                        val circle = Circle(10.0)
                        circle.stroke = Color.BLACK
                        circle.strokeWidth = 1.0
                        circle.fill = c(getColor(item.toInt()))
                        graphic = circle
                        text = if (item.toInt() < 0) "" else it.toString()
                    }

//            bindSelected(model = model)

                    selectionModel.select(10)

                    onSelectionChange {
                        if (it != null) {

                            model.imageName.value = it.imageName.value

                            model.annotationId.value = it.annotationId.value
                            if (it.annotationId.value != null) {
                                println("Here " + it.annotationId.value)
                                imageController.select(it.annotationId.value.toInt())
                            }

                        }
                    }
//                sortOrder.add(xx)
                }
            }
        }

    }

    private fun getButton(category: Int): Button {
        val b = button {
            val circle = Circle(10.0)
            circle.stroke = Color.BLACK
            circle.strokeWidth = 1.0
            circle.fill = c(getColor(category))
            graphic = circle
            text = if (category < 0) "" else category.toString()
            setOnAction {
                changeCategory(category)
            }
        }
        return b
    }

    private fun changeCategory(category: Int) {
        println("change category $category")
    }
}
