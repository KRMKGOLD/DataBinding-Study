package com.example.databinding_study.viewmodel

import android.databinding.ObservableField
import android.view.View
import android.widget.Toast

class MainViewModel {
    val text = ObservableField<String>("")

    fun showText(view : View) {
        Toast.makeText(view.context, "${text.get()}", Toast.LENGTH_SHORT).show()
    }
}