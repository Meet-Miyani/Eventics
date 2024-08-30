package avinya.tech.eventics

/**
 * Adds a key-value pair to a mutable map.
 *
 * This extension function simplifies the process of adding a key-value pair
 * to a `MutableMap<String, Any?>` by accepting a `Pair` as an argument. It
 * destructures the pair into its components (key and value) and inserts them
 * into the map.
 *
 * @receiver The `MutableMap<String, Any?>` to which the key-value pair will be added.
 * @param pair A `Pair<String, Any?>` containing the key and value to be added to the map.
 */
fun MutableMap<String, Any?>.put(pair: Pair<String, Any?>) {
    val (key, value) = pair
    put(key, value)
}