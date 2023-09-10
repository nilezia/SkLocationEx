package example.lizardo.sklocationex

import androidx.room.Database
import androidx.room.RoomDatabase
import example.lizardo.sklocationex.data.database.LocationDao
import example.lizardo.sklocationex.data.database.LocationData

@Database(entities = [LocationData::class], version = 1,exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}