package com.redmadrobot.app.di

import com.redmadrobot.app.di.android.AndroidToolsProvider
import com.redmadrobot.app.di.deauthorizationRepository.DeauthorizationRepositoryProvider
import com.redmadrobot.app.di.memoryCache.MemoryCacheProvider
import com.redmadrobot.app.di.network.NetworkProvider
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider

interface AppProvider :
    AndroidToolsProvider,
    NetworkProvider,
    SessionRepositoryProvider,
    MemoryCacheProvider,
    DeauthorizationRepositoryProvider
