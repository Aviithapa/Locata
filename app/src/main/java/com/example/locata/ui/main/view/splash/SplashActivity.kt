package com.example.locata.ui.main.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.locata.R
import com.example.locata.databinding.ActivitySplashBinding
import com.example.locata.ui.main.view.authentication.LoginActivity
import com.example.locata.ui.main.view.main.HomeActivity
import com.example.locata.ui.main.view.main.ui.home.HomeViewModel
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

class SplashActivity : AppCompatActivity()  , KodeinAware {
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var binding:ActivitySplashBinding
    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)


        Coroutines.main {
            startActivity(
                    Intent(this, SplashActivity2::class.java)
            )
            finish()
        }
        Intent(this, SplashActivity2::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)

//        viewModel.getLoggedInUser().observe(this, Observer { user ->
//            if (user != null) {
//                Intent(this, HomeActivity::class.java).also {
//                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    startActivity(it)
//                }
//            }else {
//                Intent(this, SplashActivity2::class.java).also {
//                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    startActivity(it)
//                }
//            }
//        })

        lifecycleScope.launch {
            try {
                val authResponse = viewModel.Location()
                if (authResponse.data != null) {
//                   Coroutines.data= authResponse.data
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