package com.example.memorygame.presentation.screen.game

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.memorygame.R
import com.example.memorygame.databinding.ScreenGameBinding
import com.example.memorygame.presentation.screen.dialog.WinDialogFr
import com.example.memorygame.utils.closeAnim
import com.example.memorygame.utils.gameLogger
import com.example.memorygame.utils.myApply
import com.example.memorygame.utils.openAnim
import com.example.memorygame.utils.removeAnim
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.ldralighieri.corbind.view.clicks

@AndroidEntryPoint
class GameScreen : Fragment(R.layout.screen_game) {
    private val viewModel: GameViewModel by viewModels<GameViewModelImpl>()
    private val binding by viewBinding(ScreenGameBinding::bind)
    private val cards = ArrayList<ImageView>()
    private val navArgs: GameScreenArgs by navArgs()
    private var firstOpenIndex = -1
    private var secondOpenIndex = -1
    private var findCount = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        super.onViewCreated(view, savedInstanceState)
        cardContainer.post {
            viewModel.openGameScreen(navArgs.level)
            initView()
        }
        initFlow()
    }

    private fun initFlow() = binding.myApply {
        btnMenu
            .clicks()
            .onEach { viewModel.onClickMenu() }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)

        viewModel.menuBg
            .onEach { btnMenu.setBackgroundResource(it) }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)

        viewModel.cardsList
            .onEach { loadCards(it) }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

    private fun initView() = binding.myApply {

    }

    private fun loadCards(list: List<Int>) = binding.myApply {
        val containerWidth = cardContainer.width
        val containerHeight = cardContainer.height
        val rowCount = navArgs.level.row
        val columnCount = navArgs.level.column
        cardContainer.rowCount = rowCount
        cardContainer.columnCount = columnCount
        for (i in list.indices) {
            val img = ImageView(requireContext())
            img.setImageResource(R.drawable.bg_fishs_close)
            img.setBackgroundResource(R.drawable.bg_card)
            cardContainer.addView(img)
            cards.add(img)
            (img.layoutParams as GridLayout.LayoutParams).apply {
                this.width = containerWidth / columnCount - 10
                this.height = containerHeight / rowCount - 10
                setMargins(0, 10, 10, 0)
            }
            img.isClickable = false
            img.openAnim { }
            img.tag = Pair(false, list[i])
            img.setOnClickListener {
                if (firstOpenIndex != -1 && secondOpenIndex != -1) return@setOnClickListener
                onCLickImg(img, i)
            }
        }
        lifecycleScope.launch {
            delay(1500)
            cards.forEach { it.closeAnim { } }
        }
    }

    private fun onCLickImg(img: ImageView, i: Int) {
        val tag = img.tag as Pair<Boolean, Int>
        if (tag.first) {
//            img.closeAnim {
//                if (secondOpenIndex != -1) {
//                    secondOpenIndex = -1
//                } else firstOpenIndex = -1
//            }
        } else {
            img.openAnim {}
            if (firstOpenIndex == -1) {
                firstOpenIndex = i
            } else {
                secondOpenIndex = i
                lifecycleScope.launch {
                    delay(1500)
                    gameLogger(" first = $firstOpenIndex second = $secondOpenIndex")
                    if (!checkCorrect()) {
                        cards[firstOpenIndex].closeAnim { }
                        cards[secondOpenIndex].closeAnim {
                            firstOpenIndex = -1
                            secondOpenIndex = -1
                        }
                    }
                }
            }
        }
    }

    private fun checkCorrect(): Boolean {
        if (firstOpenIndex == -1 || secondOpenIndex == -1) return false
        val tag1 = cards[firstOpenIndex].tag as Pair<Boolean, Int>
        val tag2 = cards[secondOpenIndex].tag as Pair<Boolean, Int>
        if (tag1.second == tag2.second) {
            cards[firstOpenIndex].removeAnim { }
            cards[secondOpenIndex].removeAnim {
                firstOpenIndex = -1
                secondOpenIndex = -1
                findCount += 2
                checkWin()
            }
            return true
        }
        return false
    }

    private fun checkWin() {
        if (findCount == navArgs.level.row * navArgs.level.column) {
            val dialog = WinDialogFr()
            dialog.isCancelable = false
            dialog.setOnClick {
                dialog.dismiss()
                viewModel.onClickMenu()
            }
            dialog.show(childFragmentManager, "")
        }
    }
}