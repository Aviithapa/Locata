package com.example.locata.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import com.example.locata.R
import com.example.locata.ui.main.HomeActivity
import com.example.locata.utils.checkRunTimePermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var txtRegister:TextView
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var cirLoginButton: CircularProgressButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        txtRegister=findViewById(R.id.txtRegister)
        editEmail=findViewById(R.id.editTextEmail)
        editPassword=findViewById(R.id.editTextPassword)
        cirLoginButton=findViewById(R.id.cirLoginButton)

        cirLoginButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                startActivity(
                        Intent(
                                this@LoginActivity,
                                HomeActivity::class.java
                        )
                )
            }
        }
        txtRegister.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                startActivity(
                    Intent(
                        this@LoginActivity,
                        RegisterActivity::class.java
                    )
                )
                finish()
            }
        }
        checkRunTimePermission(this)
    }
}