## HQMonitor Kotlin Sample App.

#### SDK integration steps:

1. Add `hqm-sdk` repository to `/<project_path>/build.gradle`:
```groovy
allprojects {
    repositories {
        google()
        jcenter()
        ...
        maven { url 'https://repo.repsy.io/mvn/humanteq/hqsdk' } < --
    }
```
2. Add `hqm-core` dependency to `/<project_path>/app/build.gradle`:
```groovy
android {
    ...

    // HQSdk requires at minimum Java 11
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_11
        targetCompatibility JavaVersion.VERSION_11
    }
}

dependencies {
    implementation fileTree(include: ['*.jar'], dir: 'libs')
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"

    ...
    implementation 'io.humanteq.hqm:hqm-core:2.3.2' < --
}
```

3. Skip this step if your `targetSdkVersion` < 30 .

Otherwise place `QUERY_ALL_PACKAGES` permission in your AndroidManifest.xml:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android" package="io.sample.app">

    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
    ...
    <uses-permission android:name="android.permission.QUERY_ALL_PACKAGES" />

    <application>
        ...

    </application>
</manifest>
```
In order to comply with Google Play policy you also have to declare this permission using the
Declaration Form in Play
Console. [More info](https://support.google.com/googleplay/android-developer/answer/10158779?hl=en)

4. Sync project and initialize SDK:

```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize SDK
        // Should be called only after obtaining user consent
        HQSdk.init(
            this,
                "your_api_key",
                BuildConfig.DEBUG,
                true, // Enable Advertising ID tracking
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

        // Enable Advertising ID tracking (if previously has been disabled)
        HQSdk.setId(this, null)
        // Or set custom id
        HQSdk.setId(this, "custom_id")
        
        // Send event as a text ...
        HQSdk.logEvent("test_event", "test")

        // ... or as a map.
        HQSdk.logEvent("test_event", mapOf(
                Pair("test_param1", "test_value1"),
                Pair("test_param2", "test_value2")
        ))
        
        // Request user data
        HQSdk.requestUserData("my@email.org")

        // Request user data deletion
        HQSdk.deleteUserData()

        // Send target segments to Firebase Analytics. Firebase Analytics dependency must be imported separately. 
        HQSdk.trackSegments(true)

        // Send target segments to AppsFlyer.
        HQSdk.trackSegments(true, HQApi.EventTracker.AppsFlyer)

        // Send predefined event `inAppPurchase(int revenue, String currency, String item_name)`.
        // `currency`    - a string representing a currency id in ISO 4217 format (https://www.currency-iso.org/dam/downloads/lists/list_one.xml)
        HQSdk.inAppPurchase(75.0, "EUR", "Useful item name");

        // Send predefined event `subscriptionPurchase(int revenue, String currency, String item_name, String status)`.
        // `currency`    - a string representing a currency id in ISO 4217 format (https://www.currency-iso.org/dam/downloads/lists/list_one.xml)
        // `status`      - state of purchase event (trial/first/renewal/...)
        HQSdk.subscriptionPurchase(75.0, "EUR", "Useful item name", "trial");
        
        // Send predefined event `tutorialStep(String step, String result)`.
        // `step`        - a current step of tutorial
        // `result`      - a result of the current step
        HQSdk.tutorialStep("tutorial_step_1", "start");
    }
}
```

<br>

#### Kochava attribution.

To enable Kochava attribution tracking you must setup Kochava attribution listener before launching HQSdk. Please follow these [steps](https://support.kochava.com/sdk-integration/android-sdk-integration/android-using-the-sdk/?scrollto=marker_8#collapseRetrievingAttribution).

<br>

#### Proguard and R8.

If you are using R8 no additional steps are required.  
If you are using Proguard you might want to add [proguard-rules](https://raw.githubusercontent.com/HumanteQ/HQMonitorKotlinExample/master/app/proguard-rules.pro) in your `proguard-rules.pro` file. 

<br>

#### GDPR compliance.

To comply with GDPR, we provide following user data management methods:
1. Request for user data. 
A report with current user data will be sent to the provided email.
```kotlin
            HQSdk.requestUserData("some@email.org")
```

2. User data deletion request. All current user data will be deleted from Humanteq servers.
```kotlin
            HQSdk.deleteUserData()
```
