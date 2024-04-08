package com.example.hemius

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.hemius.ui.theme.HemiusTheme

@Composable
fun HemiusApp (
    finishActivity: () -> Unit
){
    HemiusTheme {
        val navController = rememberNavController()
        NavGraph(
            finishActivity = finishActivity,
            navController = navController,
        )
    }
}