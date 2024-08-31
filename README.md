# Eventics - Log Events Efficiently

Eventics is a lightweight and scalable library designed to simplify and enhance event logging in Android applications. It addresses the challenges of traditional event logging by providing a structured and manageable approach to preparing and logging events across your app.

![EVENTICS (1)](https://github.com/user-attachments/assets/87ef2e3d-f4f8-4fb7-a8d5-b54c5416049a)

## Why Eventics?

### Traditional Approach Challenges

In the traditional approach, developers often log events like this:

```kotlin
FirebaseAnalytics.getInstance(this).logEvent("CLICK_ON_ITEM", Bundle().apply {
    putLong("time", System.currentTimeMillis())
    putBoolean("isUserLoggedIn", isUserLoggedIn)
    putString("itemName", itemName)
})
```

This requires creating bundles manually at every place where an event needs to be logged, leading to repetitive code, potential errors, and reduced readability. Managing such events across different parts of the app becomes increasingly difficult and unscalable.

### The Eventics Solution

Eventics streamlines the event logging process by allowing you to:

- **Create Custom Event Classes:** Define events in a structured way by extending the `BaseEventic` interface.
- **Centralize Event Logging:** Manage event logging in a single, centralized class (`EventicsManager`), ensuring consistency.
- **Automate Bundle Creation:** Automatically convert property maps to bundles and add super properties.
- **Integrate with Analytics SDKs:** Seamlessly integrate with analytics platforms like Firebase and Facebook.

## Getting Started

### Installation

To include Eventics in your project, add the following dependency to your `build.gradle` file:

```groovy
dependencies {
    implementation 'io.github.meet-miyani:eventics-library:1.0.1'
}
```

### Setting Up Eventics

1. **Create Your Custom Event Manager**

Extend `EventicsManager` to define how events should be logged across different analytics platforms.

```kotlin
class MyEventManager(
    context: Context?,
    superPropertiesProvider: SuperPropertiesProvider = MySuperPropertiesProvider()
) : EventicsManager(context, superPropertiesProvider) {

    override fun log(context: Context, eventName: String, bundle: Bundle) {

        // Google Firebase Analytics
        // Uncomment and use one of the following based on your Firebase setup:

        // Option 1: Using Firebase Analytics directly
        // Firebase.analytics.logEvent(eventName, bundle)

        // Option 2: Using an instance of FirebaseAnalytics
        // FirebaseAnalytics.getInstance(context).logEvent(eventName, bundle)

        // Facebook Analytics
        // Uncomment and use the following to log events to Facebook:

        // val logger = AppEventsLogger.newLogger(context)
        // logger.logEvent(eventName, bundle)
    }

    override fun loggingError(e: EventicsException) {
        Log.e(TAG, "Error logging event: ", e)
    }

    companion object {
        private const val TAG = "MyEventManager"

        fun get(context: Context?): MyEventManager = MyEventManager(context)
    }
}
```

Define super properties if required; otherwise, you can pass `null`.

```kotlin
class MySuperPropertiesProvider : SuperPropertiesProvider {
    override fun getSuperProperties(context: Context): Map<String, Any?> {
        return mapOf(
            "app_version" to BuildConfig.VERSION_NAME,
            "device_model" to Build.MODEL,
            "platform" to "Android"
        )
    }
}
```

**Example:**

```kotlin
class MyEventManager(
    context: Context?,
    superPropertiesProvider: SuperPropertiesProvider = MySuperPropertiesProvider()
) : EventicsManager(context, superPropertiesProvider)
```

or

```kotlin
class MyEventManager(
    context: Context?,
    superPropertiesProvider: SuperPropertiesProvider? = null
) : EventicsManager(context, superPropertiesProvider)
```

### Important note:
To build the app, please ensure you add the **`gradle.properties`** file. This file has been intentionally excluded from the GitHub repository for security reasons.

Alternatively, you can create a new gradle.properties file in your project's root directory and copy-paste the following content into it:
```properties
# Project-wide Gradle settings.
# IDE (e.g. Android Studio) users:
# Gradle settings configured through the IDE *will override*
# any settings specified in this file.
# For more details on how to configure your build environment visit
# http://www.gradle.org/docs/current/userguide/build_environment.html
# Specifies the JVM arguments used for the daemon process.
# The setting is particularly useful for tweaking memory settings.
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
# When configured, Gradle will run in incubating parallel mode.
# This option should only be used with decoupled projects. For more details, visit
# https://developer.android.com/r/tools/gradle-multi-project-decoupled-projects
# org.gradle.parallel=true
# AndroidX package structure to make it clearer which packages are bundled with the
# Android operating system, and which are packaged with your app's APK
# https://developer.android.com/topic/libraries/support-library/androidx-rn
android.useAndroidX=true
# Kotlin code style for this project: "official" or "obsolete":
kotlin.code.style=official
# Enables namespacing of each library's R class so that its R class includes only the
# resources declared in the library itself and none from the library's dependencies,
# thereby reducing the size of the R class for that library
android.nonTransitiveRClass=true
```

## Usage

You can now log events in a consistent and scalable way:

- **Simple Event Logging:**

```kotlin
MyEventManager.get(this).log("EVENT_NAME")
```

- **Dynamic Event Logging:**

You can log events dynamically in two ways:

1. **Using Lambdas:** With lambdas, you can build the properties map directly within the log function, allowing for concise and readable code.

```kotlin
MyEventManager(this).log(eventName = "EVENT_NAME") {
    put("This" to 12.3)
}
```

2. **Using Parameters:** Alternatively, you can pass a predefined map of properties directly to the log function.

```kotlin
MyEventManager(this).log(
    eventName = "EVENT_NAME",
    properties = mapOf("Property1" to true)
)
```

- **Custom Event Class:**

```kotlin
data class EventHome(private val message: String) : BaseEventic {
    override val eventName = "HOME_EVENT"
    override val properties = mapOf("MESSAGE" to message)
}

val homeEvent = EventHome(message = "HomeScreen:1.0")
MyEventManager.get(this).log(homeEvent)
```

> Refer to the app module in the repository for a more detailed understanding.

## How It Works

Eventics handles the conversion of `Map<String, Any?>` to `Bundle` and automatically includes super properties defined by your `SuperPropertiesProvider`. The `log()` method in `MyEventManager` then logs the event through the specified analytics SDKs, making your event logging process scalable and manageable.

## Acknowledgments

Eventics was created to provide a more structured and scalable solution to event logging in Android applications. We hope it helps you manage your events more efficiently!
