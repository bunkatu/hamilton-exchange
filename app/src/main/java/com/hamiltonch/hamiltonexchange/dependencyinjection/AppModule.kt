package com.hamiltonch.hamiltonexchange.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.hamiltonch.hamiltonexchange.BuildConfig
import com.hamiltonch.hamiltonexchange.api.RetrofitAPI
import com.hamiltonch.hamiltonexchange.db.CurrencyDB
import com.hamiltonch.hamiltonexchange.db.CurrencyDao
import com.hamiltonch.hamiltonexchange.repository.CurrencyRepository
import com.hamiltonch.hamiltonexchange.repository.CurrencyRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, CurrencyDB::class.java,"CurrencyDB").build()

    @Singleton
    @Provides
    fun injectDao(
        database: CurrencyDB
    ) = database.currencyDao()


    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao: CurrencyDao, api:RetrofitAPI) = CurrencyRepository(dao,api) as CurrencyRepositoryInterface



}