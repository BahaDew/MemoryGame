package com.example.memorygame.navigation

import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigationDispatcher @Inject constructor() : AppNavigator, AppNavigationHandler {
    override val buffer = MutableSharedFlow<AppNavigation>()

    private suspend fun navigate(navigation : AppNavigation) {
        buffer.emit(navigation)
    }

    override suspend fun navigateTo(directions: NavDirections) = navigate {
        navigate(directions)
    }

    override suspend fun navigateUp() = navigate {
        popBackStack()
    }

}