package avinya.tech.eventics.sample.events.core

import android.content.Context
import avinya.tech.eventics.SuperPropertiesProvider
import avinya.tech.eventics.sample.R

/**
 * Provides global properties for event logging.
 *
 * This implementation includes properties like the current timestamp
 * and application name to be attached to all logged events.
 */
class MySuperPropertiesProvider : SuperPropertiesProvider {

    /**
     * Retrieves global properties to be included with every event.
     *
     * @param context The context used to obtain resources.
     * @return A map containing the global properties.
     */
    override fun getSuperProperties(context: Context): Map<String, Any?> {

        // Define the global properties
        val superProperties = mapOf(

            // Current timestamp in milliseconds
            "Timestamp" to System.currentTimeMillis(),

            // Application name retrieved from the string resources
            "AppName" to context.getString(R.string.app_name),

            // Add more properties that you need
        )

        return superProperties
    }
}