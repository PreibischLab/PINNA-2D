package com.preibisch.pinna2d.app

import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val biglabel by cssclass()
        val fieldStyle by cssclass()
        val inputButtonStyle by cssclass()
        val heading by cssclass()
        val validColumn by cssclass()
        val invalidColumn by cssclass()
        val category by cssclass()
    }

    init {
        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }

        biglabel{
            alignment = Pos.CENTER
            maxWidth = infinity
            fontSize = 15.px
            fontWeight = FontWeight.BLACK
        }

        inputButtonStyle{

        }

        fieldStyle{
            prefWidth = 300.px
        }
        invalidColumn {
            backgroundColor += c("#8b0000")
        }
        validColumn and category {
            backgroundColor += c("#00b200")
        }

    }
}