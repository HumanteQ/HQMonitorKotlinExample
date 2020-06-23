package io.humanteq.test_package

import android.app.Application
import io.humanteq.hq_core.HQSdk
import io.humanteq.hq_core.interfaces.HQCallback
import io.humanteq.hq_core.main.HQApi
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

        // Send target segments to Firebase Analytics. Firebase Analytics dependency must be imported separately.
        HQSdk.trackSegments(true)

        // Send target segments to AppsFlyer.
        HQSdk.trackSegments(true, HQApi.EventTracker.AppsFlyer)

        // Send predefined event `inAppPurchase(revenue: Int, currency: String, item_name: String)`.
        // `currency`    - a string representing a currency id in ISO 4217 format (https://www.currency-iso.org/dam/downloads/lists/list_one.xml)
        HQSdk.inAppPurchase(75, "EUR", "Useful item name");

        // Send predefined event `subscriptionPurchase(revenue: Int, currency: String, item_name: String, status: String)`.
        // `currency`    - a string representing a currency id in ISO 4217 format (https://www.currency-iso.org/dam/downloads/lists/list_one.xml)
        // `status`      - state of purchase event (trial/first/renewal/...)
        HQSdk.subscriptionPurchase(75, "EUR", "Useful item name", "trial");

        // Send predefined event `tutorialStep(step: String, result: String)`.
        // `step`        - a current step of tutorial
        // `result`      - a result of the current step
        HQSdk.tutorialStep("tutorial_step_1", "start");
    }
}