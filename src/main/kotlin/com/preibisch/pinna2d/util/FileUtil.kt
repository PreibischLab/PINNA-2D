package com.preibisch.pinna2d.util

import com.preibisch.pinna2d.tools.Log


private val statusList: List<String> = listOf("Not stated Yet","Started","No Masks","Finished")

fun getStatus(statusCode: Int) : String{
    if((statusCode)>= statusList.size){
        Log.error("Invalid Status")
        return statusList[statusList.size-1];}
    if(statusCode<0)
        return statusList[0]
    return statusList[statusCode]
}