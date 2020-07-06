package com.preibisch.pinna2d.util

import com.preibisch.pinna2d.tools.Log

val CATEGORIES = listOf(0, 1, 2, 3,4)
val CATEGORIES_STRINGS = listOf("b","1","2","3","e")
class Category(category: Int){
    private val category: Int = category

    fun getColor() : String{
        if((category)>= colorList.size){
            Log.error("Invalid category, not found color")
            return colorList[colorList.size-1];}
        if(category<0)
            return colorList[0]
        return colorList[category]
    }

    fun getString(): String {
        return when(category){
            in CATEGORIES.indices -> CATEGORIES_STRINGS[category]
            else -> ""
        }
    }

}