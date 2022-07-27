package space.mel.getusersapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import space.mel.getusersapp.di.module.retrofitModule
import space.mel.getusersapp.di.module.roomModule

class App: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {// Koin initialization
            androidContext(this@App) //passing the androidContext to the Koin container
            modules(
                listOf(
                    retrofitModule, roomModule //pass here all of modules
                )
            )
        }
    }
}