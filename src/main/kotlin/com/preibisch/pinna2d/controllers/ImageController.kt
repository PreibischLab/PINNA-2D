package com.preibisch.pinna2d.controllers

import com.preibisch.pinna2d.tools.Imp
import com.preibisch.pinna2d.tools.Log
import com.preibisch.pinna2d.util.randomColor
import com.preibisch.pinna2d.view.MainAnnotationView
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Point2D
import javafx.scene.Node
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import tornadofx.*


class ImageController : Controller() {
    val annotationController : AnnotationController by inject()
    val scrollFactor = 0.4
    private var circle = Circle()
    private var positionCircle = Circle()
    var circleRadius = 15.0
    private var started = false
    var mText = SimpleStringProperty()
    var imageView = ImageView()
//    var image: Image

    init {
        val input_path = MainAnnotationView::class.java.getResource("/img.tif").path
        val mask_path = MainAnnotationView::class.java.getResource("/mask.tif").path

        println(input_path)

        imageView.image = Imp.init(input_path,mask_path).toImage()
        annotationController.imgAnnotation("img.tif",Imp.get().min, Imp.get().max)
    }


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
        Imp.get().set(v)
        Imp.get().add(v,255)

//        Imp.get().set(v,250)

        imageView.image = Imp.get().toImage()
//        imageView.rep
        Log.info("x: $x y: $y - Val :$v")
    }

}