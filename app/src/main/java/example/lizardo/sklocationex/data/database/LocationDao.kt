package example.lizardo.sklocationex.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LocationDao {
    @Query("SELECT * FROM locationdata")
    fun getAll(): List<LocationData>

    @Insert
    fun insertAll(vararg locationdata: LocationData)

    @Query("delete from locationdata")
    fun deleteAllData()
}