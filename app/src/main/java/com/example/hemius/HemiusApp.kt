package com.example.hemius

import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hemius.database.HemiusDatabase
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.example.hemius.ui.theme.HemiusTheme

@Composable
fun HemiusApp (
    finishActivity: () -> Unit,
    state: ThingState,
    onEvent: (ThingEvent) -> Unit,
    applicationContext : Context,
){
    HemiusTheme {
        val navController = rememberNavController()
        NavGraph(
            state = state,
            onEvent = onEvent,
            finishActivity = finishActivity,
            navController = navController,
            applicationContext=applicationContext
        )
    }
}