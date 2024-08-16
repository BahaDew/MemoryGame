package com.example.memorygame.navigation

import androidx.navigation.NavDirections

interface AppNavigator {

    suspend fun navigateTo(directions: NavDirections)

    suspend fun navigateUp()
}