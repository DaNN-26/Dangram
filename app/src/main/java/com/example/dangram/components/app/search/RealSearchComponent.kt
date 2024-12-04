package com.example.dangram.components.app.search

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.dangram.mvi.app.search.SearchIntent
import com.example.dangram.mvi.app.search.SearchState
import com.example.dangram.stream.model.FoundUser
import com.example.dangram.stream.repository.domain.StreamRepository
import com.google.firebase.auth.FirebaseAuth
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RealSearchComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val firebaseAuth: FirebaseAuth,
    private val streamRepository: StreamRepository,
    private val navigateToChannel: (Channel) -> Unit,
    private val navigateBack: () -> Unit
) : SearchComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(SEARCH_COMPONENT, SearchState.serializer()) ?: SearchState()
    )

    override val state = _state

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun processIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.OnEmailChanged -> { _state.update { it.copy(email = intent.email) } }
            is SearchIntent.SearchByEmail -> { searchByEmail() }
            is SearchIntent.SetFalseIsUserFounded -> { _state.update { it.copy(isUserFounded = false) } }
            is SearchIntent.CreateChannel -> { createChannel() }
            is SearchIntent.NavigateBack -> { navigateBack() }
        }
    }

    private fun searchByEmail() {
       scope.launch {
           val foundUser = streamRepository.findUserByEmail(email = state.value.email)
           if(foundUser != null && foundUser.id != firebaseAuth.currentUser!!.uid)
               _state.update { it.copy(
                   foundUser = foundUser.toFoundUser(),
                   isUserFounded = true,
                   isUserNull = false,
                   isCurrentUser = false
               ) }
           else if(foundUser?.id == firebaseAuth.currentUser!!.uid)
               _state.update { it.copy(
                   isCurrentUser = true,
                   isUserNull = false,
                   isUserFounded = false
               ) }
           else
               _state.update { it.copy(
                   isUserNull = true,
                   isCurrentUser = false,
                   isUserFounded = false
               ) }
       }
    }

    private fun createChannel() {
        scope.launch {
            try {
                val channel = streamRepository.createChannel(
                    userId = state.value.foundUser?.id ?: "",
                    currentUserId = firebaseAuth.currentUser?.uid ?: "",
                )
                if(channel != null)
                    navigateToChannel(channel)
            } catch (e: Exception) {
                Log.d("Channel Creating", e.message.toString())
            }
        }
    }

    private fun User.toFoundUser(): FoundUser =
        FoundUser(
            id = this.id,
            email = this.name,
            avatar = this.image
        )

    companion object {
        const val SEARCH_COMPONENT = "SEARCH_COMPONENT"
    }
}