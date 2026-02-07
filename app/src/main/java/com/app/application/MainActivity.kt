package com.app.application

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize views of this activity
        initializeViews()
    }

    private fun initializeViews() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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
        if (item.itemId == R.id.action_crash) {
            throw RuntimeException("Test Crash") // Force a crash
//            return true
        }
        return super.onOptionsItemSelected(item)
    }
}