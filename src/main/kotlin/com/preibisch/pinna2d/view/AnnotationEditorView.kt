package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.app.Styles
import com.preibisch.pinna2d.controllers.AnnotationController
import com.preibisch.pinna2d.model.AnnotationEntryModel
import com.preibisch.pinna2d.util.getColor

import javafx.scene.control.TableCell
import javafx.scene.paint.Color
import javafx.util.StringConverter
import tornadofx.*

class AnnotationEditorView : View("Annotations") {
    val model = AnnotationEntryModel()
    val controller: AnnotationController by inject()

    var mTableView: TableViewEditModel<AnnotationEntryModel> by singleAssign()

    override val root = form {
        fieldset {
            tableview<AnnotationEntryModel> {
                items = controller.items
                mTableView = editModel
                column("ID", AnnotationEntryModel::annotationId)
                column("Category", AnnotationEntryModel::annotationVal).cellFormat {

                    text = if (item.toInt() < 0) "null" else it.toString()
                    style {
                        backgroundColor += c(getColor(item.toInt()))
                    }
//                    when(it.toInt()){
//                        -1 -> addClass(Styles.validColumn)
//                    }
                }



//                sortOrder.add(xx)
            }
        }

    }

}

