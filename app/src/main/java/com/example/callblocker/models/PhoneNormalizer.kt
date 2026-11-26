package com.example.callblocker.models

object PhoneNormalizer {
    fun toNational(raw: String): String {
        val s = raw.trim().replace(Regex("[\\s.\\-()]"), "")
        val stripped = when {
            s.startsWith("+33") -> s.removePrefix("+33")
            s.startsWith("0033") -> s.removePrefix("0033")
            s.startsWith("0") -> s.removePrefix("0")
            else -> s
        }
        return stripped.filter(Char::isDigit)
    }

    fun toDisplay(prefix: String): String = "0$prefix..."
}