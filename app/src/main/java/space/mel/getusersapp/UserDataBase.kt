package space.mel.getusersapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import space.mel.getusersapp.dao.UsersDao
import space.mel.getusersapp.data.*

@Database(
    entities = [
        Results::class
    ], version = 1, exportSchema = true
)
@TypeConverters(
    NameConverter::class,
    ResultConverters::class,
    LocationConverter::class,
    LoginConverter::class,
    DobConverter::class,
    IDConverter::class,
    PictureConverter::class
)
abstract class UserDataBase : RoomDatabase() {

    abstract fun userDao(): UsersDao

    companion object {

        @Volatile
        private var INSTANCE: UserDataBase? = null

        fun getDatabase(context: Context): UserDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): UserDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                UserDataBase::class.java,
                "user_database",
            ).build()
        }
    }
}

