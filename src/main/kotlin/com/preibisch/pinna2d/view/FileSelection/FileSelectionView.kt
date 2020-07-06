package com.preibisch.pinna2d.view.FileSelection

import tornadofx.*

class FileSelectionView : View("Init Project") {
    override val root = form {
            paddingAll = 10.0
        add(SelectInputPane::class)
        add(ListInputPane::class)
    }
}
