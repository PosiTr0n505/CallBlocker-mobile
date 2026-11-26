package com.example.callblocker.models

import android.content.Context
import androidx.core.content.edit

class PrefixRepository(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    private val prefixes: MutableSet<String> = (prefs.getStringSet(KEY_PREFIXES, DEFAULT_PREFIXES) ?: DEFAULT_PREFIXES).toMutableSet()

    var isEnabled: Boolean
        get() = prefs.getBoolean(KEY_ENABLED, true)
        set(value) = prefs.edit().putBoolean(KEY_ENABLED, value).apply()

    fun all(): Set<String> = prefixes.toSet()

    fun add(input: String): Boolean {
        val prefix = PhoneNormalizer.toNational(input)
        if (prefix.length < MIN_PREFIX_LENGTH) return false

        prefixes.add(prefix)
        persist()

        return true
    }

    fun remove(prefix: String) {
        prefixes.remove(prefix)
        persist()
    }

    fun isBlocked(number: String): Boolean {
        val national = PhoneNormalizer.toNational(number)
        return prefixes.any(national::startsWith)
    }

    private fun persist() {
        prefs.edit { putStringSet(KEY_PREFIXES, prefixes.toSet()) }
    }

    private companion object {
        const val FILE_NAME = "callblocker"
        const val KEY_ENABLED = "enabled"
        const val KEY_PREFIXES = "prefixes"
        const val MIN_PREFIX_LENGTH = 3
        val DEFAULT_PREFIXES = setOf(
            "162", "163", "270", "271", "377", "378",
            "424", "425", "568", "569", "948", "949",
        )
    }
}