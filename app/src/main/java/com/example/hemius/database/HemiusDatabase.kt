package com.example.hemius.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hemius.database.dao.ThingDao
import com.example.hemius.database.entities.Thing


@Database(
    entities = [Thing::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class HemiusDatabase : RoomDatabase() {
    abstract val thingDao: ThingDao
}