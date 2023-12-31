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
import com.example.cupcake.databinding.FragmentPickupBinding
import com.example.cupcake.model.OrderViewModel

/**
 * [PickupFragment] allows the user to choose a pickup date for the cupcake order.
 */
class PickupFragment : Fragment() {

    // Binding object instance corresponding to the fragment_pickup.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentPickupBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels<OrderViewModel.Base>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pickup, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            pickupFragment = this@PickupFragment
            viewModel = sharedViewModel

            /*            if (dateOptions.checkedRadioButtonId == R.id.option0 || sharedViewModel.flavor.value == getString(R.string.flavor_secret)) {
                            dateOptions.clearCheck()
                            option0.isEnabled = false
                            dateOptions.check(R.id.option1)
                        } else {
                            dateOptions.check(R.id.option0)
                        }*/

            setupDayOptions()
        }
    }

    private fun setupDayOptions() {
        binding?.apply {
            option0.text = sharedViewModel.dateOptions[0]
            option0.setOnClickListener {
                sharedViewModel.setDate(sharedViewModel.dateOptions[0])
            }
            if (sharedViewModel.flavor.value == getString(R.string.flavor_secret)) {
                option0.isEnabled = false
                dateOptions.check(R.id.option1)
            }

            option1.text = sharedViewModel.dateOptions[1]
            option1.setOnClickListener {
                sharedViewModel.setDate(sharedViewModel.dateOptions[1])
            }

            option2.text = sharedViewModel.dateOptions[2]
            option2.setOnClickListener {
                sharedViewModel.setDate(sharedViewModel.dateOptions[2])
            }

            option3.text = sharedViewModel.dateOptions[3]
            option3.setOnClickListener {
                sharedViewModel.setDate(sharedViewModel.dateOptions[3])
            }
        }
    }

    /**
     * Navigate to the next screen to see the order summary.
     */
    fun goToNextScreen() {
        this.findNavController()
            .navigate(PickupFragmentDirections.actionPickupFragmentToSummaryFragment())
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(PickupFragmentDirections.actionPickupFragmentToStartFragment2())
    }
}
