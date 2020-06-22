package com.preibisch.pinna2d.model

import com.preibisch.pinna2d.model.AnnotationEntryTbl.autoIncrement
import com.preibisch.pinna2d.model.AnnotationEntryTbl.primaryKey
import com.preibisch.pinna2d.util.toJavaLocalDate
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import tornadofx.*
import java.time.LocalDate

fun ResultRow.toImageEntry() = ImageEntry(
        this[ImageEntryTbl.id],
        this[ImageEntryTbl.entryDate].toJavaLocalDate(),
        this[ImageEntryTbl.fileName],
        this[ImageEntryTbl.nbMasks].toInt(),
        this[ImageEntryTbl.nbCells].toInt(),
        this[ImageEntryTbl.nbClassifiedCells].toInt(),
        this[ImageEntryTbl.status].toInt()
)

object ImageEntryTbl : Table() {

    val id = integer("id").autoIncrement().primaryKey()
    val entryDate = date("entry_date")
    val fileName = varchar("image_name", length = 100)
    val nbMasks = integer("nb_masks")
    val nbCells = integer("nb_cells")
    val nbClassifiedCells = integer("nb_classified_cells")
    val status = integer("status")

}

class ImageEntry(id: Int, entryDate: LocalDate,  name: String, nbMasks: Int, nbCells: Int, nbClassifiedCells: Int, status: Int) {
    val idProperty = SimpleIntegerProperty(id)
    var id by idProperty

    val entryDateProperty = SimpleObjectProperty<LocalDate>(entryDate)
    var entryDate by entryDateProperty

    val fileNameProperty = SimpleStringProperty(name)
    var fileName by fileNameProperty

    val nbMasksProperty = SimpleIntegerProperty(nbMasks)
    var nbMasks by nbMasksProperty

    val nbCellsProperty = SimpleIntegerProperty(nbCells)
    var nbCells by nbCellsProperty

    val nbClassifiedCellsProperty = SimpleIntegerProperty(nbClassifiedCells)
    var nbClassifiedCells by nbClassifiedCellsProperty

    val statusProperty = SimpleIntegerProperty(status)
    var status by statusProperty

//    override fun toString(): String = "$voltage (time=$time)"
}

class ImageEntryModel : ItemViewModel<ImageEntry>() {
    val id = bind { item?.idProperty }
    val entryDate = bind { item?.entryDateProperty }
    val fileName = bind { item?.fileNameProperty }
    val nbMasks = bind { item?.nbMasksProperty }
    val nbCells = bind { item?.nbCellsProperty }
    val nbClassifiedCells = bind { item?.nbClassifiedCellsProperty }
    val status = bind { item?.statusProperty}

}

