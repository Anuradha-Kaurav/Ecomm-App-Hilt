package com.ecomm.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ecomm.app.models.Product

@Database(entities = [Product::class], version = 1)
@TypeConverters(Converters::class)
abstract class ProductsDB : RoomDatabase() {

    abstract fun getFavProductDao(): FavProductDAO
}