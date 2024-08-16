package com.example.memorygame.presentation.screen.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.memorygame.R
import com.example.memorygame.databinding.DialogWinBinding

class WinDialogFr : DialogFragment(R.layout.dialog_win) {
    private val binding : DialogWinBinding by viewBinding(DialogWinBinding::bind)
    private var onClick : (() -> Unit)? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnHome.setOnClickListener { onClick?.invoke() }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
    }

    fun setOnClick(onClick : () -> Unit) {
        this.onClick = onClick
    }
}