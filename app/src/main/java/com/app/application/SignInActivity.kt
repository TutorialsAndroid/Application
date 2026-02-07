package com.app.application

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    //UI Components
    private lateinit var btnSignIn: MaterialButton
    private var loadingDialog: Dialog? = null

    //Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    override fun onStart() {
        super.onStart()

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            //User is already signed in
            homeScreen()
        } else {
            //User is not signed in
            return
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        //Initialize views of this activity
        initializeViews()
    }

    private fun initializeViews() {
        btnSignIn = findViewById(R.id.btnSignIn)
        btnSignIn.setOnClickListener(this@SignInActivity)
    }

    override fun onClick(v: View?) {
        if (v == btnSignIn) {
            showTermsDialog(this) {
                // User accepted
                //Sign in anonymously
                signInAnonymously()
            }
        }
    }

    private fun signInAnonymously() {
        loadingDialog = showSimpleLoading(this@SignInActivity)
        auth.signInAnonymously().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                //Sign in was successful
                loadingDialog?.dismiss()
                loadingDialog = null

                firebaseUser = auth.currentUser!!
                homeScreen()
            } else {
                //Sign in failed
                loadingDialog?.dismiss()
                loadingDialog = null

                Log.e("SignInActivity", "Sign in failed", task.exception)
                showToast(this@SignInActivity, getString(R.string.signInError))
            }
        }
    }

    private fun homeScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}