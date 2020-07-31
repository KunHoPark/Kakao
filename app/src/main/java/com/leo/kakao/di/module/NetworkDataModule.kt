package com.leo.kakao.di.module;

import com.leo.kakao.data.remote.api.AuthInterceptor
import com.leo.kakao.util.Constants
import com.leo.kakao.util.RetrofitLogger
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


/**
 * NetworkDataModule
 * AppComponent에 연결 된다.
 * @author LeoPark
 **/
@Module
class NetworkDataModule {

    @Provides
    @Named("authorized")
    @Singleton
    fun provideAuthRestAdapter(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(Constants.HOST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    }

    @Provides
    @Singleton
    fun providesOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(Constants.READ_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            writeTimeout(Constants.WRITE_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            connectTimeout(Constants.CONNECT_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            addInterceptor(authInterceptor)
            addInterceptor(HttpLoggingInterceptor(RetrofitLogger(Constants.HTTP_LOGGING_PRETTY_PRINTING_ENABLE)).apply {
                level = HttpLoggingInterceptor.Level.BODY
                HttpLoggingInterceptor.Level.HEADERS

            })
        }.let {
            it.build()
        }
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor(Constants.REST_API_KEY)

}
