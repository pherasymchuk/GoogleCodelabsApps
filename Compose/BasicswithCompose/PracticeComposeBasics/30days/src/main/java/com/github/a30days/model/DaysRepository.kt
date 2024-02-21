package com.github.a30days.model

import com.github.a30days.R

class DaysRepository {
    fun getAllDays(): List<Day> {
        return listOf(
            Day(1, R.string.day_1_goal, R.drawable.daypicture1, R.string.day_1_description),
            Day(2, R.string.day_1_goal, R.drawable.daypicture2, R.string.day_1_description),
            Day(3, R.string.day_1_goal, R.drawable.daypicture3, R.string.day_1_description),
            Day(4, R.string.day_1_goal, R.drawable.daypicture4, R.string.day_1_description),
            Day(5, R.string.day_1_goal, R.drawable.daypicture5, R.string.day_1_description),
            Day(6, R.string.day_1_goal, R.drawable.daypicture6, R.string.day_1_description),
        )
    }
}
