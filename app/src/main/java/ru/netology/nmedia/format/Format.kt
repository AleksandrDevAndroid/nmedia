package ru.netology.nmedia.format

import java.text.DecimalFormat

object Format : FormatCount {
    override fun format(num: Long): String {
        return when {
            num < 1000 -> num.toString()
            num < 1_000_000 -> {
                val formatted = num / 1000.0
                DecimalFormat("#.#").format(formatted) + "K"
            }

            else -> {
                val formatted = num / 1_000_000.0
                DecimalFormat("#.#").format(formatted) + "M"
            }
        }

    }
}