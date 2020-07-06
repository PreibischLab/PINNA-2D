package com.preibisch.pinna2d.controllers

import com.preibisch.pinna2d.model.AnnotationEntryModel
import com.preibisch.pinna2d.tools.Imp
import com.preibisch.pinna2d.tools.Log
import com.preibisch.pinna2d.util.showPopup
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Point2D
import javafx.scene.Node
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import tornadofx.*
import java.io.File


class ImageController : Controller() {
    val annotationController: AnnotationController by inject()
    val scrollFactor = 0.4
    private var circle = Circle()
    private var positionCircle = Circle()
    var circleRadius = 15.0
    private var started = false
    var mText = SimpleStringProperty()
    var imageView = ImageView()
    var currentCategory = 0;

    init {

    }

    fun start(input: String, mask: String, imageName: String,lut:String) {
        imageView.image = Imp.init(input, mask,lut).toImage()
    }

    fun addCircle(it: MouseEvent, root: Node) {
        val mousePt: Point2D = root.sceneToLocal(it.sceneX, it.sceneY)
        circle = Circle(mousePt.x, mousePt.y, circleRadius, Color.ORANGE)
        root.getChildList()!!.add(circle)
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
        val v = Imp.get().getValue(x.toInt(), y.toInt())
        if (v > 0) {
            annotationController.select(v)
            select(v)
        }
        Log.info("x: $x y: $y - Val :$v")
    }

    fun save(file: File) {
        val result =  Imp.get().save(file)
        showPopup(result,"Saving file!",file.path)
    }

    fun select(v: Float) {
        if (v > 0) {
            Imp.get().set(v)
            updateImage()
        }
    }

    fun updateImage() {
        imageView.image = Imp.get().toImage()
    }

}