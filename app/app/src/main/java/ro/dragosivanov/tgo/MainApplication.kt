package ro.dragosivanov.tgo

import android.app.Application
import ro.dragosivanov.tgo.di.AppContainer

class MainApplication: Application() {

    val appContainer = AppContainer()
}
