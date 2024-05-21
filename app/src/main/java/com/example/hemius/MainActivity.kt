@file:OptIn(ExperimentalFoundationApi::class)

package com.example.hemius

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import androidx.room.Room

import com.example.hemius.database.HemiusDatabase
import com.example.hemius.database.vm.ThingViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException



class MainActivity : ComponentActivity() {

//    private val db by lazy {
//        Room.databaseBuilder(
//            applicationContext,
//            HemiusDatabase::class.java,
//            "hemius.db"
//        )
////            .allowMainThreadQueries()
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//
//    private val viewModel by viewModels<ThingViewModel>(
//        factoryProducer = {
//            object : ViewModelProvider.Factory {
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    return ThingViewModel(db.thingDao, db.folderDao, db.settingsDao) as T
//                }
//            }
//        }
//    )

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            HemiusDatabase::class.java,
            "hemius.db"
        )
//            .allowMainThreadQueries() // Avoid allowing main thread queries
            .fallbackToDestructiveMigration()
            .build()
    }

    private val viewModel by viewModels<ThingViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ThingViewModel(db.thingDao, db.folderDao, db.settingsDao, applicationContext) as T
                }
            }
        }
    )

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        if (!hasRequiredPermissions()) {
//            ActivityCompat.requestPermissions(
//                this, CAMERAX_PERMISSIONS, 0
//            )
//        }
//        setContent {
//            val state by viewModel.state.collectAsState()
//            val onEvent = viewModel::onEvent
//            HemiusApp(
//                state=state,
//                onEvent = onEvent,
//                finishActivity = { finish() },
//                applicationContext=applicationContext
//            )
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }
        setContent {
            val state by viewModel.state.collectAsState()
            val onEvent = viewModel::onEvent
            HemiusApp(
                state = state,
                onEvent = onEvent,
                finishActivity = { finish() },
                applicationContext = applicationContext
            )
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
        )
    }
}
