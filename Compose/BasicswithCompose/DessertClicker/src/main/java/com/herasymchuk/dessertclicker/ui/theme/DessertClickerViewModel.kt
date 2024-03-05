package com.herasymchuk.dessertclicker.ui.theme

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.herasymchuk.dessertclicker.R
import com.herasymchuk.dessertclicker.data.Datasource
import com.herasymchuk.dessertclicker.model.Dessert
import com.herasymchuk.dessertclicker.ui.DessertClickerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class DessertClickerViewModel : ViewModel() {
    abstract val state: StateFlow<DessertClickerState>

    abstract fun sellDessert()
    abstract fun determineDessertToShow()
    abstract fun shareSoldDessertsInformation(intentContext: Context)

    class Base : DessertClickerViewModel() {
        private val desserts: List<Dessert> = Datasource.dessertList
        private var currentDessert: Dessert = desserts.first()

        override val state: MutableStateFlow<DessertClickerState> = MutableStateFlow(
            DessertClickerState(0, 0, desserts.first())
        )

        override fun sellDessert() {
            state.update { oldState: DessertClickerState ->
                oldState.copy(
                    revenue = oldState.revenue + oldState.dessert.price,
                    dessertsSold = oldState.dessertsSold + 1
                )
            }
            determineDessertToShow()
        }

        /**
         * Determine which dessert to show.
         */
        override fun determineDessertToShow(
        ) {
            var dessertToShow: Dessert = desserts.first()
            for (dessert: Dessert in desserts) {
                if (state.value.dessertsSold >= dessert.startNextDessertAmound) {
                    dessertToShow = dessert
                } else {
                    // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                    // you'll start producing more expensive desserts as determined by startProductionAmount
                    // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                    // than the amount sold.
                    break
                }
            }

            state.update { it.copy(dessert = dessertToShow) }
        }

        /**
         * Share desserts sold information using ACTION_SEND intent
         */
        override fun shareSoldDessertsInformation(intentContext: Context) {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    intentContext.getString(
                        R.string.share_text,
                        state.value.dessertsSold,
                        state.value.revenue
                    )
                )
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)

            try {
                ContextCompat.startActivity(intentContext, shareIntent, null)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    intentContext,
                    intentContext.getString(R.string.sharing_not_available),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    class BaseFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(Base::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return Base() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
