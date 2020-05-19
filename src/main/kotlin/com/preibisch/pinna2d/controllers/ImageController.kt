package com.preibisch.pinna2d.controllers

import com.preibisch.pinna2d.util.randomColor
import com.preibisch.pinna2d.view.MainAnnotationView
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Point2D
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.media.AudioClip
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.util.Duration
import tornadofx.*

class ImageController : Controller() {

    private var circle = Circle()

    var mText = SimpleStringProperty()

    var image = Image(MainAnnotationView::class.java.getResource("/cat.jpg").toExternalForm())

//    private var audioClip = AudioClip(MainView::class.java.getResource("/celestial-sound.wav").toExternalForm())


    fun addCircle(it: MouseEvent, root: Node) {
        val mousePt: Point2D = root.sceneToLocal(it.sceneX, it.sceneY)
        circle = Circle(mousePt.x, mousePt.y, 14.5, Color.ORANGE)

        root.getChildList()!!.add(circle)


    }

    fun addRandomText(){
        mText.set(randomColor())
    }


}