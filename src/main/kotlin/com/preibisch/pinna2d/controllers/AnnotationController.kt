package com.preibisch.pinna2d.controllers


import com.preibisch.pinna2d.model.AnnotationEntry
import com.preibisch.pinna2d.model.AnnotationEntryModel
import com.preibisch.pinna2d.model.AnnotationEntryTbl
import com.preibisch.pinna2d.model.toAnnotationEntry
import com.preibisch.pinna2d.tools.Imp
import com.preibisch.pinna2d.tools.Log
import com.preibisch.pinna2d.util.execute
import com.preibisch.pinna2d.util.showPopup
import com.preibisch.pinna2d.util.toDate
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.TableView
import org.jetbrains.exposed.sql.*
import tornadofx.*
import java.io.File
import java.time.LocalDate

class AnnotationController : Controller() {

    private var folder = ""
    private var currentImage = ""
    private val imageController: ImageController by inject()

    //    var annotationsModel = AnnotationEntryModel()
    var tableview: TableView<AnnotationEntryModel> by singleAssign()

    //    get All Items


    var items: ObservableList<AnnotationEntryModel> by singleAssign()
//    var pieItemsData = FXCollections.observableArrayList<PieChart.Data>()

    init {
        items = FXCollections.observableArrayList();
    }

    fun add(newImageId: Int,newEntryDate: LocalDate, newImageName: String, newAnnotationId: Float, newAnnotationVal: Int, spaceVal: Long): AnnotationEntry {
        val newEntry = execute {
            AnnotationEntryTbl.insert {
                it[entryDate] = newEntryDate.toDate()
                it[imageId] = newImageId
                it[imageName] = newImageName
                it[annotationId] = newAnnotationId
                it[annotationVal] = newAnnotationVal
                it[spaceDims] = spaceVal
            }
        }
        items.add(AnnotationEntryModel().apply {
            item = AnnotationEntry(newEntry[AnnotationEntryTbl.id], newEntryDate, newImageId,newImageName, newAnnotationId, newAnnotationVal, spaceVal)
        })
//        pieItemsData.add(PieChart.Data(newItem,newPrice))
        return AnnotationEntry(newEntry[AnnotationEntryTbl.id], newEntryDate, newImageId, newImageName, newAnnotationId, newAnnotationVal, spaceVal)
    }

    fun update(updatedItem: AnnotationEntryModel): Int {
        return execute {
            AnnotationEntryTbl.update({
                AnnotationEntryTbl.id eq (updatedItem.id.value.toInt())
            }) {
                it[entryDate] = updatedItem.entryDate.value.toDate()
                it[imageName] = updatedItem.imageName.value
                it[annotationId] = updatedItem.annotationId.value.toFloat()
                it[annotationVal] = updatedItem.annotationVal.value.toInt()
                it[spaceDims] = updatedItem.spaceDims.value.toLong()
            }
        }
    }

    fun delete(model: AnnotationEntryModel) {
        execute {
            AnnotationEntryTbl.deleteWhere {
                AnnotationEntryTbl.id eq (model.id.value.toInt())
            }
        }
        items.remove(model)
//        removeModelFromPie(model)
    }

    private fun newEntry(imageId: Int, img: String, min: Int, max: Int) {
        for (i in min..max) {
            add(imageId,LocalDate.now(), img, i.toFloat(), -1, 0)

        }
    }

    fun start(imageId: Int, projectFolder: String, imageName: String, min: Float, max: Float) {
        folder = projectFolder
        currentImage = imageName
        items.clear()
        val exists = checkExist(imageName)
        if (!exists)
            newEntry(imageId, imageName, min.toInt(), max.toInt())
        else {
            val newItems = execute {
                AnnotationEntryTbl.select { AnnotationEntryTbl.imageName eq imageName }.map {
                    AnnotationEntryModel().apply {
                        item = it.toAnnotationEntry()
                    }
                }
            }
            items.addAll(newItems.observable())
            addAll(newItems)
        }
    }

    private fun addAll(items: List<AnnotationEntryModel>) {
        for (it in items) {
            if (it.annotationVal.value.toInt() > 0)
                Imp.get().add(it.annotationId.value.toFloat(), it.annotationVal.value.toInt())
        }
        imageController.imageView.image = Imp.get().toImage()
//        imageController.updateImage()
    }


    private fun checkExist(img: String): Boolean {
        val listOfItems: List<AnnotationEntryModel> = execute {
            AnnotationEntryTbl.selectAll().map {
                AnnotationEntryModel().apply {
                    item = it.toAnnotationEntry()
                }
            }
        }
        for (item in listOfItems) {
            if (item.imageName.value == img) {
                Log.info("Image $img in Annotation database ")
                return true
            }
        }
        return false;
    }


    fun select(v: Float) {
        var position = 0;
        loop@ for (it in tableview.items) {
            if (it.annotationId.value == v)
                break@loop;

            position++;
        }
        if (position >= tableview.items.size)
            Log.error("Not found $v")
        else {
            tableview.selectionModel.select(position)
            tableview.scrollTo(position)
        }
    }

    fun exportStatistics() {
        val basename = currentImage.split(".")[0]
        val f = File(folder, String.format("%s.csv", basename))
        val result = f.toAnnotationCSV(items)
        showPopup(result,"Saving CSV file!",f.path)
    }


//    fun updatePiecePie(model: ExpensesEntryModel){
//        var modelId = model.id
//        var currentIndex : Int = 0
//
//        items.forEachIndexed { index, data ->
//            if(data.id == modelId ){
//                currentIndex = index
//                pieItemsData[currentIndex].name = data.itemName.value
//                pieItemsData[currentIndex].pieValue = data.itemPrice.value.toDouble()
//            }
//        }
//    }

//    private fun removeModelFromPie(model: ExpensesEntryModel) {
//        var currentIndex = 0
//        pieItemsData.forEachIndexed { index, data ->
//            if(data.name == model.itemName.value && index != -1){
//                currentIndex = index
//            }
//        }
//        pieItemsData.removeAt(currentIndex)
//    }
}