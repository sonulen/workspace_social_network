package com.redmadrobot.data.util

import com.redmadrobot.data.entity.api.response.NetworkEntityToken
import com.redmadrobot.domain.entity.repository.Tokens

fun NetworkEntityToken.toTokens() = Tokens(
    access = accessToken,
    refresh = refreshToken
)
