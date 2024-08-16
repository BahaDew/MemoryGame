package com.example.memorygame.utils

import android.widget.ImageView
import com.example.memorygame.R
import timber.log.Timber

fun <T> T.myApply(block: T.() -> Unit) {
    block(this)
}

fun gameLogger(message: String, tag: String = "BAHA") {
    Timber.tag(tag).d(message)
}

fun ImageView.openAnim(block: () -> Unit) {
    this.isEnabled = false
    this.animate()
        .setDuration(500)
        .rotationY(89f)
        .scaleX(0.55f)
        .scaleY(0.55f)
        .withEndAction {
            this.rotationY = -89f
            val tag = this.tag as Pair<Boolean, Int>
            val src = tag.second
            this.tag = tag.copy(first = true)
            this.setImageResource(src)
            this.animate()
                .withEndAction {
                    block.invoke()
                    this.isEnabled = true
                }
                .setDuration(500)
                .scaleX(1f)
                .scaleY(1f)
                .rotationY(0f)
                .start()
        }
        .start()
}

fun ImageView.closeAnim(block: () -> Unit) {
    this.isEnabled = false
    this.animate()
        .setDuration(500)
        .rotationY(-89f)
        .scaleX(0.55f)
        .scaleY(0.55f)
        .withEndAction {
            this.rotationY = 89f
            val tag = this.tag as Pair<Boolean, Int>
            this.tag = tag.copy(first = false)
            this.setImageResource(R.drawable.bg_fishs_close)
            this.animate()
                .withEndAction {
                    block.invoke()
                    this.isEnabled = true
                }
                .setDuration(500)
                .scaleX(1f)
                .scaleY(1f)
                .rotationY(0f)
                .start()
        }
        .start()
}
fun ImageView.removeAnim(block: () -> Unit) {
    this.isEnabled = false
    this.animate()
        .setDuration(500)
        .scaleX(0f)
        .scaleY(0f)
        .withEndAction {  block.invoke() }
        .start()
}