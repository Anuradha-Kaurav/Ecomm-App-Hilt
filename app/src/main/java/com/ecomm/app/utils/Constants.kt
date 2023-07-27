package com.ecomm.app.utils

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.ecomm.app.R

object Constants {

    const val BASE_URL = "https://fakestoreapi.com/"
    const val PRODUCT = "Product"
    const val DB_NAME = "ProductsDB"

    inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }

    fun appendString(firstString: String, secondString: String) : String{
        val sb = StringBuilder()
        sb.append( firstString).append(secondString)
        return sb.toString()
    }

    fun selectFav(favIcon: ImageView, context: Context) {
        favIcon.setImageDrawable(
            AppCompatResources.getDrawable(
                context,
                R.drawable.ic_fav_selected
            )
        )
    }

    fun unSelectFav(favIcon: ImageView, context: Context) {
        favIcon.setImageDrawable(
            AppCompatResources.getDrawable(
                context,
                R.drawable.ic_fav
            )
        )
    }
}