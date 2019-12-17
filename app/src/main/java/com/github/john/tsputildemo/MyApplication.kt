package com.github.john.tsputildemo

import android.app.Application
import com.github.john.tsputil.TSPUtil

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TSPUtil.init(
            this,
            "APP",
            "USER",
            arrayOf("example1", "example2", "example3", "example4")
        )
    }
}