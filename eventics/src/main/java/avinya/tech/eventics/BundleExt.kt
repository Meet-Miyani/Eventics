package avinya.tech.eventics

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * Adds a key-value pair to a `Bundle`, automatically handling various data types.
 *
 * This extension function simplifies the process of adding values to a `Bundle` by
 * automatically determining the appropriate method to call based on the type of the value.
 * It supports a wide range of types including primitive types, arrays, lists, `Parcelable`,
 * and `Serializable` objects.
 *
 * @receiver The `Bundle` to which the key-value pair will be added.
 * @param key The key with which the specified value is to be associated.
 * @param value The value to be added to the `Bundle`. This can be of any type, including
 *              primitive types, arrays, lists, `Parcelable`, `Serializable`, and others.
 */
fun Bundle.put(key: String, value: Any?) {
    when (value) {
        is String -> putString(key, value)
        is Byte -> putByte(key, value)
        is Char -> putChar(key, value)
        is CharSequence -> putCharSequence(key, value)
        is Int -> putInt(key, value)
        is Long -> putLong(key, value)
        is Float -> putFloat(key, value)
        is Double -> putDouble(key, value)
        is Boolean -> putBoolean(key, value)
        is Bundle -> putBundle(key, value)

        // Arrays
        is ByteArray -> putByteArray(key, value)
        is CharArray -> putCharArray(key, value)
        is IntArray -> putIntArray(key, value)
        is LongArray -> putLongArray(key, value)
        is FloatArray -> putFloatArray(key, value)
        is DoubleArray -> putDoubleArray(key, value)
        is BooleanArray -> putBooleanArray(key, value)

        // Arrays of specific types
        is Array<*> -> {
            @Suppress("UNCHECKED_CAST")
            when {
                value.isArrayOf<String>() ->
                    putStringArray(key, value as Array<String>)

                value.isArrayOf<CharSequence>() ->
                    putCharSequenceArray(key, value as Array<CharSequence>)

                value.isArrayOf<Parcelable>() ->
                    putParcelableArray(key, value as Array<Parcelable>)
            }
        }

        // Lists
        is List<*> -> {
            @Suppress("UNCHECKED_CAST")
            when {
                value.all { it is String } ->
                    putStringArrayList(key, value as ArrayList<String>)

                value.all { it is Int } ->
                    putIntegerArrayList(key, value as ArrayList<Int>)

                value.all { it is CharSequence } ->
                    putCharSequenceArrayList(key, value as ArrayList<CharSequence>)

                value.all { it is Parcelable } ->
                    putParcelableArrayList(key, value as ArrayList<Parcelable>)
            }
        }
        
        // Serializable and Parcelable
        is Serializable -> putSerializable(key, value)
        is Parcelable -> putParcelable(key, value)

        // If the value type is not supported, do nothing
        else -> {}
    }
}