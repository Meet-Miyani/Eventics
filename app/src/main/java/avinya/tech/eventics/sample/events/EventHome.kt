package avinya.tech.eventics.sample.events

import avinya.tech.eventics.BaseEventic

/**
 * Represents an event on the home screen with a message.
 *
 * This class implements `BaseEventic` and is used for logging a specific event
 * related to the home screen.
 *
 * @property message The message associated with the home event.
 */
data class EventHome(private val message: String) : BaseEventic {

    override val eventName: String
        get() = "HOME_EVENT"

    override val properties: Map<String, Any?>
        get() = mapOf(
            "MESSAGE" to message
        )
}
