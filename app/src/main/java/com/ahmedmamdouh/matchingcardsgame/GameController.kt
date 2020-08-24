package com.ahmedmamdouh.matchingcardsgame

import android.widget.ImageView
import androidx.cardview.widget.CardView

object GameController {
    lateinit var previousImageView: ImageView
    lateinit var previousCardView: CardView
    var gameStartedFlag = true
    var firstClickFlag = true
    var secondClickFlag: Boolean  = false
    var previousImageShown : Int = -1
    var triesCounter = 0
    var delayDoneFlag = false

}