package com.example.cinemaspot.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.inject.Inject

class EncryptedPrefsManager@Inject constructor(context: Context) {
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveSessionId(sessionId: String) {
        sharedPreferences.edit().putString("session_id", sessionId).apply()
    }

    fun getSessionId(): String? {
        return sharedPreferences.getString("session_id", null)
    }

    fun clearSessionId() {
        sharedPreferences.edit().remove("session_id").apply()
    }
}
