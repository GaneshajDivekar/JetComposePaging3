package jetcompose.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPref(
    context: Context
) {

    private val application=context.applicationContext
    private val dataStore:DataStore<Preferences>

    init {
        dataStore=application.createDataStore(name = "user")
    }

    val authToken:Flow<String?>
    get() = dataStore.data.map {preferences ->
        preferences[KEY_AUTH]
    }

    suspend fun saveAuthToken(authToken:String)
    {
        dataStore.edit {preferences ->
            preferences[KEY_AUTH]=authToken
        }
    }
    companion object{
        private val KEY_AUTH= preferencesKey<String>("key_auth")
    }
}