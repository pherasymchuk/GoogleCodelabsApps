/*
 * Copyright (c) 2021 The Android Open Source Project
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

package com.example.android.sports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.android.sports.databinding.FragmentSportsListBinding
import com.example.android.sports.insetter.Insetter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SportsListFragment : Fragment() {

    private val sportsViewModel: SportsViewModel by activityViewModels<SportsViewModel.Base>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentSportsListBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSportsListBinding.bind(view)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            SportsListOnBackPressedCallback(binding.slidingPaneLayout)
        )
        binding.slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED

        Insetter.Base(binding.recyclerView).apply(bottom = true, left = true)
        Insetter.Base(binding.detailContainer).apply(bottom = true, right = true)

        // Initialize the adapter and set it to the RecyclerView.
        val adapter = SportsAdapter {
            // Update the user selected sport as the current sport in the shared viewmodel
            // This will automatically update the dual pane content
            sportsViewModel.updateCurrentSport(it)
            // Navigate to the details screen
            binding.slidingPaneLayout.openPane()
        }
        binding.recyclerView.adapter = adapter
        adapter.submitList(sportsViewModel.sportsData)
    }
}

class SportsListOnBackPressedCallback(private val slidingPaneLayout: SlidingPaneLayout) :
    OnBackPressedCallback(enabled = slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen),
    SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }

    override fun handleOnBackPressed() {
        slidingPaneLayout.closePane()
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {}

    override fun onPanelOpened(panel: View) {
        isEnabled = true
    }

    override fun onPanelClosed(panel: View) {
        isEnabled = false
    }
}
