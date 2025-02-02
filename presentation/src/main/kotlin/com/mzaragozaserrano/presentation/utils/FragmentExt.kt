package com.mzaragozaserrano.presentation.utils

import androidx.fragment.app.Fragment
import com.mzaragozaserrano.presentation.base.BaseActivity

fun Fragment.hideError() {
    (activity as BaseActivity<*>).hideError()
}

fun Fragment.showError(
    title: String,
    message: String,
    buttonText: String,
    onButtonClicked: () -> Unit,
) {
    (activity as BaseActivity<*>).showError(
        buttonText = buttonText,
        message = message,
        title = title,
        onButtonClicked = onButtonClicked
    )
}