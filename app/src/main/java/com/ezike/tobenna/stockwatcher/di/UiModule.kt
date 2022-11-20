package com.ezike.tobenna.stockwatcher.di

import android.app.Activity
import com.ezike.tobenna.stockwatcher.databinding.ActivityMainBinding
import com.ezike.tobenna.stockwatcher.ui.RootUi
import com.ezike.tobenna.stockwatcher.ui.RootUiImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@[Module InstallIn(ActivityComponent::class)]
internal interface UiModule {

    @Binds
    fun bindRootUi(rootUi: RootUiImpl): RootUi

    companion object {
        @Provides
        fun provideBinding(activity: Activity): ActivityMainBinding =
            ActivityMainBinding.inflate(activity.layoutInflater)
    }
}