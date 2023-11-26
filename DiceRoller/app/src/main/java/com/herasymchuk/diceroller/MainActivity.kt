package com.herasymchuk.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {
    /**
     * This method is called when the Activity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the Button in the layout
        val rollButton: Button = findViewById(R.id.button)
        rollButton.setOnClickListener {
            rollDice()
            rollDice2()
        }

        rollDice()
        rollDice2()
    }

    /**
     * Roll the dice and update the screen with the result.
     */
    private fun rollDice() {
        val dice = Dice(6)
        val diceRoll = dice.roll()

        val diceImage = findViewById<ImageView>(R.id.imageView)
        val drawableResource = getDrawableResource(diceRoll)
        diceImage.setImageResource(drawableResource)

        diceImage.contentDescription = drawableResource.toString()
    }

    private fun rollDice2() {
        val dice = Dice(6)
        val diceRoll = dice.roll()

        val diceImage = findViewById<ImageView>(R.id.imageView2)
        diceImage.setImageResource(getDrawableResource(diceRoll))
    }

    private fun getDrawableResource(diceRoll: Int) = when (diceRoll) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

}

/**
 * Dice with a fixed number of sides.
 */
class Dice(private val numSides: Int) {
    /**
     * Do a random dice roll and return the result.
     */
    fun roll(): Int {
        return (1..numSides).random()
    }
}