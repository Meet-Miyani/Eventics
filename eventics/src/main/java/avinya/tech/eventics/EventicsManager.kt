package avinya.tech.eventics

import android.content.Context
import android.os.Bundle

/**
 * Abstract base class for managing and logging events in an application.
 *
 * The `EventicsManager` class provides a structured way to log events by
 * accepting event data and properties, validating them, and then delegating
 * the actual logging to an implementation of the abstract `log` method.
 * It also handles errors and provides a mechanism for adding global properties
 * to each event via a `SuperPropertiesProvider`.
 *
 * @property context The context in which the manager operates, typically the application or activity context.
 * @property superPropertiesProvider An optional provider for global properties that should be included in every event.
 *                                    If null, no super properties will be added.
 */
abstract class EventicsManager(
    private val context: Context?,
    private val superPropertiesProvider: SuperPropertiesProvider? = null
) {

    /**
     * Logs an event with the given name and properties bundle.
     *
     * This method must be implemented by subclasses to define how the event should be logged.
     * The implementation could involve sending the event to an analytics service, logging it locally,
     * or any other desired behavior.
     *
     * @param context The context from which the logging is initiated.
     * @param eventName The name of the event to log.
     * @param bundle A `Bundle` containing the event properties to log.
     */
    protected abstract fun log(context: Context, eventName: String, bundle: Bundle)

    /**
     * Handles errors that occur during the event logging process.
     *
     * This method must be implemented by subclasses to define how errors should be handled.
     * The implementation could involve logging the error, reporting it to an error tracking service,
     * or any other desired error-handling behavior.
     *
     * @param e The `EventicsException` that was thrown during the logging process.
     */
    protected abstract fun loggingError(e: EventicsException)

    /**
     * Logs an event with the specified name and no additional properties.
     *
     * This method is a convenience function that logs an event with the provided `eventName`
     * and an empty map of properties. It delegates to the `log(eventName: String, properties: Map<String, Any?>)` method.
     *
     * Example usage:
     * ```
     * log("USER_SIGNED_UP")
     * ```
     *
     * @param eventName The name of the event to log.
     */
    fun log(eventName: String) {
        logEvent(eventName)
    }

    /**
     * Logs an event using a `BaseEventic` instance.
     *
     * This method extracts the event name and properties from the `BaseEventic` instance
     * and delegates the logging to the appropriate method.
     *
     * Example:
     * ```
     * data class UserSignedInEvent(
     *     private val userId: Int,
     *     private val loginMethod: String,
     * ) : BaseEventic {
     *     override val eventName = "USER_SIGNED_IN"
     *     override val properties = mapOf(
     *         "user_id" to userId,
     *         "login_method" to loginMethod
     *     )
     * }
     *
     * val event = UserSignedInEvent(
     *     userId = 12345,
     *     loginMethod = "email"
     * )
     * log(event)
     * ```
     *
     * @param event The `BaseEventic` instance containing the event name and properties.
     */
    fun log(event: BaseEventic) {
        logEvent(event.eventName, event.properties)
    }

    /**
     * Inline function to log an event using a lambda to build the event.
     * This is useful for cases where the event is constructed dynamically.
     *
     * Example:
     * ```
     * log {
     *     UserSignedInEvent(
     *         userId = 12345,
     *         loginMethod = "email"
     *     )
     * }
     * ```
     *
     * @param buildEvent Lambda function that constructs the event, returning an instance of a class that implements `BaseEventic`.
     */
    inline fun <E : BaseEventic> log(buildEvent: () -> E) {
        val event = buildEvent()
        log(event)
    }

    /**
     * Logs an event with a name and a lambda that defines its properties.
     *
     * This method allows for a more dynamic way to build the properties map before logging the event.
     * The lambda is applied to a mutable map, which is then passed to the logging method.
     *
     * Example usage:
     * ```
     * log("USER_SIGNED_UP") {
     *     put("source", "email")
     *     put("successful", true)
     * }
     * ```
     *
     * @param eventName The name of the event to log.
     * @param properties A lambda function that populates the properties map.
     */
    fun log(eventName: String, properties: MutableMap<String, Any?>.() -> Unit) {
        logEvent(eventName, mutableMapOf<String, Any?>().apply(properties))
    }

    /**
     * Logs an event with a name and a predefined properties map.
     *
     * Example usage:
     * ```
     * log("USER_SIGNED_UP", mapOf("source" to "email", "successful" to true))
     * ```
     *
     * @param eventName The name of the event to log.
     * @param properties A map containing the properties to log with the event.
     */
    fun log(eventName: String, properties: Map<String, Any?>) {
        logEvent(eventName = eventName, properties = properties)
    }

    /**
     * Logs an event with a name and a predefined properties map.
     *
     * This method validates the event, creates a bundle with the properties,
     * and attempts to log it. If an error occurs during this process,
     * it is handled by the `loggingError` method.
     *
     * @param eventName The name of the event to log.
     * @param properties A map containing the properties to log with the event.
     */
    private fun logEvent(eventName: String, properties: Map<String, Any?> = emptyMap()) {
        if (!validateEvent(eventName)) {
            val exception = EventicsException(eventName, "Invalid event: $eventName", null)
            loggingError(exception)
            return
        }

        if (context == null) {
            return
        }

        val bundle = createBundle(eventName, properties) ?: return
        tryLogging(eventName) {
            log(context, eventName, bundle)
            EventicsLogger.printEventLogging(eventName, bundle)
        }
    }

    /**
     * Creates a `Bundle` from the event name and properties map, including any super properties.
     *
     * This method combines the event-specific properties with any global properties
     * provided by the `SuperPropertiesProvider`. It returns a `Bundle` that can be
     * passed to the `log` method for final logging.
     *
     * @param eventName The name of the event.
     * @param properties A map containing the properties to log with the event.
     * @return A `Bundle` containing all the event properties, or null if an error occurs.
     */
    private fun createBundle(
        eventName: String,
        properties: Map<String, Any?>,
    ): Bundle? = try {
        Bundle().apply {
            context?.let {
                superPropertiesProvider
                    ?.getSuperProperties(context)
                    ?.forEach { (key, value) ->
                        put(key, value)
                    }
            }
            properties.forEach { (key, value) ->
                put(key, value)
            }
        }
    } catch (e: Exception) {
        val exception =
            EventicsException(eventName = eventName, "Failed to log event: $eventName", e)
        loggingError(exception)
        null
    }

    /**
     * Attempts to log the event and handles any exceptions that occur.
     *
     * This method encapsulates the logging logic in a try-catch block to
     * ensure that any exceptions thrown during logging are handled appropriately.
     *
     * @param eventName The name of the event being logged.
     * @param logFunction A lambda function containing the logging logic.
     */
    private fun tryLogging(eventName: String, logFunction: () -> Unit) {
        try {
            logFunction()
        } catch (e: Exception) {
            val exception =
                EventicsException(eventName = eventName, "Failed to log event: $eventName", e)
            loggingError(exception)
        }
    }

    /**
     * Validates the event before it is logged.
     *
     * This method can be overridden by subclasses to implement custom validation logic.
     * By default, it ensures that the event name is not empty.
     *
     * @param eventName The name of the event.
     * @return `true` if the event is valid and can be logged, `false` otherwise.
     */
    private fun validateEvent(eventName: String): Boolean {
        // Default validation could be as simple as ensuring the event name is not empty
        return eventName.isNotEmpty()
    }
}