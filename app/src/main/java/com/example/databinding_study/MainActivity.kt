package com.example.databinding_study

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.databinding_study.databinding.ActivityMainBinding
import com.example.databinding_study.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.vm = MainViewModel()
    }
}
