package com.ahmedmamdouh.matchingcardsgame

import android.media.MediaPlayer
import android.widget.ImageView
import androidx.cardview.widget.CardView

object GameController {
    lateinit var previousImageView: ImageView
    lateinit var previousCardView: CardView
    var gameStartedFlag = true
    var firstClickFlag = true
    var secondClickFlag: Boolean  = false
    var gameRestarted: Boolean = false
    var previousImageShown : Int = -1
    var triesCounter = 0
    var score : Int = 0
    const val MAX_SCORE = 4
    var timeWhenStopped : Long = 0
    lateinit var soundCat: MediaPlayer
    lateinit var soundDog : MediaPlayer
    lateinit var soundSnake : MediaPlayer
    lateinit var soundChicken : MediaPlayer
    lateinit var soundCelebration : MediaPlayer

    /**
     * This method is responsible for resetting the values of all variables to their initial values.
     */
    fun resetGameState(){
        gameStartedFlag = true
        triesCounter = 0
        firstClickFlag = true
        secondClickFlag = false
        gameRestarted = true
        score = 0
    }



}