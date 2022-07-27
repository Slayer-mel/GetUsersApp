package space.mel.getusersapp.di.module

import android.content.Context
import org.koin.dsl.module
import space.mel.getusersapp.UserDataBase
import space.mel.getusersapp.dao.UsersDao

val roomModule = module {
    single { provideDao(get()) }
}

fun provideDao(context: Context):UsersDao = UserDataBase.getDatabase(context).userDao()


