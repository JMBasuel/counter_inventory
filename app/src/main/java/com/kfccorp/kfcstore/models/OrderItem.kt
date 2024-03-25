package com.kfccorp.kfcstore.models

data class OrderItem(
    val id: String? = null,
    var total: Int? = null,
    var order: ArrayList<OrderItemItem>? = null,
    var cash: Int? = null,
    var change: Int? = null,
    var timestamp: String? = null
)

data class OrderItemItem(
    val id: String? = null,
    val name: String? = null,
    val customs: Any? = null,
    val price: Int? = null,
    val amount: Int? = null,
    val total: Int? = null
)