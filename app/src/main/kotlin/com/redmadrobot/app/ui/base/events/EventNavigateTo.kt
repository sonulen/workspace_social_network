package com.redmadrobot.app.ui.base.events

import androidx.navigation.NavDirections
import com.redmadrobot.extensions.lifecycle.Event

class EventNavigateTo(val direction: NavDirections) : Event
