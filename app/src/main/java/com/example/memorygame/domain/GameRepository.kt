package com.example.memorygame.domain

import com.example.memorygame.data.LevelEnum
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getCardsByLevel(level : LevelEnum) : Flow<List<Int>>
}