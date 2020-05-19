package com.preibisch.pinna2d.controllers

import com.preibisch.pinna2d.tools.CV2
import com.preibisch.pinna2d.util.format
import com.preibisch.pinna2d.util.randomColor
import com.preibisch.pinna2d.view.MainAnnotationView
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Point2D
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import org.opencv.core.Mat
import tornadofx.*
import java.util.*


class ImageController : Controller() {
    val scrollFactor = 0.4
    private var circle = Circle()
    private var positionCircle = Circle()
    var circleRadius = 15.0
    private var started = false
    var mText = SimpleStringProperty()

    var image: Image

    init {
        val path = MainAnnotationView::class.java.getResource("/cat.jpg").path
        println(path)
        val mat: Mat = CV2.readImg(path)
        println(mat.height())
        println(mat.width())
        image = CV2.matToImg(mat)

    }
//    private var audioClip = AudioClip(MainView::class.java.getResource("/celestial-sound.wav").toExternalForm())


    fun addCircle(it: MouseEvent, root: Node) {
        val mousePt: Point2D = root.sceneToLocal(it.sceneX, it.sceneY)
        circle = Circle(mousePt.x, mousePt.y, circleRadius, Color.ORANGE)
        root.getChildList()!!.add(circle)
    }

    fun addRandomText() {
        mText.set(randomColor())
    }

    fun positionCicle(it: MouseEvent, root: Node) {
        if (started)
            root.getChildList()!!.removeAt(1)
        else
            started = true
        val mousePt: Point2D = root.sceneToLocal(it.sceneX, it.sceneY)
        positionCircle = Circle(mousePt.x, mousePt.y, circleRadius, Color.TRANSPARENT)
        positionCircle.stroke = Color.GREY
//        positionCircle.apply {
//            animateStroke(Duration.seconds(0.02), Color.GREY,Color.TRANSPARENT)
//        }
        root.getChildList()!!.add(1, positionCircle)
    }

}