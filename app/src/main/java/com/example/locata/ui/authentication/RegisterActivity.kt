package com.example.locata.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import com.example.locata.R
import com.example.locata.utils.validateInputs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var contact: EditText
    private lateinit var password: EditText
    private lateinit var ImageViewBack:ImageView
    private lateinit var txtAlreadyHaveAccount:TextView
    private lateinit var cirRegisterButton: CircularProgressButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        name=findViewById(R.id.editTextName)
        email=findViewById(R.id.editTextEmail)
        contact=findViewById(R.id.editTextMobile)
        password=findViewById(R.id.editTextPassword)
        ImageViewBack=findViewById(R.id.ImageViewBack)
        txtAlreadyHaveAccount=findViewById(R.id.txtAlreadyHaveAccount)
        cirRegisterButton=findViewById(R.id.cirRegisterButton)

        cirRegisterButton.setOnClickListener{
            if(validateInputs(name,email,password,contact)){
                RegisterUser()
            }
        }
        ImageViewBack.setOnClickListener{
             LoginActivity()
        }
        txtAlreadyHaveAccount.setOnClickListener {
            LoginActivity()
        }

    }

    private fun LoginActivity(){
        CoroutineScope(Dispatchers.IO).launch {
            startActivity(
                    Intent(
                            this@RegisterActivity,
                            LoginActivity::class.java
                    )
            )
            finish()
        }
    }

    private fun RegisterUser() {
        val name=name.text.toString()
        val email=email.text.toString()
        val password=password.text.toString()
        val contact=contact.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
                startActivity(
                        Intent(
                                this@RegisterActivity,
                                LoginActivity::class.java
                        )
                )

            }

        }


}