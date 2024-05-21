package com.example.hemius.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Settings(
    @PrimaryKey val id: Int = 0,
    val isDarkTheme: Boolean
)