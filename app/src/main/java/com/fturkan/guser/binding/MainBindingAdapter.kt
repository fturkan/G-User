package com.fturkan.guser.ui

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter

@set:BindingAdapter("visible")
var View.visible
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }