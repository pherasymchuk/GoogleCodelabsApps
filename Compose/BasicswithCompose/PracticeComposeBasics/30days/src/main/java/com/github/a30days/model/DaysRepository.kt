package com.github.a30days.model

import com.github.a30days.R

class DaysRepository {
    fun getAllDays(): List<Day> {
        return listOf(
            Day(1, R.string.day_1_goal, R.drawable.daypicture1, R.string.day_1_description),
            Day(1, R.string.day_1_goal, R.drawable.daypicture1, R.string.day_1_description),
            Day(1, R.string.day_1_goal, R.drawable.daypicture1, R.string.day_1_description),
            Day(1, R.string.day_1_goal, R.drawable.daypicture1, R.string.day_1_description),
            Day(1, R.string.day_1_goal, R.drawable.daypicture1, R.string.day_1_description),
            Day(1, R.string.day_1_goal, R.drawable.daypicture1, R.string.day_1_description),
        )
    }
}
