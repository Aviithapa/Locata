package com.example.locata.ui.main.view.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.locata.R
import com.example.locata.data.db.entities.User
import com.example.locata.databinding.ActivityRegisterBinding
import com.example.locata.ui.main.viewModel.authentication.AuthViewModel
import com.example.locata.ui.main.viewModel.authentication.AuthViewModelFactory
import com.example.locata.utils.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class RegisterActivity : AppCompatActivity() , KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)


        binding.cirRegisterButton.setOnClickListener {
                Register()
        }

        binding.ImageViewBack.setOnClickListener {
             LoginActivity()
        }
//
        binding.txtAlreadyHaveAccount.setOnClickListener {
            Login()
        }


    }

    private fun Login(){
            startActivity(Intent(this, RegisterActivity::class.java))
    }
    private fun Register() {
        val name = binding.editTextName.text.toString().trim()
        val username = binding.editTextUserName.text.toString().trim()
        val phone = binding.editTextMobile.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        val user:User=User(name=name,username = username,phone_number = phone,password = password)
        //@todo add input validations

        lifecycleScope.launch {
            try {
                if (name.isNullOrEmpty() || username.isNullOrEmpty() || phone.isNullOrEmpty() || password.isNullOrEmpty()){
                    if (name.isNullOrEmpty()){
                        binding.editTextName.error="Enter name field"
                        binding.editTextName.requestFocus()
                    }else if (username.isNullOrEmpty()){
                        binding.editTextUserName.error="Enter username field"
                        binding.editTextUserName.requestFocus()
                    }else if (phone.isNullOrEmpty()){
                        binding.editTextMobile.error="Enter contact number field"
                        binding.editTextMobile.requestFocus()
                    }else if (password.isNullOrEmpty()){
                        binding.editTextPassword.error="Enter password field"
                        binding.editTextPassword.requestFocus()
                    }

                }else{
                    val authResponse = viewModel.userSignup(user)
                    if (authResponse.data != null) {
                        viewModel.saveLoggedInUser(authResponse.data)
                    } else {
                        binding.root.snackbar(authResponse.message!!)
                    }
                }

            } catch (e: ApiException) {
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }
        }
    }




}