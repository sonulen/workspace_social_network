package com.redmadrobot.app.di

import android.content.Context
import com.scottyab.rootbeer.RootBeer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RootBeerModule {
    @Provides
    @Singleton
    fun provideRootBeer(context: Context): RootBeer = RootBeer(context)
}
