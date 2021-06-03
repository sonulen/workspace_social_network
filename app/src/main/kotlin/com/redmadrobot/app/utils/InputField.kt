package com.redmadrobot.app.utils

data class InputField(
    val value: String = "",
    val isValid: Boolean = false,
    val error: Int? = null,
)
