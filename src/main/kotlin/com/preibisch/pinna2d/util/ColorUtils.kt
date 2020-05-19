package com.preibisch.pinna2d.util

private val colorList: List<String> = listOf("#81ecec", "#55efc4", "#74b9ff", "#a29bfe", "#b2bec3", "#fab1a0", "#fd79a8")

fun randomColor(): String {
    return randomValue(colorList)
}
