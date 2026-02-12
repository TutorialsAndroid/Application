package com.app.noteva

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth
import androidx.core.net.toUri

class SettingsFragment : PreferenceFragmentCompat() {

    private val privacyPolicy = "https://noteva-android.web.app/privacy.html"
    private val termsOfService = "https://noteva-android.web.app/terms.html"
    private val github = "https://github.com/TutorialsAndroid"


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_settings, rootKey)

        val user = FirebaseAuth.getInstance().currentUser

        // UID
        findPreference<Preference>("uid")?.summary =
            user?.uid ?: "Not logged in"

        // Version
        val versionName = BuildConfig.VERSION_NAME
        findPreference<Preference>("version")?.summary = versionName

        // Privacy
        findPreference<Preference>("privacy")?.setOnPreferenceClickListener {
            openCustomTab(requireContext(), privacyPolicy)
            true
        }

        // Terms
        findPreference<Preference>("terms")?.setOnPreferenceClickListener {
            openCustomTab(requireContext(), termsOfService)
            true
        }

        // Contact email
        findPreference<Preference>("contact")?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:heaticdeveloper@gmail.com".toUri()
                putExtra(Intent.EXTRA_SUBJECT, "Noteva Support")
            }
            startActivity(intent)
            true
        }

        // GitHub
        findPreference<Preference>("github")?.setOnPreferenceClickListener {
            openCustomTab(requireContext(), github)
            true
        }
    }
}
