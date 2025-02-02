package com.mzaragozaserrano.presentation.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.mzaragozaserrano.presentation.R

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {

    protected abstract val binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = binding.root

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
         dialog.window?.setBackgroundDrawable(context?.let { context ->
             ContextCompat.getDrawable(context, R.drawable.dialog_fragment_insets)
         })
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSetup()
    }

    private fun initSetup() {
        setUpListeners()
        setUpObservers()
        setUpView()
    }

    open fun setUpListeners() {}
    open fun setUpObservers() {}
    open fun setUpView() {}

}