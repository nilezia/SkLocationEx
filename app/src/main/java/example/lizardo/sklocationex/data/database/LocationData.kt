package example.lizardo.sklocationex.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class LocationData(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo var jsonData: String? = null )
