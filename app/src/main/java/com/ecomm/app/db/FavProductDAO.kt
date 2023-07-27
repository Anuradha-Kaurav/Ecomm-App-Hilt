package com.ecomm.app.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ecomm.app.models.Product

@Dao
interface FavProductDAO {

    //add products
    @Insert
    suspend fun addFavProduct(product: Product)

    //get all products
    @Query("SELECT * FROM Product")
    suspend fun getAllFavProducts(): List<Product>

    // delete product
    @Delete()
    suspend fun deleteFavProduct(product: Product)

    //check if selected prod exists
    @Query("SELECT EXISTS(SELECT * FROM Product WHERE id = :id)")
    suspend fun hasFavProduct(id: Int): Boolean

    //check if any prod exists
//    @Query("SELECT EXISTS(SELECT * FROM Product)")
    @Query("SELECT COUNT(*) from Product")
    suspend fun hasProducts(): Int

    @Query("SELECT EXISTS(SELECT * FROM Product)")
    suspend fun isExists(): Boolean
}