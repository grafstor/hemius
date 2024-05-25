package com.example.hemius.database.vm

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hemius.database.dao.FolderDao

import com.example.hemius.database.dao.ThingDao
import com.example.hemius.database.entities.Folder
import com.example.hemius.database.entities.Thing
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.util.Log
import com.example.hemius.database.dao.SettingsDao
import com.example.hemius.database.entities.Settings
import com.example.hemius.database.entities.ThingFolderCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@OptIn(ExperimentalCoroutinesApi::class)
class ThingViewModel(
    private val dao: ThingDao,
    private val folderDao: FolderDao,
    private val settingsDao: SettingsDao,
    private val applicationContext: Context,
): ViewModel() {
    companion object {
        const val ALL_FOLDERS_ID = -1
    }
    init {
        viewModelScope.launch {
            val settings = settingsDao.getSettings()
            settings?.let {
                _state.update { it.copy(isDarkTheme = settings.isDarkTheme) }
            }
        }
    }
    private val _state = MutableStateFlow(ThingState())

    private val _things = _state.flatMapLatest { state ->
        val thingsFlow = if (state.selectedFolderId == ALL_FOLDERS_ID) {
            dao.getAllThings(state.archivedFilter)
        } else {
            if (state.archivedFilter){
                dao.getAllThings(state.archivedFilter)
            }else {
                dao.getThingsByFolder(state.selectedFolderId)
            }
        }

        thingsFlow.map { things ->
            if (state.search.isEmpty()) {
                things
            } else {
                things.filter {
                    it.name?.contains(state.search, ignoreCase = true) == true ||
                            it.description?.contains(state.search, ignoreCase = true) == true
                }
            }
        }
    }

    private val _folders = folderDao.getAllFoldersWithImages()

    val state = combine(_state, _things, _folders) { state, things, foldersWithImages ->
        val folderMap = foldersWithImages.groupBy { it.folderId }.mapValues { entry ->
            entry.value.first().folderName to entry.value.mapNotNull { it.imagePath }
        }
        state.copy(
            things = things,
            folders = folderMap.map { Folder(it.key, it.value.first) to it.value.second }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThingState())

    fun saveImageToDevice(bitmap: Bitmap?): String? {
        if (bitmap == null) return null

        val context = applicationContext
        val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val fileName = "image_${System.currentTimeMillis()}.png"
        val file = File(imagesDir, fileName)

        return try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
    fun onEvent(event: ThingEvent){
        when (event) {
            is ThingEvent.SetSearch -> {
                _state.update {
                    it.copy(
                        search = event.search
                    )
                }
            }
            is ThingEvent.SetName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
            is ThingEvent.SetDescription -> {
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
            }
            is ThingEvent.SetFolderName -> {
                _state.update {
                    it.copy(
                        folderName = event.name
                    )
                }
            }
            is ThingEvent.Save -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val name = state.value.name
                    val description = state.value.description

                    if (!name.isBlank() || !description.isBlank()) {
                        val imageBitmap = _state.value.image
                        val imagePath = saveImageToDevice(imageBitmap)

                        val thing = Thing(
                            name = name,
                            description = description,
                            imagePath = imagePath
                        )

                        dao.upsert(thing)

                        _state.update {
                            it.copy(
                                name = "",
                                description = "",
                                image = null
                            )
                        }
                    }
                }
            }
            is ThingEvent.SetImage -> {
                _state.update {
                    it.copy(
                        image = event.image
                    )
                }
            }
            is ThingEvent.DeleteFromFolderSelected -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.value.selectedThings.forEach { thing ->
                        dao.deleteThingFromFolder(thing.uid, _state.value.selectedFolderId)
                    }
                    onEvent(ThingEvent.DeselectSelected)
                }
            }
            is ThingEvent.ArchiveSelected -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.value.selectedThings.forEach { thing ->
                        val updatedThing = thing.copy(isArchived = true)
                        dao.upsert(updatedThing)
                    }
                    _state.update {
                        it.copy(
                            selectedThings = emptyList(),
                            isThingSelected = false
                        )
                    }
                }
            }
            is ThingEvent.ToggleArchive -> {
                _state.update {
                    it.copy(
                        archivedFilter = event.isArchive
                    )
                }
            }
            is ThingEvent.DeleteSelected -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.value.selectedThings.forEach { thing ->
                        dao.delete(thing)
                    }
                    _state.update {
                        it.copy(
                            selectedThings = emptyList(),
                            isThingSelected = false
                        )
                    }
                }
            }

            is ThingEvent.UnarchiveSelected -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.value.selectedThings.forEach { thing ->
                        val updatedThing = thing.copy(isArchived = false)
                        dao.upsert(updatedThing)
                    }
                    _state.update {
                        it.copy(
                            selectedThings = emptyList(),
                            isThingSelected = false
                        )
                    }
                }
            }
            is ThingEvent.ToggleItemInList -> {
                if (_state.value.selectedThings.contains(event.thing)) {
                    onEvent(ThingEvent.DeselectThing(event.thing))
                } else {
                    onEvent(ThingEvent.SelectThing(event.thing))
                }
            }
            is ThingEvent.SelectThing -> {
                val newSelectedThings = _state.value.selectedThings.toMutableList().apply {
                    add(event.thing)
                }
                _state.update { currentState ->
                    currentState.copy(
                        selectedThings = newSelectedThings,
                        isThingSelected = true
                    )
                }
            }
            is ThingEvent.DeselectThing -> {
                val newSelectedThings = _state.value.selectedThings.toMutableList().apply {
                    remove(event.thing)
                }
                _state.update { currentState ->
                    currentState.copy(
                        selectedThings = newSelectedThings,
                        isThingSelected = newSelectedThings.isNotEmpty()
                    )
                }
            }
            is ThingEvent.SaveSelected -> {
                _state.update {
                    it.copy(
                        moveableThings = state.value.selectedThings
                    )
                }
            }
            is ThingEvent.DeselectSelected -> {
                _state.update {
                    it.copy(
                        selectedThings = emptyList(),
                        isThingSelected = false
                    )
                }
            }
            is ThingEvent.OpenThing -> {
                _state.update {
                    it.copy(
                        thing = event.thing
                    )
                }
            }
            is ThingEvent.SelectFolder -> {
                _state.update { it.copy(selectedFolderId = event.folderId) }
            }
            is ThingEvent.ToggleFolderCreation -> {
                _state.update {
                    it.copy(
                        isFolderCreation = event.creation,
                    )
                }
            }
            is ThingEvent.SaveFolder -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val name = state.value.folderName

                    if (!name.isBlank()) {
                        val folder = Folder(
                            name = name,
                        )

                        folderDao.upsert(folder)

                        _state.update {
                            it.copy(
                                folderName = "",
                            )
                        }
                    }
                }
            }
            is ThingEvent.ToggleToFolderMoving -> {
                _state.update {
                    it.copy(
                        isToFolderMoving = event.isMoving,
                    )
                }
            }
            is ThingEvent.ToFolderSelected -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.value.moveableThings.forEach { thing ->
                        if (_state.value.isToFolderMoving) {
                            dao.deleteThingFromFolder(thing.uid, _state.value.selectedFolderId)
                        }
                        dao.insertThingFolderCrossRef(
                            ThingFolderCrossRef(
                                thing.uid,
                                event.folderId
                            )
                        )
                    }
                    _state.update {
                        it.copy(
                            moveableThings = emptyList(),
                            isToFolderMoving = false,
                        )
                    }
                }
            }
            is ThingEvent.ToggleDarkTheme -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.update {
                        it.copy(
                            isDarkTheme = event.isDarkTheme
                        )
                    }
                    settingsDao.upsert(Settings(isDarkTheme = event.isDarkTheme))
                }
            }
            else -> {}
        }
    }

}