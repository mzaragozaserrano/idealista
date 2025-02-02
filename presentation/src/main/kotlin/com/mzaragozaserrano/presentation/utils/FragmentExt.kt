package com.mzaragozaserrano.presentation.utils

import androidx.fragment.app.Fragment
import com.mzaragozaserrano.presentation.base.BaseActivity

fun Fragment.hideProgressDialog() {
    (activity as BaseActivity<*>).hideProgressDialog()
}

fun Fragment.showProgressDialog() {
    (activity as BaseActivity<*>).showProgressDialog()
}