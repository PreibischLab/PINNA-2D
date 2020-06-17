package com.preibisch.pinna2d.view

import com.preibisch.pinna2d.util.MASK_PATH
import com.preibisch.pinna2d.util.TEST_IMAGE
import javafx.scene.image.Image
import tornadofx.*

class MaskImageSelectionView : View("My View") {
    var masksView : DataGrid<String> by singleAssign()
    override val root = hbox {
        prefHeight = 600.0
        prefWidth = 800.0
        borderpane{
           left =  imageview("/cat.jpg"){
               fitHeightProperty().bind(parent.prefHeight(100.0).toProperty())
               fitWidthProperty().bind(parent.prefWidth(100.0).toProperty())
           }
//           center =  imageview("/cat.jpg"){
//               prefWidth = 200.0
//           }
           right =  imageview("/cat.jpg")
//            imageview(MASK_PATH)
//            imageview(MASK_PATH)
        }
        vbox {

        }
    }
}
