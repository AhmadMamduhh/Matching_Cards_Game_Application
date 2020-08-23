package com.ahmedmamdouh.matchingcardsgame

import android.graphics.Color
import android.graphics.Color.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var imagesArray = mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fillArray()
    }

    /**
     * This function fills the imagesArray with the image IDs and shuffles the array to ensure
     * random locations for the images during each game session.
     */
    private fun fillArray() {
        imagesArray.add(R.drawable.cat_big)
        imagesArray.add(R.drawable.dog_big)
        imagesArray.add(R.drawable.chicken_big)
        imagesArray.add(R.drawable.snake_big)
        imagesArray.add(R.drawable.cat_big)
        imagesArray.add(R.drawable.dog_big)
        imagesArray.add(R.drawable.chicken_big)
        imagesArray.add(R.drawable.snake_big)

        imagesArray.shuffle()
    }

    fun revealImage(view: View) {
        val cardView = view as CardView
        when(view.id){
            R.id.card1 ->{ img1.setImageResource(imagesArray[0]) }
            R.id.card2 ->{ img2.setImageResource(imagesArray[1]) }
            R.id.card3 ->{ img3.setImageResource(imagesArray[2]) }
            R.id.card4 ->{ img4.setImageResource(imagesArray[3]) }
            R.id.card5 ->{ img5.setImageResource(imagesArray[4]) }
            R.id.card6 ->{ img6.setImageResource(imagesArray[5]) }
            R.id.card7 ->{ img7.setImageResource(imagesArray[6]) }
            R.id.card8 ->{ img8.setImageResource(imagesArray[7]) }

        }
    }

    /**
     * This method is responsible for restarting the game state. It hides all revealed images and
     * reshuffles their positions. The scores are reset.
     */
    fun restartGame(view: View) {
        triesTextView.text = "Tries: 0"
        timeTextView.text = "Time: 0"
        img1.setImageResource(android.R.color.transparent)
        img2.setImageResource(android.R.color.transparent)
        img3.setImageResource(android.R.color.transparent)
        img4.setImageResource(android.R.color.transparent)
        img5.setImageResource(android.R.color.transparent)
        img6.setImageResource(android.R.color.transparent)
        img7.setImageResource(android.R.color.transparent)
        img8.setImageResource(android.R.color.transparent)

        imagesArray.shuffle()
    }
}