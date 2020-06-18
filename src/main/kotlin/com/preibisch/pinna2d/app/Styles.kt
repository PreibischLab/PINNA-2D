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
        val overdue by cssclass()
        val highlighted by cssclass()
        val pass by cssclass()
        val fail by cssclass()
    }

    init {

        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }

        biglabel {
            alignment = Pos.CENTER
            maxWidth = infinity
            fontSize = 15.px
            fontWeight = FontWeight.BLACK
        }

        inputButtonStyle {

        }

        fieldStyle {
            prefWidth = 300.px
        }
        invalidColumn {
            backgroundColor += c("#8b0000")
        }
        validColumn and category {
            backgroundColor += c("#00b200")
        }

        pass {
            backgroundColor += c("#4CAF50")
            and(selected) {
                backgroundColor += c("#0096C9", .5)
            }
        }
        fail {
            backgroundColor += c("#FF5722", .5)
            and(selected) {
                backgroundColor += c("#0096C9", .5)
            }
        }

        tableView {
            tableRowCell {
                and(highlighted) {
                    backgroundColor += c("yellow")
                }
            }
        }

        overdue {
            textFill = c("red")
            fill = c("blue")
        }
    }

}


