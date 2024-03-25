package com.kfccorp.kfcstore.models

data class InventoryItem(
    val id: String? = null,
    val product: String? = null,
    val price: Float? = null,
    val category: String? = null,
    val quantity: Int? = null
)