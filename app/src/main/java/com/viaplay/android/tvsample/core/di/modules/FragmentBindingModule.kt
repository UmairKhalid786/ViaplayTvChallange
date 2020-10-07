package com.viaplay.android.tvsample.core.di.modules

import androidx.fragment.app.Fragment
import com.viaplay.android.tvsample.core.di.qualifiers.FragmentKey
import com.viaplay.android.tvsample.features.main.ui.MainFragment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoMap


@Module
@InstallIn(ApplicationComponent::class)
abstract class FragmentBindingModule {
    @Binds
    @IntoMap
    @FragmentKey(MainFragment::class)
    abstract fun bindMainFragment(fragment: MainFragment): Fragment
}