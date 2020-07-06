package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.app.Styles
import com.preibisch.pinna2d.controllers.AnnotationController
import com.preibisch.pinna2d.controllers.ImageController
import com.preibisch.pinna2d.model.AnnotationEntryModel
import com.preibisch.pinna2d.tools.Imp
import com.preibisch.pinna2d.util.*
import javafx.scene.control.Labeled
import javafx.scene.control.TableCell
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import tornadofx.*
import java.io.File

class AnnotationEditorView : View("Annotations") {

    var model = AnnotationEntryModel()
    val controller: AnnotationController by inject()
    private val imageController: ImageController by inject()
    var mTableView: TableViewEditModel<AnnotationEntryModel> by singleAssign()
    var categoriesButtons = ArrayList<ToggleButton>()

    override val root = vbox {
        prefWidth = 200.0
        form {
            fieldset {
                field("image:") {
                    label(model.imageName)
                }
            }
            fieldset { field("ID: ") { label(model.annotationId) } }
            fieldset { label("Category:") {  addClass(Styles.biglabel) } }
            fieldset {
                hbox(spacing = 10.0) {
                    categoriesButtons = generateCategoriesButtons()
                    for (button in categoriesButtons)
                        add(button)
                }
            }

            fieldset {
                controller.tableview = tableview<AnnotationEntryModel> {
                    items = controller.items
                    column("ID", AnnotationEntryModel::annotationId)
                    column("Category", AnnotationEntryModel::annotationVal).cellFormat {
                        initGraphic(this, Category(item.toInt()))
                    }
                    column("Size", AnnotationEntryModel::spaceDims)

                    onSelectionChange {
                        if (it != null) {
                            model.id.value = it.id.value
                            model.entryDate.value = it.entryDate.value
                            model.imageName.value = it.imageName.value
                            model.annotationId.value = it.annotationId.value
                            setSelectedCategory(it.annotationVal.value.toInt())
                            enableCategoryButtons()
                            if (it.annotationId.value != null) {
                                imageController.select(it.annotationId.value.toFloat())
                            }
                        }
                    }
                }

            }
          button("Export Statistics") {
              prefWidth = 220.0
                setOnAction { controller.exportStatistics() }
            }
            button("Save Instance Image") {
                prefWidth = 220.0
                setOnAction { save() }
            }


        }
    }

    private fun save(){
        val file = imageSaverBox(this.currentWindow)
        if (file.toString() != null)
            imageController.save(File(file.toString()))
    }

    private fun generateCategoriesButtons(): ArrayList<ToggleButton> {
        var butts = ArrayList<ToggleButton>()
        val group = ToggleGroup()
        for (cat in CATEGORIES)
            butts.add(getButton(cat, group))
        return butts
    }

    private fun getButton(cat: Int, group: ToggleGroup): ToggleButton {

        return togglebutton(group = group) {
            userData = cat
            toggleGroupProperty().set(group)
            initGraphic(this, Category(cat))
            isDisable = true
            setOnAction {
                changeCategory(cat)
            }

        }
    }

    private fun initGraphic(graph: Labeled , category: Category) {
        val circle = Circle(10.0)
        circle.stroke = Color.BLACK
        circle.strokeWidth = 1.0
        circle.fill = c(category.getColor())
        graph.text = category.getString()
        graph.graphic = circle

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
        val size = Imp.get().add(model.annotationId.value.toFloat(), category)
        println("change category $category")
        model.annotationVal.value = category
        model.spaceDims.value = size
        for (it in controller.items) {
            if (it.id.value == model.id.value) {
                it.annotationVal.value = category
                it.spaceDims.value = size
            }
        }
        controller.update(model)
        controller.tableview.selectionModel.selectNext()
    }
}
