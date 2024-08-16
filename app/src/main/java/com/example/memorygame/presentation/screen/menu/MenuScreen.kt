package com.example.memorygame.presentation.screen.menu

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.memorygame.R
import com.example.memorygame.databinding.ScreenMenuBinding
import com.example.memorygame.presentation.screen.dialog.ExitDialog
import com.example.memorygame.utils.myApply
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.ldralighieri.corbind.view.clicks

@AndroidEntryPoint
class MenuScreen : Fragment(R.layout.screen_menu) {
    private val binding by viewBinding(ScreenMenuBinding::bind)
    private val viewModel: MenuViewModel by viewModels<MenuVIewModelImpl>()
    private var time = System.currentTimeMillis()
    val exitDialog = ExitDialog()
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            exitDialog.setOnclickOk {
                exitDialog.dismiss()
                requireActivity().finish()
            }
            exitDialog.show(parentFragmentManager, "exit")
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        initView()
        initFLow()
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    private fun initView() = binding.myApply {
        lifecycleScope.launch {
            btnStartAnim(btnEasy) {}
            delay(500)
            btnStartAnim(btnMedium) {}
            delay(500)
            btnStartAnim(btnHard) {
                btnEasy.isEnabled = true
                btnMedium.isEnabled = true
                btnHard.isEnabled = true
            }
        }
    }

    override fun onStop() {
        super.onStop()
        onBackPressedCallback.remove()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    private fun btnStartAnim(btn: AppCompatButton, block: () -> Unit) {
        btn.animate()
            .setDuration(500)
            .translationX(800f)
            .withEndAction { block.invoke() }
            .start()
    }

    private fun initFLow() = binding.myApply {
        btnEasy.setOnClickListener {
            if (System.currentTimeMillis() - time > 1000) {
                time = System.currentTimeMillis()
                viewModel.onClickEasy()
            }
        }

        btnMedium
            .clicks()
            .onEach {
                if (System.currentTimeMillis() - time > 1000) {
                    time = System.currentTimeMillis()
                    viewModel.onClickMedium()
                }
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)

        btnHard
            .clicks()
            .onEach {
                if (System.currentTimeMillis() - time > 1000) {
                    time = System.currentTimeMillis()
                    viewModel.onClickHard()
                }
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }
}