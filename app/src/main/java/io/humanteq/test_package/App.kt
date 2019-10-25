package io.humanteq.test_package

import android.app.Application
import io.humanteq.hqsdkcore.HQSdk

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // init HQM SDK
        HQSdk.enableDebug(BuildConfig.DEBUG)
        HQSdk.init(
            this,
            "38e44d7",
            false,
            enableSlackDebug = BuildConfig.DEBUG
        )
        HQSdk.collectApps(this)
    }
}