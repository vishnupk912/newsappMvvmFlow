package com.test.newsapp.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}