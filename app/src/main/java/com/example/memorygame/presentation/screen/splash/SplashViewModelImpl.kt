package com.example.memorygame.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorygame.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val navigator: AppNavigator
) : ViewModel(), SplashViewModel {
    override fun openSplash() {
        viewModelScope.launch {
            delay(800)
            navigator.navigateTo(SplashScreenDirections.actionSplashScreenToMenuScreen())
        }
    }
}