package avinya.tech.eventics

import android.os.Bundle
import android.util.Log


/**
 * Utility object for logging events in a structured format, particularly useful for debugging.
 *
 * The `EventicsLogger` provides functionality to print detailed event logs to the console
 * when debugging is enabled. It includes the event name and its associated properties.
 * This can be especially useful during development to verify that events are being logged correctly.
 */
object EventicsLogger {

    // Tag used for logging. It can be customized if necessary.
    private const val TAG = "EventicsManager"

    // Indicates whether debug mode is enabled. When enabled, events will be logged.
    private var isDebugMode = false

    /**
     * Enables or disables debug mode for event logging.
     *
     * When debug mode is enabled, detailed event logs will be printed to the console.
     * This includes the event name and all associated properties. When disabled, no logs
     * will be printed by this logger.
     *
     * @param enable `true` to enable debug mode, `false` to disable it.
     */
    fun enableDebugMode(enable: Boolean) {
        isDebugMode = enable
    }

    /**
     * Prints the event name and properties to the console if debug mode is enabled.
     *
     * This function logs the event name followed by its properties in a structured format.
     * The properties are logged as key-value pairs. If debug mode is not enabled, the function
     * will return without logging anything.
     *
     * @param eventName The name of the event being logged.
     * @param bundle A `Bundle` containing the properties of the event, where each key-value pair
     * represents a property of the event.
     */
    fun printEventLogging(eventName: String, bundle: Bundle) {
        if (!isDebugMode) {
            return
        }
        Log.d(TAG, "Debug mode enabled. Logging event: $eventName")
        val separator = "*".repeat(50)

        Log.d(TAG, separator)
        // Print the event name
        Log.d(TAG, "Event Name: $eventName")

        // Print the properties
        Log.d(TAG, "Properties:")

        // Build and print the properties in the desired format
        bundle.keySet().forEach { key ->
            val value = bundle.get(key)?.toString() ?: "null"
            Log.d(TAG, "[$key: $value]")
        }
        Log.d(TAG, separator)
    }
}
