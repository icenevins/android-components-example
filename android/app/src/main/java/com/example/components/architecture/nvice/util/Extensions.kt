package com.example.components.architecture.nvice.util

import android.support.v7.widget.AppCompatImageView
import android.view.ViewGroup
import com.bumptech.glide.Glide
import java.util.*


fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) +  start

fun AppCompatImageView.load(url: String){
    Glide.with(context).load(url).into(this)
}