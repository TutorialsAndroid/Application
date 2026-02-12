package com.app.noteva

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var inputLayoutEt: TextInputEditText
    private lateinit var inputLayout: TextInputLayout
    private lateinit var autoSaveText: TextView
    private var textWatcher: TextWatcher? = null
    private var loadingDialog: Dialog? = null

    //FirebaseUser
    private lateinit var user: FirebaseUser

    //FirebaseDatabase
    private lateinit var notesRef: DatabaseReference

    private lateinit var consentManager: ConsentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        //Initialize firebase user
        user = FirebaseAuth.getInstance().currentUser!!

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize views of this activity
        initializeViews()

        consentManager = ConsentManager(this)

        if (!consentManager.isAccepted()) {
            showTermsDialog(
                context = this,
                onAccept = {
                    consentManager.setAccepted(true)
                }
            )
        }
    }

    override fun onStart() {
        super.onStart()
        loadingDialog = showSimpleLoading(this@MainActivity)
        loadingDialog?.show()

        notesRef = FirebaseDatabase
            .getInstance()
            .getReference("note")
            .child(user.uid)

        getSavedNoteFromDB()
        saveNoteWhileTyping()
    }

    override fun onStop() {
        super.onStop()
        textWatcher?.let { inputLayoutEt.removeTextChangedListener(it) }
    }

    private fun initializeViews() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        inputLayoutEt = findViewById(R.id.inputLayoutEt)
        inputLayout = findViewById(R.id.inputLayout)

        autoSaveText = findViewById(R.id.autoSaveMsg)

        //Show the status bar at the top of the screen
        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { view, insets ->
            val topInset = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.setPadding(0, topInset, 0, 0)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        //Show the overflow icons in the toolbar
        try {
            val field = menu?.javaClass?.getDeclaredField("mOptionalIconsVisible")
            field?.isAccessible = true
            field?.setBoolean(menu, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            return true
        }
        if (item.itemId == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut()

            showToast(this@MainActivity, "Logged out successfully")
            //Go to SignInActivity
            startActivity(Intent(this@MainActivity, SignInActivity::class.java))
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNoteWhileTyping() {
        textWatcher = object : TextWatcher {
            private var timer: CountDownTimer? = null

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateAutoSaveMsg("not saved")
                timer?.cancel()
                timer = object : CountDownTimer(700, 700) {
                    override fun onTick(p0: Long) {}
                    override fun onFinish() {
                        notesRef.setValue(s.toString()).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                updateAutoSaveMsg("saved")
                            } else {
                                updateAutoSaveMsg("error")
                            }
                        }
                    }
                }.start()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        inputLayoutEt.addTextChangedListener(textWatcher)
    }

    private fun getSavedNoteFromDB() {
        //Get saved note from database
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Get note from database
                val note = dataSnapshot.getValue(String::class.java)
                //Set note to input field
                inputLayoutEt.setText(note)

                loadingDialog?.dismiss()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //Handle error
                showToast(this@MainActivity, databaseError.message)
                loadingDialog?.dismiss()
            }
        }
        notesRef.addListenerForSingleValueEvent(valueEventListener)
    }

    private fun updateAutoSaveMsg(value: String) {
        when (value) {
            "saved" -> {
                autoSaveText.text = getString(R.string.auto_save_msg_on)
            }
            "not saved" -> {
                autoSaveText.text = getString(R.string.auto_save_msg_off)
            }
            else -> {
                autoSaveText.text = getString(R.string.auto_save_msg_error)
            }
        }
    }
}