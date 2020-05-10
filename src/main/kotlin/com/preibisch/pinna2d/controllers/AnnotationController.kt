package com.preibisch.pinna2d.controllers

import com.preibisch.pinna2d.view.MainView
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Point2D
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import javafx.scene.media.AudioClip
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.util.Duration
import tornadofx.*

class AnnotationController : Controller() {

    private var circle = Circle()

    var mText = SimpleStringProperty()

//    private var audioClip = AudioClip(MainView::class.java.getResource("/celestial-sound.wav").toExternalForm())

    private val colorList: List<String> = listOf("#81ecec", "#55efc4", "#74b9ff","#a29bfe","#b2bec3","#fab1a0","#fd79a8")

    fun addCircle(it: MouseEvent, root: Node) {
        val mousePt: Point2D = root.sceneToLocal(it.sceneX, it.sceneY)
        circle = Circle(mousePt.x, mousePt.y, 14.5, Color.ORANGE)
//        circle.apply {
//            animateFill(Duration.seconds(1.19), c(randomColor()),Color.TRANSPARENT)
//        }

        root.getChildList()!!.add(circle)

//        if(audioClip.isPlaying){
//            audioClip.volumeProperty().value = 0.3
//            audioClip.panProperty().value = 1.0
//        }else{
//            audioClip.volumeProperty().value = 0.8
//            audioClip.play()
//        }

    }

    fun addRandomText(){
        mText.set(randomColor())
    }

    fun <T> ranomValue(list: List<T>): T{
        val listSize: Int = list.size
        val randomNum: Int = (0 until listSize).shuffled().last()
        return list[randomNum]
    }

    private fun randomColor(): String {
        return ranomValue(colorList)
    }
}