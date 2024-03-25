package com.kfccorp.kfcstore.interfaces

interface InventoryClickListener
{
    fun onEditConfirm(id: String, price: Float, quantity: Int)
    fun onDeleteClick(id: String)
}