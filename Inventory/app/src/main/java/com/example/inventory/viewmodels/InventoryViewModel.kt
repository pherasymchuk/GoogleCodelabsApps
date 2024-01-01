package com.example.inventory.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.launch

abstract class InventoryViewModel(protected val itemDao: ItemDao) : ViewModel() {
    abstract val allItems: LiveData<List<Item>>

    abstract fun insertItem(item: Item)
    abstract fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Item
    abstract fun addNewItem(itemName: String, itemPrice: String, itemCount: String)
    abstract fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean
    abstract fun retrieveItem(id: Int): LiveData<Item>
    abstract fun updateItem(item: Item)
    abstract fun sellItem(item: Item)
    abstract fun isStockAvailable(item: Item): Boolean
    abstract fun deleteItem(item: Item)
    protected abstract fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String,
    ): Item

    abstract fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String,
    )

    class Base(itemDao: ItemDao) : InventoryViewModel(itemDao) {
        override val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

        override fun insertItem(item: Item) {
            viewModelScope.launch {
                itemDao.insert(item)
            }
        }

        override fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Item {
            return Item(
                itemName = itemName,
                itemPrice = itemPrice.toDouble(),
                quantityInStock = itemCount.toInt()
            )
        }

        override fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
            val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
            insertItem(newItem)
        }

        override fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
            return !(itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank())
        }

        override fun retrieveItem(id: Int): LiveData<Item> {
            return itemDao.getItem(id).asLiveData()
        }

        override fun updateItem(item: Item) {
            viewModelScope.launch {
                itemDao.update(item)
            }
        }

        override fun updateItem(
            itemId: Int,
            itemName: String,
            itemPrice: String,
            itemCount: String,
        ) {
            val updatedEntry = getUpdatedItemEntry(itemId, itemName, itemPrice, itemCount)
            updateItem(updatedEntry)
        }

        override fun sellItem(item: Item) {
            if (item.quantityInStock > 0) {
                val newItem = item.copy(quantityInStock = item.quantityInStock - 1)
                updateItem(newItem)
            }
        }

        override fun isStockAvailable(item: Item): Boolean = item.quantityInStock > 0

        override fun deleteItem(item: Item) {
            viewModelScope.launch {
                itemDao.delete(item)
            }
        }

        override fun getUpdatedItemEntry(
            itemId: Int,
            itemName: String,
            itemPrice: String,
            itemCount: String,
        ): Item {
            return Item(itemId, itemName, itemPrice.toDouble(), itemCount.toInt())
        }
    }
}

class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (InventoryViewModel.Base::class.java.isAssignableFrom(modelClass)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel.Base(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
