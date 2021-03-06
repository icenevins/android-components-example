package com.example.components.architecture.nvice.util

import com.example.components.architecture.nvice.util.extension.getInt
import com.example.components.architecture.nvice.util.regex.RegexPattern
import timber.log.Timber

object ValidationUtils {

    fun isValidCitizenId(id: String?): Boolean {
        id?.let {
            if (id.length == 13) {
                try {
                    var sum = 0
                    for (index in 1..12) {
                        sum += (14 - index) * id[index - 1].getInt()
                    }
                    (sum % 11).let {
                        if ((if (it > 1) 11 else 1) - it == id.last().getInt()) {
                            Timber.i("Citizen id is detected\n\tresult: $id")
                            return true
                        }
                    }
                } catch (nfe: NumberFormatException) {
                    Timber.i("Input is not a number")
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
        return false
    }

    fun isValidEmail(email: String?): Boolean {
        email?.let {
            return it.matches(RegexPattern.EMAIL.regex())
        }
        return false
    }
}