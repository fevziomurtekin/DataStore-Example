package com.fevziomurtekin.datastore_example.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserNameRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val KEY_USER_NAME = stringPreferencesKey("username")
    }

    val userPreferencesFlow: Flow<UserNameUiModel> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            UserNameUiModel(
                userName = preferences[PreferencesKeys.KEY_USER_NAME],
                isInitial = preferences[PreferencesKeys.KEY_USER_NAME].isNullOrEmpty()
            )
        }

    suspend fun updateUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.KEY_USER_NAME] = name
        }
    }
}