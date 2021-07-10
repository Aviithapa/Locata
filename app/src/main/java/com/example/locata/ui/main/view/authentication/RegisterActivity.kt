package com.example.locata.ui.main.view.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import com.example.locata.R
import com.example.locata.databinding.ActivityLoginBinding
import com.example.locata.ui.main.viewModel.authentication.AuthViewModel
import com.example.locata.ui.main.viewModel.authentication.AuthViewModelFactory
import com.example.locata.utils.checkInternetConnection
import com.example.locata.utils.validateInputs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class RegisterActivity : AppCompatActivity() , KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)



//        binding.cirRegisterButton.setOnClickListener {
//            if(validateInputs(name,email,password,contact)){
//                RegisterUser()
//            }
//        }

//        binding.ImageViewBack.setOnClickListener {
//             LoginActivity()
//        }
//
//        binding.txtAlreadyHaveAccount.setOnClickListener {
//            LoginActivity()
//        }

    }




}