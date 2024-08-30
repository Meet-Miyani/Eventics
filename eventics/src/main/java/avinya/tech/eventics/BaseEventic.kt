package avinya.tech.eventics

/**
 * Interface representing the base structure for any event to be logged.
 *
 * Classes implementing this interface should define the event name and the properties
 * associated with that event. This allows for a consistent structure across different
 * types of events, making it easier to manage and log events in a uniform way.
 */
interface BaseEventic {
    /**
     * The name of the event to be logged.
     *
     * This is a unique identifier for the event and should be descriptive enough
     * to convey the purpose of the event. Example: "USER_SIGNED_IN", "PURCHASE_COMPLETED".
     */
    val eventName: String

    /**
     * A map containing key-value pairs representing the properties of the event.
     *
     * These properties provide additional context or data about the event. The keys
     * should be descriptive, and the values can be of any type. Example properties might
     * include "user_id", "timestamp", "item_price".
     */
    val properties: Map<String, Any?>
}