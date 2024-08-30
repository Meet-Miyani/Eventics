package avinya.tech.eventics

import android.content.Context

/**
 * Interface for providing global properties that should be included with every event log.
 *
 * Implementations of this interface are responsible for supplying a set of key-value pairs
 * (properties) that will be automatically included in all events logged by the application.
 * This can include information such as device details, app version, or user identification.
 * These properties provide additional context that is useful for all logged events.
 */
interface SuperPropertiesProvider {

    /**
     * Retrieves a map of global properties to be included with every event.
     *
     * This method is called each time an event is logged to collect any properties
     * that should be consistently attached to all events. The properties returned can be dynamic,
     * such as the current timestamp or location, or static, such as the app version or device model.
     *
     * @param context The `Context` from which the properties may be derived. This is typically used
     * to access system resources or shared preferences to gather information.
     * @return A `Map` containing key-value pairs where the key is a `String` representing the property name,
     * and the value is an `Any?`, representing the property value, which can be of any type or null.
     */
    fun getSuperProperties(context: Context): Map<String, Any?>
}