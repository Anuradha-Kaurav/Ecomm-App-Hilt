package com.ecomm.app.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ecomm.app.db.Converters
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Product(
    val category: String,
    val description: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val price: Double,
    @TypeConverters(Converters::class)
    val rating: Rating,
    val title: String,
    var isInWishlist: Boolean,
): Parcelable