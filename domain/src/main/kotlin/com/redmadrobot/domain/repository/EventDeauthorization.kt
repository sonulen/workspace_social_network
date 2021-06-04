package com.redmadrobot.domain.repository

sealed class EventDeauthorization(val message: String) {
    class StayLoggedIn : EventDeauthorization("")
    class Logout(message: String) : EventDeauthorization(message)
}
