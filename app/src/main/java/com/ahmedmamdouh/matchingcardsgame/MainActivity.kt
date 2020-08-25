package com.ahmedmamdouh.matchingcardsgame


import android.content.Intent
import android.content.res.Configuration
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.ahmedmamdouh.matchingcardsgame.GameController.soundCat
import com.ahmedmamdouh.matchingcardsgame.GameController.soundChicken
import com.ahmedmamdouh.matchingcardsgame.GameController.soundDog
import com.ahmedmamdouh.matchingcardsgame.GameController.soundSnake
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
        loadSounds()
    }

//    /**
//     * Handling orientation changes
//     */
//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
//            setContentView(R.layout.activity_main)
//        }
//        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            setContentView(R.layout.activity_main)
//        }
//    }

    override fun onStart() {
        super.onStart()
        loadSounds()
    }

    override fun onStop() {
        super.onStop()
        releaseSounds()
    }

    /**
     * Loading the audio files into variables
     */
    private fun loadSounds() {
        soundCat = MediaPlayer.create(this, R.raw.cat)
        soundDog = MediaPlayer.create(this, R.raw.dog)
        soundSnake = MediaPlayer.create(this, R.raw.snake)
        soundChicken = MediaPlayer.create(this, R.raw.chicken)
    }

    /**
     * Releasing the audio files from the memory
     */
    private fun releaseSounds() {
        soundCat.release()
        soundDog.release()
        soundChicken.release()
        soundSnake.release()
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

        // Checking if game has been restarted
        if(GameController.gameRestarted)
            GameController.gameRestarted = false

        // Checking if there's a sound file playing
        if(isSoundPlaying())
            return

        for (i in 0 until cardsArray.size) {
            if (cardsArray[i].id == cardView.id) {

                // Play the sound but only under certain conditions
                if (!GameController.secondClickFlag)
                    if (GameController.firstClickFlag)
                        playSound(imagesArray[i])
                    else if (cardView != GameController.previousCardView) {
                        playSound(imagesArray[i])
                    }

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
                    if (cardView.equals(GameController.previousCardView) || GameController.secondClickFlag) {
                        return
                    }
                    imageViewsArray[i].setImageResource(imagesArray[i])
                    GameController.secondClickFlag = true

                    // Delay so that the user can have enough time to memorize the locations of the images
                    object : CountDownTimer(1000, 1) {
                        override fun onFinish() {

                            if (!GameController.gameRestarted) {

                                if (GameController.previousImageShown == imagesArray[i]) {
                                    GameController.previousCardView.visibility = View.INVISIBLE
                                    cardView.visibility = View.INVISIBLE
                                    GameController.score += 1
                                } else {
                                    GameController.previousImageView.setImageResource(android.R.color.transparent)
                                    imageViewsArray[i].setImageResource(android.R.color.transparent)
                                }
                                GameController.triesCounter += 1
                                triesTextView.text = "Tries: ${GameController.triesCounter}"
                                GameController.firstClickFlag = true
                                GameController.secondClickFlag = false
                            } else
                                GameController.gameRestarted = false

                            // Checking game ending condition
                            if(GameController.score == GameController.MAX_SCORE){
                               val intent = Intent(this@MainActivity, ResultsActivity::class.java)
                                    .apply { putExtra("tries", GameController.triesCounter )
                                    putExtra("time", chronometer.text.toString()) }
                                GameController.resetGameState()
                                startActivity(intent)
                                finish()

                            }
                        }

                        override fun onTick(p0: Long) {

                        }

                    }.start()

                }


            }
        }

    }

    /**
     * This function is responsible for checking if there is any sound files being played.
     * Returns true if there's at least one audio file playing. Returns false otherwise.
     */
    private fun isSoundPlaying(): Boolean {
        if(soundCat.isPlaying or soundDog.isPlaying or soundChicken.isPlaying or soundSnake.isPlaying)
            return true

        return false
    }

    /**
     * This function is responsible for playing the appropriate sound file for the animal clicked.
     * @param i : the ID of the image of the animal which will be used to identify the correct sound
     * file to play
     */
    private fun playSound(i: Int) {

        when (i) {
            R.drawable.cat_big -> soundCat.start()
            R.drawable.dog_big -> soundDog.start()
            R.drawable.chicken_big -> soundChicken.start()
            R.drawable.snake_big -> soundSnake.start()
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
        GameController.resetGameState()

        for (i in 0 until imageViewsArray.size) {
            imageViewsArray[i].setImageResource(android.R.color.transparent)
            cardsArray[i].visibility = View.VISIBLE
        }

        imagesArray.shuffle()
    }


}