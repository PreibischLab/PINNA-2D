package com.preibisch.pinna2d.controllers


import com.preibisch.pinna2d.model.AnnotationEntry
import com.preibisch.pinna2d.model.AnnotationEntryModel
import com.preibisch.pinna2d.model.AnnotationEntryTbl
import com.preibisch.pinna2d.model.toAnnotationEntry
import com.preibisch.pinna2d.tools.Log
import com.preibisch.pinna2d.util.execute
import com.preibisch.pinna2d.util.toDate
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.chart.PieChart
import javafx.scene.control.TableView
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import tornadofx.*
import java.time.LocalDate

class AnnotationController : Controller() {
    var annotationsModel = AnnotationEntryModel()
    var tableview: TableView<AnnotationEntryModel> by singleAssign()
    //    get All Items
    private val listOfItems: ObservableList<AnnotationEntryModel> = execute {
//        .orderBy(AnnotationEntryTbl.annotationVal)
        AnnotationEntryTbl.selectAll().map {
            AnnotationEntryModel().apply {
                item = it.toAnnotationEntry()
            }
        }.observable()
    }

    var items: ObservableList<AnnotationEntryModel> by singleAssign()
//    var pieItemsData = FXCollections.observableArrayList<PieChart.Data>()

    init {
        items = listOfItems
        items
        //test
//        add(LocalDate.now(),"test1",1,1)
//        add(LocalDate.now(),"test1",2,3)
//        add(LocalDate.now(),"test1",3,2)
//        add(LocalDate.now(),"test1",4,1)

//        listOfItems.forEach{
//            print("Item :: ${it.itemName.value}")
//        }

//        items.forEach {
//            pieItemsData.add(PieChart.Data(it.itemName.value,it.itemPrice.value.toDouble()))
//        }
    }

    fun add(newEntryDate: LocalDate, newImageName: String, newAnnotationId: Int, newAnnotationVal: Int): AnnotationEntry {
        val newEntry = execute {
            AnnotationEntryTbl.insert {
                it[entryDate] = newEntryDate.toDate()
                it[imageName] = newImageName
                it[annotationId] = newAnnotationId
                it[annotationVal] = newAnnotationVal
            }
        }
        listOfItems.add(AnnotationEntryModel().apply {
            item = AnnotationEntry(newEntry[AnnotationEntryTbl.id], newEntryDate, newImageName,newAnnotationId,newAnnotationVal)
        })
//        pieItemsData.add(PieChart.Data(newItem,newPrice))
        return AnnotationEntry(newEntry[AnnotationEntryTbl.id], newEntryDate, newImageName,newAnnotationId,newAnnotationVal)
    }

    fun update(updatedItem: AnnotationEntryModel): Int {
        return execute {
            AnnotationEntryTbl.update({
                AnnotationEntryTbl.id eq (updatedItem.id.value.toInt())
            }) {
                it[entryDate] = updatedItem.entryDate.value.toDate()
                it[imageName] = updatedItem.imageName.value
                it[annotationId] = updatedItem.annotationId.value.toInt()
                it[annotationVal] = updatedItem.annotationVal.value.toInt()
            }
        }
    }

    fun delete(model: AnnotationEntryModel) {
        execute {
            AnnotationEntryTbl.deleteWhere {
                AnnotationEntryTbl.id eq (model.id.value.toInt())
            }
        }
        listOfItems.remove(model)
//        removeModelFromPie(model)
    }

    fun newEntry(img: String, min: Int, max: Int) {
        for ( i in min..max){
            add(LocalDate.now(),img,i,-1)

        }
    }

    fun imgAnnotation(img: String, min: Int, max: Int) {
        val exists = checkExist(img)
        if(exists == false)
            newEntry(img,min,max)
    }

    private fun checkExist(img: String): Boolean {
        for(item in listOfItems){
           if(item.imageName.value == img) {
               Log.info("Image exist in database")
               return true
           }
        }
        return false;
    }


    fun select(v: Int) {
        var position = 0;
        loop@ for (it in tableview.items) {
            if (it.annotationId.value == v)
                break@loop;

            position++;
        }
        if (position >= tableview.items.size)
            Log.error("Not found $v")
        else
            tableview.selectionModel.select(position)
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