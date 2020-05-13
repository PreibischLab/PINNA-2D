package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.controllers.AnnotationController
import com.preibisch.pinna2d.model.AnnotationEntryModel
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
                column("Category", AnnotationEntryModel::annotationVal)
            }
        }

    }

}