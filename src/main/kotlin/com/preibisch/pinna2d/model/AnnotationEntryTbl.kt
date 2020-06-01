package com.preibisch.pinna2d.model

import com.preibisch.pinna2d.model.AnnotationEntryTbl.autoIncrement
import com.preibisch.pinna2d.model.AnnotationEntryTbl.primaryKey
import com.preibisch.pinna2d.util.toJavaLocalDate
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import tornadofx.*
import java.time.LocalDate

fun ResultRow.toAnnotationEntry() = AnnotationEntry(
        this[AnnotationEntryTbl.id],
        this[AnnotationEntryTbl.entryDate].toJavaLocalDate(),
        this[AnnotationEntryTbl.imageName],
        this[AnnotationEntryTbl.annotationId].toInt(),
        this[AnnotationEntryTbl.annotationVal].toInt()

)

object AnnotationEntryTbl : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val entryDate = date("entry_date")
    val imageName = varchar("image_name", length = 100)
    val annotationId = integer("annotation_id")
    val annotationVal = integer("annotation_val")
}

class AnnotationEntry(id: Int, entryDate: LocalDate, imageName: String, annotationId: Int, annotationVal : Int) {
    val idProperty = SimpleIntegerProperty(id)
    var id by idProperty

    val entryDateProperty = SimpleObjectProperty<LocalDate>(entryDate)
    var entryDate by entryDateProperty

    val imageNameProperty = SimpleStringProperty(imageName)
    var imageName by imageNameProperty

    val annotationIdProperty = SimpleIntegerProperty(annotationId)
    var annotationId by annotationIdProperty

    val annotationValProperty = SimpleIntegerProperty(annotationVal)
    var annotationVal by annotationValProperty


//    var totalAnnotation = Bindings.add(1, 0)

    override fun toString(): String {
        return "AnnotationEntry(id=$id, entryDate=$entryDate, imageName=$imageName, annotationId=$annotationId, annotationVal=$annotationVal"
    }
}

class AnnotationEntryModel : ItemViewModel<AnnotationEntry>() {
    val id = bind { item?.idProperty }
    val entryDate = bind { item?.entryDateProperty }
    val imageName = bind { item?.imageNameProperty }
    val annotationId = bind { item?.annotationIdProperty }
    val annotationVal = bind { item?.annotationValProperty }
}

