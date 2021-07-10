package com.example.locata.ui.main.view.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import com.example.locata.R
import com.example.locata.databinding.ActivityLoginBinding
import com.example.locata.ui.main.view.main.HomeActivity
import com.example.locata.ui.main.viewModel.authentication.AuthViewModel
import com.example.locata.ui.main.viewModel.authentication.AuthViewModelFactory
import com.example.locata.utils.ApiException
import com.example.locata.utils.NoInternetException
import com.example.locata.utils.snackbar
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity() , KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var cirLoginButton: CircularProgressButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)


        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        binding.cirLoginButton.setOnClickListener {
            loginUser()
        }

        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        lifecycleScope.launch {
            try {
                val authResponse = viewModel.userLogin(email, password)
                if (authResponse.data != null) {
                    viewModel.saveLoggedInUser(authResponse.data)
                } else {
                    binding.rootLayout.snackbar(authResponse.message!!)
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }
        }
    }

}