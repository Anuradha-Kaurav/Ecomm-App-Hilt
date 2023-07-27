package com.ecomm.app.view.ui.interfaces

interface ItemClickListener<T> {

    fun onItemClick(item: T)

    fun onFavClick(item: T)
}