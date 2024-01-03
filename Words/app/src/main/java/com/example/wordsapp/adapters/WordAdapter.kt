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
package com.example.wordsapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.R
import com.example.wordsapp.ui.WordListFragment

/**
 * Adapter for the [RecyclerView] in [WordListFragment].
 */
class WordAdapter(private val letter: String, context: Context) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private val filteredWords: List<String>

    init {
        // Retrieve the list of words from res/values/arrays.xml
        val words = context.resources.getStringArray(R.array.words).toList()

        filteredWords = words
            // Returns items in a collection if the conditional clause is true,
            // in this case if an item starts with the given letter,
            // ignoring UPPERCASE or lowercase.
            .filter { it.startsWith(letter, ignoreCase = true) }
            // Returns a collection that it has shuffled in place
            .shuffled()
            // Returns the first n items as a [List]
            .take(5)
            // Returns a sorted version of that [List]
            .sorted()
    }

    class WordViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.button_item)
    }

    override fun getItemCount(): Int = filteredWords.size

    /**
     * Creates new views with R.layout.item_view as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        // Setup custom accessibility delegate to set the text read
        layout.accessibilityDelegate = Accessibility

        return WordViewHolder(layout)
    }

    /**
     * Replaces the content of an existing view with new data
     */
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        val item = filteredWords[position]
        // Needed to call startActivity
        val context = holder.view.context

        // Set the text of the WordViewHolder
        with(holder.button) {
            text = item
            setOnClickListener {
                val uri: Uri = Uri.parse("${WordListFragment.SEARCH_PREFIX}$item")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }
        }

    }

    // Setup custom accessibility delegate to set the text read with
    // an accessibility service
    companion object Accessibility : View.AccessibilityDelegate() {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfo,
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            // With `null` as the second argument to [AccessibilityAction], the
            // accessibility service announces "double tap to activate".
            // If a custom string is provided,
            // it announces "double tap to <custom string>".
            val customString = host.context?.getString(R.string.look_up_word)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info.addAction(customClick)
        }
    }
}
