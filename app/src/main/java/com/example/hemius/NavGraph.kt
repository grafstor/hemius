package com.example.hemius

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.hemius.surfaces.MainSurface
import com.example.hemius.surfaces.SettingsSurface

@Composable
fun NavGraph(
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController,
        startDestination = "main",
        enterTransition  = { fadeIn(animationSpec = tween(200)) },
        exitTransition = { fadeOut(animationSpec = tween(200)) },
    ) {
        navigation(startDestination = "home", route = "main") {
            composable("home") {
                MainSurface(
                    onArchiveClick = {},
                    onSearchClick = {},

                    onToFolderClick = {},
                    onDeleteClick = {},
                    onToArchiveClick = {},

                    onHomeClick = {
                        navController.navigate("home"){
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    onFoldersClick = {},
                    onSettingsClick = { navController.navigate("settings") },

                    onFromFolderDeleteClick = {},
                    onModeToFolderClick = {},
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
                    onFoldersClick = {},
                    onSettingsClick = { navController.navigate("settings") },
                )
            }

        }
    }
}
