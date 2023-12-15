/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lunchtray.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lunchtray.R
import com.example.lunchtray.databinding.FragmentAccompanimentMenuBinding
import com.example.lunchtray.model.OrderViewModel

/**
 * [AccompanimentMenuFragment] allows people to add an accompaniment to their order or cancel the
 * order.
 */
class AccompanimentMenuFragment : Fragment() {

    // Binding object instance corresponding to the fragment_start_order.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var _binding: FragmentAccompanimentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val sharedViewModel: OrderViewModel by activityViewModels<OrderViewModel.Base>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAccompanimentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            accompanimentOptions.setOnCheckedChangeListener { _, checkedId ->
                nextButton.isEnabled = true
                when (checkedId) {
                    R.id.bread -> sharedViewModel.setAccompaniment("bread")
                    R.id.berries -> sharedViewModel.setAccompaniment("berries")
                    R.id.pickles -> sharedViewModel.setAccompaniment("pickles")
                }
            }
            sharedViewModel.subtotalFormatted.observe(viewLifecycleOwner) { newValue ->
                subtotal.text = getString(R.string.subtotal, newValue)
            }
            nextButton.setOnClickListener {
                goToNextScreen()
            }
            cancelButton.setOnClickListener {
                cancelOrder()
            }
            sharedViewModel.menuItems["bread"]?.let { menuItem ->
                bread.text = menuItem.name
                breadDescription.text = menuItem.description
                breadPrice.text = menuItem.getFormattedPrice()
            }
            sharedViewModel.menuItems["berries"]?.let { menuItem ->
                berries.text = menuItem.name
                berriesDescription.text = menuItem.description
                berriesPrice.text = menuItem.getFormattedPrice()
            }
            sharedViewModel.menuItems["pickles"]?.let { menuItem ->
                pickles.text = menuItem.name
                picklesDescription.text = menuItem.description
                picklesPrice.text = menuItem.getFormattedPrice()
            }
            nextButton.isEnabled = accompanimentOptions.checkedRadioButtonId != -1
        }
    }

    /**
     * Navigate to the checkout fragment.
     */
    private fun goToNextScreen() {
        findNavController().navigate(AccompanimentMenuFragmentDirections.actionAccompanimentMenuFragmentToCheckoutFragment())
    }

    /**
     * Cancel the order and start over.
     */
    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(AccompanimentMenuFragmentDirections.actionAccompanimentMenuFragmentToStartOrder())
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
