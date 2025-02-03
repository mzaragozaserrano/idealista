package com.mzaragozaserrano.presentation.activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.bundle.Bundle
import androidx.core.content.ContextCompat
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.base.BaseActivity
import com.mzaragozaserrano.presentation.databinding.ActivityIdealistaBinding

class IdealistaActivity : BaseActivity<ActivityIdealistaBinding>() {

    override var loadingRaw: Int? = R.raw.loading

    override val binding: ActivityIdealistaBinding by lazy {
        ActivityIdealistaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}