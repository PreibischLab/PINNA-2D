package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.app.Styles
import com.preibisch.pinna2d.controllers.AnnotationController
import com.preibisch.pinna2d.controllers.ImageController
import com.preibisch.pinna2d.model.AnnotationEntryModel
import com.preibisch.pinna2d.util.CATEGORIES
import com.preibisch.pinna2d.util.getColor
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import tornadofx.*

class AnnotationEditorView : View("Annotations") {

    var model = AnnotationEntryModel()
    val controller: AnnotationController by inject()
    private val imageController: ImageController by inject()
    var mTableView: TableViewEditModel<AnnotationEntryModel> by singleAssign()
    var categoriesButtons = ArrayList<ToggleButton>()

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
                }
            }
            fieldset {
                label("Category:") {
                    addClass(Styles.biglabel)
                }
            }
            fieldset {
                hbox(spacing = 10.0) {
                    generateCategoriesButtons()
                    for (button in categoriesButtons)
                        add(button)
                }
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

//                    selectionModel.select(10)

                    onSelectionChange {
                        if (it != null) {
                            model.id.value = it.id.value
                            model.entryDate.value = it.entryDate.value
                            model.imageName.value = it.imageName.value
                            model.annotationId.value = it.annotationId.value
                            setSelectedCategory(it.annotationVal.value.toInt())
                            enableCategoryButtons()
                            if (it.annotationId.value != null) {
                                imageController.select(it.annotationId.value.toInt())

                            }

                        }
                    }
//                sortOrder.add(xx)
                }
            }
        }
    }

    private fun generateCategoriesButtons() {
        val group = ToggleGroup()
        for (cat in CATEGORIES)
            categoriesButtons.add(getButton(cat, group))
    }

    private fun getButton(category: Int, group: ToggleGroup): ToggleButton {

        return togglebutton(group = group) {
//            text = category.toString()
            userData = category
            toggleGroupProperty().set(group)
            val circle = Circle(10.0)
            circle.stroke = Color.BLACK
            circle.strokeWidth = 1.0
            circle.fill = c(getColor(category))
            graphic = circle
            isDisable = true
            text = if (category < 0) "" else category.toString()
            setOnAction {
                changeCategory(category)
            }

        }
    }

    fun setSelectedCategory(category: Int) {
        for (b in categoriesButtons)
            if (b.userData == category) {
                b.isSelected = true
            }
    }

    fun enableCategoryButtons() {
        for (b in categoriesButtons)
            b.isDisable = false
    }

    private fun changeCategory(category: Int) {

        println("change category $category")
        model.annotationVal.value = category
        controller.update(model)
//        controller.items.up
    }
}
