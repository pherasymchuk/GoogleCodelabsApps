package com.example.wordsapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.R
import com.example.wordsapp.adapters.LetterAdapter
import com.example.wordsapp.data.SettingsDataStore
import com.example.wordsapp.databinding.FragmentLetterListBinding
import kotlinx.coroutines.launch

class LetterListFragment : Fragment() {
    private var _binding: FragmentLetterListBinding? = null
    private val binding: FragmentLetterListBinding = _binding!!
    private var layout: Layout = Layout.Linear
    private lateinit var settingsDataStore: SettingsDataStore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = LetterAdapter()
        settingsDataStore = SettingsDataStore(requireContext())

        val menuHost: MenuHost = this.requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.layout_menu, menu)

                val layoutButton = menu.findItem(R.id.action_switch_layout)
                layout.apply(requireContext(), binding.recyclerView, layoutButton)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
                R.id.action_switch_layout -> {
                    layout = if (layout == Layout.Linear) Layout.Grid else Layout.Linear
                    layout.apply(
                        requireContext(), binding.recyclerView, menuItem
                    )
                    lifecycleScope.launch {
                        settingsDataStore.saveLayoutToPreferencesStore(
                            layout is Layout.Linear,
                            requireContext()
                        )
                    }
                    true
                }

                else -> false
            }

        }, viewLifecycleOwner)

        settingsDataStore.preferenceFlow.asLiveData()
            .observe(viewLifecycleOwner) { isLinearLayout ->
                layout = if (isLinearLayout) {
                    Layout.Linear
                } else {
                    Layout.Grid
                }
                menuHost.invalidateMenu()
            }

        ViewCompat.setOnApplyWindowInsetsListener(binding.recyclerView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(bottom = insets.bottom)
            windowInsets
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface Layout {
    fun apply(context: Context, recyclerView: RecyclerView, menuItem: MenuItem)

    object Linear : Layout {
        override fun apply(context: Context, recyclerView: RecyclerView, menuItem: MenuItem) {
            menuItem.icon = ContextCompat.getDrawable(context, R.drawable.ic_view_grid)
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    object Grid : Layout {
        override fun apply(context: Context, recyclerView: RecyclerView, menuItem: MenuItem) {
            menuItem.icon = ContextCompat.getDrawable(context, R.drawable.ic_view_list)
            recyclerView.layoutManager = GridLayoutManager(context, 4)
        }
    }
}
