package com.ahmedmamdouh.matchingcardsgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val imagesArray = mutableListOf<Int>()
    private val imageViewsArray = mutableListOf<ImageView>()
    private val cardsArray = mutableListOf<CardView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fillImagesArray()
        fillImageViewsArray()
        fillCardsArray()

    }

    /**
     * This function fills the cardsArray with the CardViews.
     */
    private fun fillCardsArray() {
        cardsArray.add(card1)
        cardsArray.add(card2)
        cardsArray.add(card3)
        cardsArray.add(card4)
        cardsArray.add(card5)
        cardsArray.add(card6)
        cardsArray.add(card7)
        cardsArray.add(card8)
    }

    /**
     * This function fills the imageViewsArray with the image views of the cards.
     */
    private fun fillImageViewsArray() {
        imageViewsArray.add(img1)
        imageViewsArray.add(img2)
        imageViewsArray.add(img3)
        imageViewsArray.add(img4)
        imageViewsArray.add(img5)
        imageViewsArray.add(img6)
        imageViewsArray.add(img7)
        imageViewsArray.add(img8)
    }

    /**
     * This function fills the imagesArray with the image IDs and shuffles the array to ensure
     * random locations for the images during each game session.
     */
    private fun fillImagesArray() {
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
        for (i in 0 until cardsArray.size) {
            if (cardsArray[i].id == cardView.id) {

                // Start counting the time until game completion or restart
                if (GameController.gameStartedFlag) {
                    chronometer.base = SystemClock.elapsedRealtime()
                    chronometer.start()
                    GameController.gameStartedFlag = false
                }

                // Checking if it's the first clicked card
                if (GameController.firstClickFlag) {
                    imageViewsArray[i].setImageResource(imagesArray[i])
                    GameController.previousImageShown = imagesArray[i]
                    GameController.previousCardView = cardView
                    GameController.previousImageView = imageViewsArray[i]
                    GameController.firstClickFlag = false
                } else {
                    if(cardView.equals(GameController.previousCardView)){
                        return
                    }
                    imageViewsArray[i].setImageResource(imagesArray[i])
                    if (GameController.previousImageShown == imagesArray[i]) {
                        GameController.previousCardView.visibility = View.INVISIBLE
                        cardView.visibility = View.INVISIBLE
                    } else {
                        GameController.previousImageView.setImageResource(android.R.color.transparent)
                        imageViewsArray[i].setImageResource(android.R.color.transparent)
                    }
                    GameController.triesCounter += 1
                    triesTextView.text = "Tries: ${GameController.triesCounter}"
                    GameController.firstClickFlag = true


                }


            }
        }

    }

    /**
     * This method is responsible for restarting the game state. It hides all revealed images and
     * reshuffles their positions. The scores are reset.
     */
    fun restartGame(view: View) {
        triesTextView.text = "Tries: 0"
        chronometer.stop()
        chronometer.text = "00:00"
        GameController.gameStartedFlag = true
        GameController.triesCounter = 0


        for (i in 0 until imageViewsArray.size) {
            imageViewsArray[i].setImageResource(android.R.color.transparent)
            cardsArray[i].visibility = View.VISIBLE
        }

        imagesArray.shuffle()
    }
}