package com.viaplay.android.tvsample.features.main

import android.content.Context
import android.os.Looper
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.EnumJsonAdapter
import com.viaplay.android.tvsample.features.main.data.ContentResponse
import com.viaplay.android.tvsample.features.main.models.ContentApi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
abstract class NetModule {

    companion object {
        private const val HTTP_TIMEOUT_S = 30
        private const val HTTP_RESPONSE_CACHE = (10 * 1024 * 1024).toLong()

        @Provides
        @Singleton
        internal fun provideCache(@ApplicationContext context: Context): Cache {
            checkThread()
            return Cache(context.cacheDir, HTTP_RESPONSE_CACHE)
        }

        private fun checkThread() {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw IllegalStateException("Don't do this on main thread.")
            }
        }

        @Provides
        @Singleton
        internal fun provideOkHttpClient(
            cache: Cache,
        ): OkHttpClient {
            checkThread()
            val builder =
                OkHttpClient.Builder().connectTimeout(HTTP_TIMEOUT_S.toLong(), TimeUnit.SECONDS)
                    .readTimeout(HTTP_TIMEOUT_S.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(HTTP_TIMEOUT_S.toLong(), TimeUnit.SECONDS)
                    .cache(cache)
            return builder.build()
        }

        @Provides
        @Singleton
        internal fun provideMoshi(): Moshi {
            return Moshi.Builder()
                .add(ContentResponse.Block.Type::class.java,
                    EnumJsonAdapter.create(ContentResponse.Block.Type::class.java)
                        .withUnknownFallback(null)
                )
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(
            client: Lazy<OkHttpClient>,
            moshi: Moshi
        ): Retrofit {
            return Retrofit.Builder()
                .callFactory {
                    client.get().newCall(it)
                }
                .baseUrl("https://content.viaplay.se/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }

        @Provides
        @Singleton
        fun provideContentApi(retrofit: Retrofit): ContentApi {
            return retrofit.create(ContentApi::class.java)
        }
    }
}