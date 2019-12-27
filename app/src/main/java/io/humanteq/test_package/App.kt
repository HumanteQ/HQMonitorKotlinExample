package io.humanteq.test_package

import android.app.Application
import io.humanteq.hq_core.HQSdk
import io.humanteq.hq_core.interfaces.HQCallback
import io.humanteq.hq_core.models.UserGroup

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize SDK
        HQSdk.init(
            this,
            "38e44d7",
            BuildConfig.DEBUG,
            object : HQCallback<Unit> {
                override fun onError(ex: Throwable?) {

                }

                override fun onSuccess(p0: Unit?) {
                    // Get user id
                    val uuid = HQSdk.getUuid()

                    // Request predicted user groups
                    HQSdk.getUserGroupsAsync(this@App, object : HQCallback<List<UserGroup>> {
                        override fun onSuccess(groupList: List<UserGroup>?) {
                        }

                        override fun onError(ex: Throwable?) {
                        }
                    })
                }
            }
        )
        // Start SDK
        HQSdk.start(this)

        // Send event as a text ...
        HQSdk.logEvent("test_event", "test")

        // ... or as a map.
        HQSdk.logEvent(
            "test_event", mapOf(
                Pair("test_param1", "test_value1"),
                Pair("test_param2", "test_value2")
            )
        )

        // Request user data
        HQSdk.requestUserData("my@email.org")

        // Request user data deletion
        HQSdk.deleteUserData()
    }
}