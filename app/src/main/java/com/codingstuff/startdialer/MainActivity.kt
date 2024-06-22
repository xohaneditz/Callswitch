package com.codingstuff.startdialer

import android.Manifest.permission.CALL_PHONE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var inputNumberEt: EditText
    private lateinit var startDialerBtn: Button
    private lateinit var dialBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initVars()
        registerClickListener()
    }

    private fun registerClickListener() {
        startDialerBtn.setOnClickListener {
            onButtonClick(false)
        }
        dialBtn.setOnClickListener {
            onButtonClick(true)
        }
    }

    private fun onButtonClick(isDial: Boolean) {
        val inputNumber = inputNumberEt.text.toString()
        val numberUri = Uri.parse("tel:$inputNumber")
        if (isDial) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(Intent.ACTION_CALL, numberUri)
                startIntent(intent)
            } else {
                requestPermissions(arrayOf(CALL_PHONE), 1)
            }
        } else {
            val intent = Intent(Intent.ACTION_DIAL, numberUri)
            startIntent(intent)

        }
    }

    private fun startIntent(intent: Intent) {
        try {
            startActivity(intent)
        } catch (e: SecurityException) {
            Log.e(TAG, "startIntent: ${e.message}")
        }
    }

    private fun initVars() {
        inputNumberEt = findViewById(R.id.inputNumberEt)
        startDialerBtn = findViewById(R.id.openDialerBtn)
        dialBtn = findViewById(R.id.dialBtn)
    }
}