package com.gani.lib.ui.view

interface IView {
    fun padding(left: Int?, top: Int?, right: Int?, bottom: Int?): IView
    fun margin(left: Int?, top: Int?, right: Int?, bottom: Int?): IView
}
