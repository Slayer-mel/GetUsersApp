package space.mel.getusersapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import space.mel.getusersapp.data.Results

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(results: Results)

    @Query("SELECT * from user_table")
    fun getAllUsers(): Results?
}