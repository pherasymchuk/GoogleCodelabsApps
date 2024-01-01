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
import com.example.lunchtray.databinding.FragmentCheckoutBinding
import com.example.lunchtray.model.OrderViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * [CheckoutFragment] allows people to apply a coupon to their order, submit order, or cancel order.
 */
class CheckoutFragment : Fragment() {

    // Binding object instance corresponding to the fragment_start_order.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var _binding: FragmentCheckoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val sharedViewModel: OrderViewModel by activityViewModels<OrderViewModel.Base>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)

        // Calculate tax and total upon creating the CheckoutFragment view
        sharedViewModel.calculateTaxAndTotal()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            cancelButton.setOnClickListener {
                cancelOrder()
            }
            submitButton.setOnClickListener {
                submitOrder()
            }
            sharedViewModel.entree.observe(viewLifecycleOwner) { menuItem ->
                entreeSelection.text = menuItem?.name
                entreePrice.text = menuItem?.getFormattedPrice()
            }
            sharedViewModel.side.observe(viewLifecycleOwner) { menuItem ->
                sideSelection.text = menuItem?.name
                sidePrice.text = menuItem?.getFormattedPrice()
            }
            sharedViewModel.accompaniment.observe(viewLifecycleOwner) { menuItem ->
                accompanimentSelection.text = menuItem?.name
                accompanimentPrice.text = menuItem?.getFormattedPrice()
            }
            sharedViewModel.subtotalFormatted.observe(viewLifecycleOwner) { newValue ->
                subtotal.text = getString(R.string.subtotal, newValue)
            }
            sharedViewModel.taxFormatted.observe(viewLifecycleOwner) { newTax ->
                tax.text = getString(R.string.tax, newTax)
            }
            sharedViewModel.totalFormatted.observe(viewLifecycleOwner) { totalPrice ->
                total.text = getString(R.string.total, totalPrice)
            }
        }
    }

    /**
     * Cancel the order and start over.
     */
    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToStartOrderFragment())
    }

    /**
     * Submit order and navigate to home screen.
     */
    private fun submitOrder() {
        Snackbar.make(binding.root, R.string.submit_order, Snackbar.LENGTH_SHORT).show()
        sharedViewModel.resetOrder()
        findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToStartOrderFragment())
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
