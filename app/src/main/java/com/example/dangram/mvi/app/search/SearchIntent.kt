package com.example.dangram.mvi.app.search

sealed class SearchIntent {
    class OnEmailChanged(val email: String) : SearchIntent()
    data object SearchByEmail : SearchIntent()
    data object SetFalseIsUserFounded : SearchIntent()
    data object CreateChannel : SearchIntent()
    data object NavigateBack : SearchIntent()
}