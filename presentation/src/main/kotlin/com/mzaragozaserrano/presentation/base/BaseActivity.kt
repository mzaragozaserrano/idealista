package com.mzaragozaserrano.presentation.base

import androidx.appcompat.app.AppCompatActivity
import androidx.core.bundle.Bundle
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected abstract val binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSetup()
        setContentView(binding.root)
    }

    private fun initSetup() {
        setUpListeners()
        setUpView()
    }

    open fun setUpListeners() {}
    open fun setUpView() {}

}