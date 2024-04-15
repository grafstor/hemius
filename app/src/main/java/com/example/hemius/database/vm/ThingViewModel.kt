package com.example.hemius.database.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.hemius.database.dao.ThingDao
import com.example.hemius.database.entities.Thing
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ThingViewModel(
    private val dao: ThingDao
): ViewModel() {
    private val _things = dao.getAllUnarchived()
    private val _archive_things = dao.getAllArchived()

    private val _state = MutableStateFlow(ThingState())

    val state = combine(_state, _things, _archive_things) { state, things, archivedThings ->
        state.copy(
            things = things,
            archivedThings = archivedThings
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThingState())

    fun onEvent(event: ThingEvent){
        when(event){
            is ThingEvent.DeleteSelected -> {
                viewModelScope.launch {
                    _state.value.selectedThings.forEach{ thing ->
                        dao.delete(thing)
                    }
                    _state.update { it.copy(
                        selectedThings = emptyList(),
                        isThingSelected = false
                    ) }
                }
            }
            ThingEvent.Save -> {
                val name = state.value.name
                val description = state.value.description

                if(name.isBlank() || description.isBlank()) {
                    return
                }
                val thing = Thing(
                    name = name,
                    description = description,
                    image = _state.value.image
                )

                viewModelScope.launch {
                    dao.upsert(thing)
                }

                _state.update { it.copy(
                    name = "",
                    description = "",
                ) }
            }
            is ThingEvent.SetName -> {
                _state.update { it.copy(
                    name = event.name
                ) }
            }
            is ThingEvent.SetDescription -> {
                _state.update { it.copy(
                    description = event.description
                ) }
            }
            is ThingEvent.SetImage -> {
                _state.update { it.copy(
                    image = event.image
                ) }
            }
            is ThingEvent.ArchiveSelected -> {
                viewModelScope.launch {
                    _state.value.selectedThings.forEach{ thing ->
                        val updatedThing = thing.copy(isArchived = true)
                        dao.upsert(updatedThing)
                    }
                    _state.update { it.copy(
                        selectedThings = emptyList(),
                        isThingSelected = false
                    ) }
                }
            }
            is ThingEvent.SelectFlder -> {
                _state.update { it.copy(
                    selectedFolder = event.folder
                ) }
            }
            is ThingEvent.SelectThing -> {
                if (_state.value.selectedThings.isEmpty()){
                    _state.update { it.copy(
                        isThingSelected = true
                    ) }
                }
                _state.update { currentState ->
                    val newSelectedThings = currentState.selectedThings.toMutableList().apply {
                        add(event.thing)
                    }
                    currentState.copy(selectedThings = newSelectedThings)
                }
            }
            is ThingEvent.DeselectThing -> {
                _state.update { currentState ->
                    val newSelectedThings = currentState.selectedThings.toMutableList().apply {
                        remove(event.thing)
                    }
                    currentState.copy(selectedThings = newSelectedThings)
                }
                if (_state.value.selectedThings.isEmpty()){
                    _state.update { it.copy(
                        isThingSelected = false
                    ) }
                }
            }
            is ThingEvent.DeselectSelected -> {
                _state.update { it.copy(
                    selectedThings = emptyList(),
                    isThingSelected = false
                ) }
            }
        }
    }
}