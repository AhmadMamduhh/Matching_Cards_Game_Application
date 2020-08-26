package com.ahmedmamdouh.matchingcardsgame

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_results.*

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        // Playing celebration audio file
        GameController.soundCelebration = MediaPlayer.create(this, R.raw.celebration)
        GameController.soundCelebration.start()

        // Getting score data from the intent and formatting it
        val tries = intent.getIntExtra("tries", 0)
        val time = intent.getStringExtra("time")
        val minutes = time?.substring(0, 2)
        val seconds = time?.substring(3, 5)
        var winingMessage = ""
        var congratsMessage = ""
        if (!minutes.equals("00")) {
            congratsMessage = "Too Slow!"
            if (minutes!![0] == '0') {
                winingMessage = if (minutes!![1] == '1')
                    "You won the game in ${minutes!![1]} minute and $seconds seconds with $tries tries!"
                else
                    "You won the game in ${minutes!![1]} minutes and $seconds seconds with $tries tries!"
            } else
                winingMessage =
                    "You won the game in $minutes minutes and $seconds seconds with $tries tries!"
        } else {
           if (seconds!![0] == '0') {
                winingMessage = "You won the game in ${seconds!![1]} seconds with $tries tries!"
                congratsMessage = "Perfection!"
            }
            else {
                winingMessage = "You won the game in $seconds seconds with $tries tries!"
               congratsMessage = if(seconds.toInt() > 20)
                   "Not Bad!"
               else
                   "Well Done!"
            }
        }

        // Shows the player's game statistics
        resultsText.text = winingMessage

        // Shows how well the player has done
        congratsText.text = congratsMessage

        // Starts a new game session when the restart button is clicked
            restartBtn.setOnClickListener {
                startActivity(Intent(this@ResultsActivity, MainActivity::class.java))
                finish()
            }

        // Exits the application when the exit button is clicked
        exitBtn.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        GameController.soundCelebration.release()
    }

}