package com.ecomm.app.di.modules

import android.content.Context
import androidx.room.Room
import com.ecomm.app.db.ProductsDB
import com.ecomm.app.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesProductsDB(@ApplicationContext context: Context): ProductsDB{
        return Room.databaseBuilder(context, ProductsDB::class.java, Constants.DB_NAME).build()
    }
}