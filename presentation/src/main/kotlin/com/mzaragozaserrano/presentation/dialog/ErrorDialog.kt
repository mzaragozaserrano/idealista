package com.mzaragozaserrano.presentation.dialog

import androidx.compose.material3.MaterialTheme
import com.mzaragozaserrano.presentation.base.BaseDialogFragment
import com.mzaragozaserrano.presentation.databinding.ErrorDialogBinding
import com.mzaragozaserrano.presentation.theme.IdealistaAppTheme
import com.mzs.core.presentation.components.compose.buttons.PushedButton

class ErrorDialog(
    private val title: String,
    private val message: String,
    private val buttonText: String,
    private val onButtonClicked: () -> Unit,
) : BaseDialogFragment<ErrorDialogBinding>() {

    companion object {
        const val ERROR_DIALOG_TAG = "ErrorDialog.TAG"
    }

    override val binding by lazy { ErrorDialogBinding.inflate(layoutInflater) }

    override fun setUpView() {
        binding.apply {
            buttonCompose.setContent {
                IdealistaAppTheme {
                    PushedButton(
                        buttonBackgroundColor = MaterialTheme.colorScheme.error,
                        durationMillisBlockingButton = 3000,
                        text = buttonText,
                        textColor = MaterialTheme.colorScheme.onError,
                        textStyle = MaterialTheme.typography.labelMedium,
                        onButtonClicked = onButtonClicked
                    )
                }
            }
            textViewTitle.text = title
            textViewMessage.text = message
        }
    }

}