package com.glib.core.ui.view

interface IView {
    fun bgColor(color: Int): IView
    fun width(width: Int?): IView
    fun height(height: Int?): IView
    fun padding(l: Int?, t: Int?, r: Int?, b: Int?): IView
    fun margin(l: Int?, t: Int?, r: Int?, b: Int?): IView
}
