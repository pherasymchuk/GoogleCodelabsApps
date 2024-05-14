package com.example.busschedule

import android.app.Application
import com.example.busschedule.data.AppContainer

class BusScheduleApplication : Application() {
    val appContainer: AppContainer by lazy { AppContainer.Default(this) }
}
