package com.hamiltonch.hamiltonexchange.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ExchangeRate::class],version = 1)
abstract class CurrencyDB : RoomDatabase() {
    abstract fun currencyDao() : CurrencyDao
}