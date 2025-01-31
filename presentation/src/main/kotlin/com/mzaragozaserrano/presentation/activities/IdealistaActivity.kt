package com.mzaragozaserrano.presentation.activities

import com.mzaragozaserrano.presentation.base.BaseActivity
import com.mzaragozaserrano.presentation.databinding.ActivityIdealistaBinding

class IdealistaActivity : BaseActivity<ActivityIdealistaBinding>() {

    override val binding: ActivityIdealistaBinding by lazy {
        ActivityIdealistaBinding.inflate(layoutInflater)
    }

}