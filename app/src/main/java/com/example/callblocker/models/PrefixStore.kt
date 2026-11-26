package com.example.callblocker.models

import android.content.Context

class PrefixStore(context: Context) {
    private val prefs = context.getSharedPreferences("callblocker", Context.MODE_PRIVATE)

    var enabled: Boolean
        get() = prefs.getBoolean("enabled", true)
        set(value) = prefs.edit().putBoolean("enabled", value).apply()

    fun prefixes(): Set<String> =
        prefs.getStringSet("prefixes", setOf("424"))!!.toSet() // copy, never mutate the original

    /** Returns false if the input is too short to be a sane prefix. */
    fun add(input: String): Boolean {
        val p = PhoneNormalizer.national(input)
        if (p.length < 3) return false // "4" alone would nuke every 04 landline
        prefs.edit().putStringSet("prefixes", prefixes() + p).apply()
        return true
    }

    fun remove(prefix: String) {
        prefs.edit().putStringSet("prefixes", prefixes() - prefix).apply()
    }

    fun matches(incoming: String): Boolean {
        val n = PhoneNormalizer.national(incoming)
        return prefixes().any { n.startsWith(it) }
    }
}