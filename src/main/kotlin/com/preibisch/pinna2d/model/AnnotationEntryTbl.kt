package com.preibisch.pinna2d.model

import com.preibisch.pinna2d.util.*
import javafx.beans.property.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import tornadofx.*
import java.time.LocalDate

fun ResultRow.toAnnotationEntry() = AnnotationEntry(
        this[AnnotationEntryTbl.id],
        this[AnnotationEntryTbl.entryDate].toJavaLocalDate(),
        this[AnnotationEntryTbl.imageId].toInt(),
        this[AnnotationEntryTbl.imageName],
        this[AnnotationEntryTbl.annotationId].toFloat(),
        this[AnnotationEntryTbl.annotationVal].toInt(),
        this[AnnotationEntryTbl.spaceDims].toLong()
)

object AnnotationEntryTbl : Table() {
    val id = integer(id_string).autoIncrement().primaryKey()
    val entryDate = date(entry_date)
    val imageId = integer(image_id)
    val imageName = varchar(image_name, length = 100)
    val annotationId = float(annotation_id)
    val annotationVal = integer(annotation_val)
    val spaceDims = long(space_dims)
}

class AnnotationEntry(id: Int, entryDate: LocalDate, imageId: Int, imageName: String, annotationId: Float, annotationVal : Int, spaceVal: Long) {
    val idProperty = SimpleIntegerProperty(id)
    var id by idProperty

    val imageIdProperty = SimpleIntegerProperty(imageId)
    var imageId by imageIdProperty

    val entryDateProperty = SimpleObjectProperty<LocalDate>(entryDate)
    var entryDate by entryDateProperty

    val imageNameProperty = SimpleStringProperty(imageName)
    var imageName by imageNameProperty

    val annotationIdProperty = SimpleFloatProperty(annotationId)
    var annotationId by annotationIdProperty

    val annotationValProperty = SimpleIntegerProperty(annotationVal)
    var annotationVal by annotationValProperty

    val spaceDimsProperty = SimpleLongProperty(spaceVal)
    var spaceDims by spaceDimsProperty

    override fun toString(): String {
        return "AnnotationEntry(id=$id, entryDate=$entryDate, imageName=$imageName, annotationId=$annotationId, annotationVal=$annotationVal"
    }
}

class AnnotationEntryModel : ItemViewModel<AnnotationEntry>() {
    val id = bind { item?.idProperty }
    val entryDate = bind { item?.entryDateProperty }
    val imageId = bind { item?.imageIdProperty }
    val imageName = bind { item?.imageNameProperty }
    val annotationId = bind { item?.annotationIdProperty }
    val annotationVal = bind { item?.annotationValProperty }
    val spaceDims = bind { item?.spaceDimsProperty }

    fun toRow():String{
        return String.format("%.2f,%d,%d", annotationId.value, annotationVal.value, spaceDims.value)
    }
}

