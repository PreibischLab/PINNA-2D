package com.preibisch.pinna2d.controllers

import com.preibisch.pinna2d.tools.Imp
import com.preibisch.pinna2d.tools.Log
import com.preibisch.pinna2d.util.randomColor
import com.preibisch.pinna2d.view.MainAnnotationView
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Point2D
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import org.opencv.core.Mat
import tornadofx.*
import java.io.File
import kotlin.math.roundToInt


class ImageController : Controller() {
    val scrollFactor = 0.4
    private var circle = Circle()
    private var positionCircle = Circle()
    var circleRadius = 15.0
    private var started = false
    var mText = SimpleStringProperty()

    var image: Image

    init {
        val input_path = MainAnnotationView::class.java.getResource("/img.tif").path
        val mask_path = MainAnnotationView::class.java.getResource("/mask.tif").path



        println(input_path)
//        CV2.init(input_path,mask_path)
//        println(mat.height())
//        println(mat.width())
//        image = CV2.getGUIImage()
        image = Imp.init(input_path,mask_path).toImage()

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

    fun clickOnImage(x: Double, y: Double) {
//        val v : Float  = CV2.getValue(x.toInt(), y.toInt())
        val v = Imp.get().getValue(x.toInt(),y.toInt())
        Log.info("x: $x y: $y - Val :$v")
    }

}