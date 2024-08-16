package com.example.memorygame.presentation.screen.game

import com.example.memorygame.data.LevelEnum
import kotlinx.coroutines.flow.StateFlow

interface GameViewModel {
    val cardsList : StateFlow<List<Int>>
    val menuBg : StateFlow<Int>

    fun openGameScreen(level: LevelEnum)

    fun onClickMenu()

    fun onClickRefresh()
}