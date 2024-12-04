package com.example.dangram.components.app.search

import com.arkivanov.decompose.value.Value
import com.example.dangram.mvi.app.search.SearchIntent
import com.example.dangram.mvi.app.search.SearchState

interface SearchComponent {
    val state: Value<SearchState>

    fun processIntent(intent: SearchIntent)
}