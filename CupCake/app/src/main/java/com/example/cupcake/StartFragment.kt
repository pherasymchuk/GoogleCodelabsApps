/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentStartBinding
import com.example.cupcake.model.OrderViewModel


/**
 * `StartFragment` is a type of Fragment wherein the initial steps of the Cupcake ordering process occur.
 * It utilizes data binding for managing UI components and interacting with shared `OrderViewModel`.
 *
 * Class Properties:
 * @property binding An instance of FragmentStartBinding. Corresponds to the fragment_start.xml layout.
 * It is not null between onCreateView() and onDestroyView() lifecycle callbacks, when the view hierarchy is connected to this fragment.
 * @property sharedViewModel A `OrderViewModel` that holds data related to the Cupcake order shared across multiple fragments.
 *
 * Major Methods:
 * - `onCreateView()` inflates the layout for this fragment.
 * - `onViewCreated()` handles necessary actions after the view is created like setting the lifecycle owner, initiating button click listeners, and setting the default flavor.
 * - `orderCupcake(quantity: Int)` allows starting an order with the desired quantity of cupcakes and navigates to the next screen.
 * - `onDestroyView()` performs clean-up actions when the view hierarchy associated with the fragment is being removed.
 */

class StartFragment : Fragment() {

    // Binding object instance corresponding to the fragment_start.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentStartBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels<OrderViewModel.Base>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = requireActivity()
            startFragment = this@StartFragment
            // Set up the button click listeners
        }
        sharedViewModel.setFlavor(getString(R.string.vanilla))
    }

    /**
     * Start an order with the desired quantity of cupcakes and navigate to the next screen.
     */
    fun orderCupcake(quantity: Int) {
        sharedViewModel.setQuantity(quantity)
        findNavController().navigate(StartFragmentDirections.actionStartFragmentToFlavorFragment2())
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
