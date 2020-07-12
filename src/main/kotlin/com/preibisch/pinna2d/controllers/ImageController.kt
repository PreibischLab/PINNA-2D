package com.preibisch.pinna2d.controllers

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
import java.awt.Point
import java.io.File


class ImageController : Controller() {
    private var point = Point(0, 0)

    //    var channelsActivation: MutableMap<String, Boolean> = HashMap()
    val annotationController: AnnotationController by inject()
    val scrollFactor = 0.4
    private var circle = Circle()
    private var positionCircle = Circle()
    var circleRadius = 15.0
    private var started = false
    var mText = SimpleStringProperty()
    var imageView = ImageView()
    var currentCategory = 0


    fun start(input: String, mask: String, imageName: String, lut: String) {
        imageView.image = Imp.init(input, mask, lut).toImage()
//        channelsActivation.clear()
//        channelsActivation.putAll(Imp.get().channelsNames)
//        Log.info("Channels: $channelsActivation")
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

    fun clickOnImage(point: Point) {
        val v = Imp.get().getValue(point.x, point.y)

        if (v > 0) {
            annotationController.select(v)
            select(v, point)
        }
        Log.info("x: $point.x y: $point.y - Val :$v")
    }

    fun save(file: File) {
        val result = Imp.get().save(file)
        showPopup(result, "Saving file!", file.path)
    }

    fun select(v: Float, point: Point) {
        if (v > 0) {
            Imp.get().set(v)
            updateImage(point)
        }
    }

    fun updateImage() {
        if (point.x > 0)
            imageView.image = Imp.get().toImage(point)
        else
            imageView.image = Imp.get().toImage()
    }

    fun updateImage(point: Point) {
        this.point = point;
        imageView.image = Imp.get().toImage(point)
    }

    fun select(v: Float) {
        if (v > 0) {
            val point = Imp.get().set(v)
            updateImage(point)
        }
    }

    fun getActivationMap(): MutableMap<String, Boolean> {
        return Imp.get().channelsNames
    }

    fun changeActivationValueFor(text: String, value: Boolean) {
        Imp.get().changeActivationValueFor(text, value)
        updateImage()
    }

}