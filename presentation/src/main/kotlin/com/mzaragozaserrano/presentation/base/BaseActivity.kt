package com.mzaragozaserrano.presentation.base

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.bundle.Bundle
import androidx.viewbinding.ViewBinding
import com.airbnb.lottie.LottieAnimationView
import com.mzaragozaserrano.presentation.dialog.ErrorDialog
import com.mzs.core.R

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected abstract val binding: VB

    open var loadingRaw: Int? = null

    private val animationLayoutInflater: View by lazy {
        View.inflate(applicationContext, R.layout.core_layout_loading, null)
    }

    private val animation: LottieAnimationView by lazy {
        animationLayoutInflater.findViewById(R.id.animation_view_loader)
    }

    private val progressDialog: AlertDialog by lazy {
        AlertDialog.Builder(this, R.style.CoreDialogTransparent)
            .setView(animationLayoutInflater)
            .setCancelable(false)
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSetup()
        setContentView(binding.root)
        setUpAnimation()
    }

    open fun setUpListeners() {}
    open fun setUpView() {}

    fun hideError() {
        val fragment = supportFragmentManager.findFragmentByTag(ErrorDialog.ERROR_DIALOG_TAG)
        if (fragment is ErrorDialog) {
            fragment.dismiss()
        }
    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing && loadingRaw != null) {
            animation.cancelAnimation()
            progressDialog.dismiss()
        }
    }

    fun showError(
        title: String,
        message: String,
        buttonText: String,
        onButtonClicked: () -> Unit,
    ) {
        val dialogFragment = ErrorDialog(
            title = title,
            message = message,
            buttonText = buttonText,
            onButtonClicked = onButtonClicked
        )
        dialogFragment.isCancelable = false
        supportFragmentManager.let { fragment ->
            dialogFragment.show(fragment, ErrorDialog.ERROR_DIALOG_TAG)
        }
    }

    fun showProgressDialog() {
        if (progressDialog.isShowing.not() && loadingRaw != null) {
            animation.playAnimation()
            progressDialog.show()
        }
    }

    private fun initSetup() {
        setUpListeners()
        setUpView()
    }

    private fun setUpAnimation() {
        loadingRaw?.let { animation.setAnimation(it) }
    }


}