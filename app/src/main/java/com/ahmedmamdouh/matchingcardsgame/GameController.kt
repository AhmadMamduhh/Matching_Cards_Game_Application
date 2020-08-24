package com.ahmedmamdouh.matchingcardsgame

import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
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
    lateinit var soundCat: MediaPlayer
    lateinit var soundDog : MediaPlayer
    lateinit var soundSnake : MediaPlayer
    lateinit var soundChicken : MediaPlayer



}