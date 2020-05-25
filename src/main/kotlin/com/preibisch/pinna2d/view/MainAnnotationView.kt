package com.preibisch.pinna2d.view

import tornadofx.*


class MainAnnotationView : View("Annotation") {
    private val imageView: ImageAnnotationView by inject()
    private val annotationEditorView: AnnotationEditorView by inject()
    override val root = borderpane() {
        setPrefSize(1000.0,500.0)
        center = imageView.root
        left = annotationEditorView.root
    }

}