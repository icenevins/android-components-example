package com.example.components.architecture.nvice.model.user

import com.example.components.architecture.nvice.R
import com.example.components.architecture.nvice.util.extension.random
import com.google.gson.annotations.SerializedName
import com.example.components.architecture.nvice.util.extension.capitalize

enum class UserStatus constructor(val id: Int?) {

    @SerializedName("0")
    PERMANENT(0),

    @SerializedName("1")
    TEMPORARY(1),

    @SerializedName("2")
    UNDEFINED(2);

    fun getValue(): Int? = id

    fun getColorResource(): Int = when (this) {
        PERMANENT -> R.color.user_status_permanent
        TEMPORARY -> R.color.user_status_temporary
        UNDEFINED -> R.color.user_status_undefined
    }

    // for spinners
    override fun toString(): String {
        return this.name.capitalize()
    }

    companion object {
        fun from(id: Int?): UserStatus = requireNotNull(values().find { it.id == id })
        fun random(): UserStatus = values()[(0 until UserStatus.values().size).random()]
    }
}