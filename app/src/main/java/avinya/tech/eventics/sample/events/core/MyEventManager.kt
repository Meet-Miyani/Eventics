package avinya.tech.eventics.sample.events.core

import android.content.Context
import android.os.Bundle
import android.util.Log
import avinya.tech.eventics.EventicsException
import avinya.tech.eventics.EventicsManager
import avinya.tech.eventics.SuperPropertiesProvider

/**
 * Custom event manager for logging events using various analytics platforms.
 *
 * This class integrates with analytics services like Google Firebase and Facebook Analytics.
 */
class MyEventManager(
    context: Context?,
    superPropertiesProvider: SuperPropertiesProvider = MySuperPropertiesProvider()
) : EventicsManager(context, superPropertiesProvider) {

    /**
     * Logs an event to the configured analytics platforms.
     *
     * @param context The context from which the logging is initiated.
     * @param eventName The name of the event to log.
     * @param bundle A `Bundle` containing the event properties to log.
     */
    public override fun log(context: Context, eventName: String, bundle: Bundle) {

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

    /**
     * Handles errors during the event logging process.
     *
     * @param e The `EventicsException` that was thrown during logging.
     */
    override fun loggingError(e: EventicsException) {
        Log.e(TAG, "loggingError: ", e)
    }

    companion object {
        private const val TAG = "MyEventManager"

        /**
         * Factory method to create an instance of `MyEventManager`.
         *
         * @param context The context in which the event manager operates.
         * @return A new instance of `MyEventManager`.
         */
        fun get(context: Context?): MyEventManager =
            MyEventManager(context, MySuperPropertiesProvider())
    }
}