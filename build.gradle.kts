// Top-level build file where you can add configuration options common to all sub-projects/modules.
// id("com.google.gms.google-services") version "4.4.4" apply false
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
}