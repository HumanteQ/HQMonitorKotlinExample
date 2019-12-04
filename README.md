[ ![Download](https://api.bintray.com/packages/humanteq/hqm-sdk/hqm-core/images/download.svg?version=2.0.0-beta01) ](https://bintray.com/humanteq/hqm-sdk/hqm-core/2.0.0-beta01/link)

### HQMonitor Kotlin Sample App.

##### SDK integration steps:

1. Add `hqm-sdk` repository to `/<project_path>/build.gradle`:
```groovy
allprojects {
    repositories {
        google()
        jcenter()
        ...
        maven { url 'https://dl.bintray.com/humanteq/hqm-sdk/' }  <--
    }
```
2. Add `hqm-core` dependency to `/<project_path>/app/build.gradle`:
```groovy
dependencies {
    implementation fileTree(include: ['*.jar'], dir: 'libs')
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
    
    ...
    implementation 'io.humanteq.hqm:hqm-core:2.0.0-beta01' <--
}
```
3. Sync project and initialize SDK:
```kotlin
class App : Application() {

    override fun onCreate() {
    	super.onCreate()
        
        // Initialize SDK
        HQSdk.init(
                this,
                "your_api_key",
                BuildConfig.DEBUG,
                object : HQCallback<Unit> {
                    override fun onError(ex: Throwable?) {

                    }

                    override fun onSuccess(p0: Unit?) {
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
        HQSdk.logEvent("test_event", mapOf(
                Pair("test_param1", "test_value1"),
                Pair("test_param2", "test_value2")
        ))
        
    }
}
```