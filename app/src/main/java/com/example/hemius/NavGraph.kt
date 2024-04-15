package com.example.hemius

import android.content.Context
import android.content.ContextWrapper
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.example.hemius.surfaces.ArchiveSurface
import com.example.hemius.surfaces.CameraSurface
import com.example.hemius.surfaces.CreateThingSurface
import com.example.hemius.surfaces.FoldersSurface
import com.example.hemius.surfaces.MainSurface
import com.example.hemius.surfaces.SearchSurface
import com.example.hemius.surfaces.SettingsSurface

@Composable
fun NavGraph(
    state: ThingState,
    onEvent: (ThingEvent) -> Unit,
    finishActivity: () -> Unit = {},
    applicationContext : Context,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController,
        startDestination = "main",
        enterTransition  = { fadeIn(animationSpec = tween(150)) },
        exitTransition = { fadeOut(animationSpec = tween(150)) },
        popEnterTransition  = { fadeIn(animationSpec = tween(150)) },
        popExitTransition = { fadeOut(animationSpec = tween(150)) },
    ) {
        navigation(startDestination = "home", route = "main") {
            composable("home") {
                MainSurface(
                    onArchiveClick = { navController.navigate("archive") },
                    onSearchClick = { navController.navigate("search") },

                    onToFolderClick = {},
                    onDeleteClick = { onEvent(ThingEvent.DeleteSelected) },
                    onToArchiveClick = { onEvent(ThingEvent.ArchiveSelected) },

                    onHomeClick = {},
                    onFoldersClick = { navController.navigate("folders") },
                    onSettingsClick = { navController.navigate("settings") },
                    onCameraClick = { navController.navigate("camera") },

                    onFromFolderDeleteClick = {},
                    onModeToFolderClick = {},
                    state = state,
                    onEvent = onEvent,
                )
            }
            composable("settings") {
                SettingsSurface(
                    onBackClick = {
                        navController.navigate("home"){
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    onHomeClick = {
                        navController.navigate("home"){
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    onFoldersClick = { navController.navigate("folders") },
                    onSettingsClick = { navController.navigate("settings") },
                )
            }
            composable("search") {
                SearchSurface(
                )
            }
            composable("archive") {
                ArchiveSurface(
                    onBackClick = {
                        navController.navigate("home"){
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    onHomeClick = {
                        navController.navigate("home"){
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    onFoldersClick = { navController.navigate("folders") },
                    onSettingsClick = { navController.navigate("settings") },
                    state = state,
                    onEvent = onEvent,
                )
            }
            composable("folders") {
                FoldersSurface(
                    onBackClick = {
                        navController.navigate("home"){
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    onHomeClick = {
                        navController.navigate("home"){
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    onFoldersClick = { navController.navigate("folders") },
                    onSettingsClick = { navController.navigate("settings") },
                )
            }
            composable("camera") {
                CameraSurface(
                    onBackClick = {
                        navController.navigate("home"){
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    onCaptureClick = { navController.navigate("creatething") },
                    applicationContext=applicationContext,

                    state = state,
                    onEvent = onEvent,
                )
            }
            composable("creatething") {
                CreateThingSurface(
                    onBackClick = {
                        navController.navigate("home"){
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    onCreateClick = { navController.navigate("home") },
                    onRephotoClick = {
                        navController.navigate("camera"){
                            popUpTo("camera") { inclusive = true }
                        }
                    },
                    state = state,
                    onEvent = onEvent,
                )
            }

        }
    }
}




