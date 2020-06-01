package com.preibisch.pinna2d.util

import com.preibisch.pinna2d.tools.Log

private val colorList: List<String> = listOf("#ecf0f1", "#2ecc71", "#f1c40f", "#9b59b6",  "#fd79a8")
private val randomColorList: List<String> = listOf("#81ecec", "#55efc4", "#74b9ff", "#a29bfe", "#b2bec3", "#fab1a0", "#fd79a8")

fun randomColor(): String {
    return randomValue(randomColorList)
}

fun getColor(category: Int) : String{
    if((category)>= colorList.size){
        Log.error("Invalid category, not found color")
        return colorList[colorList.size-1];}
    if(category<0)
        return colorList[0]
    return colorList[category]
}
