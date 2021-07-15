package com.example.locata.ui.main.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.locata.R
import com.example.locata.databinding.ActivityDriverHomeBinding
import com.example.locata.databinding.ActivitySplashBinding
import com.example.locata.ui.main.viewModel.authentication.AuthViewModel
import com.example.locata.ui.main.viewModel.authentication.AuthViewModelFactory
import com.google.android.gms.common.util.DataUtils
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class DriverHomeActivity : AppCompatActivity() , KodeinAware {
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var binding: ActivityDriverHomeBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_driver_home)
        viewModel=ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        binding.startRide.setOnClickListener {
            val buttontext=binding.startRide.text.toString().trim()
            if(buttontext=="END RIDE"){
                binding.startRide.setText("START RIDE")
            }else {
                binding.startRide.setText("END RIDE")
            }
        }
    }
}