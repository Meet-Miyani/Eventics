package avinya.tech.eventics

/**
 * Custom exception class for handling errors specific to event logging.
 *
 * This exception is thrown when an error occurs during the process of logging an event.
 * It provides additional context by including the name of the event that caused the error,
 * making it easier to debug and understand the source of the issue.
 *
 * @property eventName The name of the event that caused the exception.
 * @constructor Creates an [EventicsException] with the specified event name, error message, and optional cause.
 *
 * @param eventName The name of the event during which the error occurred.
 * @param message A detailed message explaining the reason for the exception.
 * @param cause The underlying cause of the exception, if any. Defaults to `null`.
 */
class EventicsException(
    val eventName: String,
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)