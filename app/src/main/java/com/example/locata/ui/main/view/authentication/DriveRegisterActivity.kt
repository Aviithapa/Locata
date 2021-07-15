package com.example.locata.ui.main.view.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.locata.R
import com.example.locata.data.db.entities.Location
import com.example.locata.data.db.entities.Route
import com.example.locata.data.db.entities.User
import com.example.locata.data.db.entities.VehcileRegister
import com.example.locata.databinding.ActivityDriveRegisterBinding
import com.example.locata.databinding.ActivityLoginBinding
import com.example.locata.ui.main.viewModel.authentication.AuthViewModel
import com.example.locata.ui.main.viewModel.authentication.AuthViewModelFactory
import com.example.locata.utils.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class DriveRegisterActivity : AppCompatActivity() , KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    var routeName:String?=null

    private lateinit var binding: ActivityDriveRegisterBinding
    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drive_register)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_drive_register)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)


        binding.cirRegisterButton.setOnClickListener {
            Register()
        }
        val location: MutableList<String> = ArrayList()
        var i=0
            for (item in Coroutines.data!!){
                 location.add(i,item.name.toString())
                i++
            }

        val spinner = binding.editTextRouteName
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, location)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    routeName= location[position].toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }


    private fun Register() {
        val name = binding.editTextName.text.toString().trim()
        val username = binding.editTextUserName.text.toString().trim()
        val phone = binding.editTextMobile.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val vehicle_no= binding.editVehicleNumber.text.toString().trim()
        val routeName=routeName;
        val role="Driver"
        val user: User = User(name=name,username = username,phone_number = phone,password = password, role = role)

        //@todo add input validations
        lifecycleScope.launch {
            try {
                if (name.isNullOrEmpty() || username.isNullOrEmpty() || phone.isNullOrEmpty() || password.isNullOrEmpty()){
                    if (name.isNullOrEmpty()){
                        binding.editTextName.error="Name is required"
                        binding.editTextName.requestFocus()
                    }else if (username.isNullOrEmpty()){
                        binding.editTextUserName.error="Username is required"
                        binding.editTextUserName.requestFocus()
                    }else if (phone.isNullOrEmpty()){
                        binding.editTextMobile.error="Phone number is required"
                        binding.editTextMobile.requestFocus()
                    }else if (password.isNullOrEmpty()){
                        binding.editTextPassword.error="Password is required"
                        binding.editTextPassword.requestFocus()
                    }
                }else if(password.length<8){
                    binding.root.snackbar("Password must be greater than 8")
                }else{
                    val authResponse = viewModel.userSignup(user)
                    if (authResponse.data != null) {
                        val vehcileRegister : VehcileRegister = VehcileRegister(vehicle_no=vehicle_no,user_id =authResponse.data._id,route_Name = routeName )
                        val vehcileResponse = viewModel.vehicleRegister(vehcileRegister)
                        if (vehcileResponse.data != null) {
                            viewModel.saveLoggedInUser(authResponse.data)
                            binding.root.snackbar(vehcileResponse.message!!)
                            println(vehcileResponse.message)
                        }else{
                            binding.root.snackbar(authResponse.message!!)
                        }

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