package yz.l.compose.demo

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

/**
 * desc:
 * created by liyuzheng on 2026/3/15 20:34
 */
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.v("App", "onCreate")
    }
}