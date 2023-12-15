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
import com.example.lunchtray.databinding.FragmentEntreeMenuBinding
import com.example.lunchtray.model.OrderViewModel

/**
 * [EntreeMenuFragment] allows people to add an entree to the order or cancel the order.
 */
class EntreeMenuFragment : Fragment() {

    /**
     * Binding object instance corresponding to the fragment_start_order.xml layout
     * This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
     * when the view hierarchy is attached to the fragment.
     */
    private var _binding: FragmentEntreeMenuBinding? = null

    /**
    This property is only valid between onCreateView and
    onDestroyView.
     */
    private val binding get() = _binding!!

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val sharedViewModel: OrderViewModel by activityViewModels<OrderViewModel.Base>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEntreeMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            entreeOptions.setOnCheckedChangeListener { _, checkedId ->
                nextButton.isEnabled = true
                when (checkedId) {
                    R.id.cauliflower -> sharedViewModel.setEntree("cauliflower")
                    R.id.chili -> sharedViewModel.setEntree("chili")
                    R.id.pasta -> sharedViewModel.setEntree("pasta")
                    R.id.skillet -> sharedViewModel.setEntree("skillet")
                }
            }
            sharedViewModel.subtotalFormatted.observe(viewLifecycleOwner) { newValue ->
                subtotal.text = newValue
            }
            nextButton.setOnClickListener {
                goToNextScreen()
            }
            cancelButton.setOnClickListener {
                cancelOrder()
            }
            sharedViewModel.menuItems["cauliflower"]?.let { menuItem ->
                cauliflower.text = menuItem.name
                cauliflowerDescription.text = menuItem.description
                cauliflowerPrice.text = menuItem.getFormattedPrice()
            }
            sharedViewModel.menuItems["chili"]?.let { menuItem ->
                chili.text = menuItem.name
                chiliDescription.text = menuItem.description
                chiliPrice.text = menuItem.getFormattedPrice()
            }
            sharedViewModel.menuItems["pasta"]?.let { menuItem ->
                pasta.text = menuItem.name
                pastaDescription.text = menuItem.description
                pastaPrice.text = menuItem.getFormattedPrice()
            }
            sharedViewModel.menuItems["skillet"]?.let { menuItem ->
                skillet.text = menuItem.name
                skilletDescription.text = menuItem.description
                skilletPrice.text = menuItem.getFormattedPrice()
            }
            nextButton.isEnabled = entreeOptions.checkedRadioButtonId != -1
        }
    }

    /**
     * Navigate to the side menu fragment.
     */
    private fun goToNextScreen() {
        findNavController().navigate(EntreeMenuFragmentDirections.actionEntreeMenuFragmentToSideMenuFragment())
    }

    /**
     * Cancel the order and start over.
     */
    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(EntreeMenuFragmentDirections.actionEntreeMenuFragmentToStartOrder())
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
