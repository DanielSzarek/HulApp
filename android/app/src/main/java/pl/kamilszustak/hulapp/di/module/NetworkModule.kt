package pl.kamilszustak.hulapp.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.kamilszustak.hulapp.domain.model.network.ApiServiceHolder
import pl.kamilszustak.hulapp.network.BASE_URL
import pl.kamilszustak.hulapp.network.ApiService
import pl.kamilszustak.hulapp.network.JwtAuthenticator
import pl.kamilszustak.hulapp.network.interceptor.ErrorInterceptor
import pl.kamilszustak.hulapp.network.interceptor.HttpInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
            .withNullSerialization()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        jwtAuthenticator: JwtAuthenticator,
        httpInterceptor: HttpInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        errorInterceptor: ErrorInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(jwtAuthenticator)
            .addInterceptor(httpInterceptor)
            .addInterceptor(httpLoggingInterceptor)
//            .addInterceptor(errorInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiServiceHolder(): ApiServiceHolder =
        ApiServiceHolder()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit, apiServiceHolder: ApiServiceHolder): ApiService {
        return retrofit.create<ApiService>().also {
            apiServiceHolder.service = it
        }
    }
}