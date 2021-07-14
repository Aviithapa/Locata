package com.example.locata.ui.main.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.locata.R
import com.example.locata.databinding.ActivitySplash2Binding
import com.example.locata.databinding.ActivitySplashBinding
import com.example.locata.ui.main.view.authentication.DriveRegisterActivity
import com.example.locata.ui.main.view.authentication.LoginActivity
import com.example.locata.ui.main.view.authentication.RegisterActivity
import com.example.locata.ui.main.view.main.HomeActivity
import com.example.locata.ui.main.viewModel.authentication.AuthViewModel
import com.example.locata.ui.main.viewModel.authentication.AuthViewModelFactory
import com.example.locata.utils.ApiException
import com.example.locata.utils.Coroutines
import com.example.locata.utils.NoInternetException
import com.example.locata.utils.snackbar
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SplashActivity2 : AppCompatActivity() , KodeinAware {
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivitySplash2Binding
    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash2)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        binding.cirDriveAccount.setOnClickListener {
            startActivity(Intent(this, DriveRegisterActivity::class.java))
        }

        binding.cirRideAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        
    }

}