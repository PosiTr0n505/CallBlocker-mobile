package com.example.callblocker.models

object PhoneNormalizer {
    /** "+33 4 24 12 34 56", "04 24 12...", "4 24" -> "424123456" / "424" */
    fun national(raw: String): String {
        val s = raw.trim().replace(Regex("[\\s.\\-()]"), "")
        val stripped = when {
            s.startsWith("+33") -> s.removePrefix("+33")
            s.startsWith("0033") -> s.removePrefix("0033")
            s.startsWith("0") -> s.removePrefix("0")
            else -> s
        }
        return stripped.filter { it.isDigit() }
    }
}