package com.gani.lib.ui.view

interface IView {
//    top padding is 25px
//    right padding is 50px
//    bottom padding is 75px
//    left padding is 100px
    fun padding(left: Int?, top: Int?, right: Int?, bottom: Int?): IView
    fun margin(left: Int?, top: Int?, right: Int?, bottom: Int?): IView
}
