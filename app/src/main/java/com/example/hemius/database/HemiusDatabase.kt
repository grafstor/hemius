package com.example.hemius.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hemius.database.dao.FolderDao
import com.example.hemius.database.dao.SettingsDao
import com.example.hemius.database.dao.ThingDao
import com.example.hemius.database.entities.Folder
import com.example.hemius.database.entities.FolderWithImages
import com.example.hemius.database.entities.Settings
import com.example.hemius.database.entities.Thing
import com.example.hemius.database.entities.ThingFolderCrossRef

@Database(
    entities = [
        Thing::class,
        Folder::class,
        ThingFolderCrossRef::class,
        Settings::class,
               ],

    views = [FolderWithImages::class],
    version = 7
)
@TypeConverters(Converters::class)
abstract class HemiusDatabase : RoomDatabase() {
    abstract val thingDao: ThingDao
    abstract val folderDao: FolderDao
    abstract val settingsDao: SettingsDao
}
