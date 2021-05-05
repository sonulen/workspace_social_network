package com.redmadrobot.app.di

import com.redmadrobot.app.di.android.AndroidToolsProvider
import com.redmadrobot.app.di.authClient.AuthClientProvider
import com.redmadrobot.app.di.network.NetworkProvider

interface AppProvider : AndroidToolsProvider, NetworkProvider, AuthClientProvider
