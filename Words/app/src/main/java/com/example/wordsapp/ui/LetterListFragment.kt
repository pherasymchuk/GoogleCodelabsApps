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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.R
import com.example.wordsapp.adapters.LetterAdapter
import com.example.wordsapp.data.SettingsDataStore
import com.example.wordsapp.databinding.FragmentLetterListBinding

class LetterListFragment : Fragment() {
    private var binding: FragmentLetterListBinding? = null
    private var layout: Layout = Layout.Linear
    private lateinit var recyclerView: RecyclerView
    private lateinit var settingsDataStore: SettingsDataStore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLetterListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding!!.recyclerView
        recyclerView.adapter = LetterAdapter()

        val menuHost: MenuHost = this.requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.layout_menu, menu)

                val layoutButton = menu.findItem(R.id.action_switch_layout)
                layout.apply(requireContext(), recyclerView, layoutButton)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
                R.id.action_switch_layout -> {
                    layout = if (layout == Layout.Linear) Layout.Grid else Layout.Linear
                    layout.apply(
                        requireContext(), binding!!.recyclerView, menuItem
                    )
                    true
                }

                else -> false
            }

        }, viewLifecycleOwner)

        settingsDataStore = SettingsDataStore(requireContext())
        settingsDataStore.preferenceFlow.asLiveData().observe(viewLifecycleOwner) { newValue ->

        }

        ViewCompat.setOnApplyWindowInsetsListener(binding!!.recyclerView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(bottom = insets.bottom)
            windowInsets
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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
