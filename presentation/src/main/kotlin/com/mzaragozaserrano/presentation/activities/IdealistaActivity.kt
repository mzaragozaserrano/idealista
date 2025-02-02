package com.mzaragozaserrano.presentation.activities

import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.base.BaseActivity
import com.mzaragozaserrano.presentation.databinding.ActivityIdealistaBinding

class IdealistaActivity : BaseActivity<ActivityIdealistaBinding>() {

    override var loadingRaw: Int? = R.raw.loading

    override val binding: ActivityIdealistaBinding by lazy {
        ActivityIdealistaBinding.inflate(layoutInflater)
    }

}