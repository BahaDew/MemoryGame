package com.example.memorygame.domain.impl

import com.example.memorygame.R
import com.example.memorygame.data.LevelEnum
import com.example.memorygame.domain.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GameRepositoryImpl @Inject constructor() : GameRepository {
    private val data = ArrayList<Int>()
    init {
        loadData()
    }
    override fun getCardsByLevel(level: LevelEnum): Flow<List<Int>> = flow {
        val cards = data.shuffled().subList(0, (level.column * level.row) / 2).toMutableList()
        cards.addAll(cards)
        emit(cards.shuffled())
    }
    private fun loadData() {
        data.apply {
            add(R.drawable.fish1)
            add(R.drawable.fish2)
            add(R.drawable.fish3)
            add(R.drawable.fish4)
            add(R.drawable.fish5)
            add(R.drawable.fish6)
            add(R.drawable.fish7)
            add(R.drawable.fish8)
            add(R.drawable.fish9)
            add(R.drawable.fish10)
            add(R.drawable.fish11)
            add(R.drawable.fish12)
            add(R.drawable.fish13)
            add(R.drawable.fish14)
            add(R.drawable.fish15)
            add(R.drawable.fish16)
            add(R.drawable.fish17)
            add(R.drawable.fish18)
            add(R.drawable.fish19)
            add(R.drawable.fish20)
            add(R.drawable.fish21)
            add(R.drawable.fish22)
            add(R.drawable.fish23)
            add(R.drawable.fish24)
        }
    }
}