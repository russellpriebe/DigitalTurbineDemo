package com.penda.digitalturbinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class DetailActivity : AppCompatActivity() {
    lateinit var binding: com.penda.digitalturbinedemo.databinding.ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        val mAd = intent.getParcelableExtra<Ad>("ad")
        binding.ad = mAd
    }
}
