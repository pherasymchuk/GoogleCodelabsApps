package com.example.inventory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "item_name") val itemName: String,
    @ColumnInfo(name = "item_price") val itemPrice: Double,
    @ColumnInfo(name = "quantity_in_stock") val quantityInStock: Int,
)
