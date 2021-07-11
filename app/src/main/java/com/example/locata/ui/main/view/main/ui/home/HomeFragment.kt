package com.example.locata.ui.main.view.main.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.locata.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*
import com.example.locata.databinding.FragmentHomeBinding
import com.example.locata.utils.ApiException
import com.example.locata.utils.NoInternetException
import com.example.locata.utils.snackbar
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.launch

class HomeFragment : Fragment() , KodeinAware{


    override val kodein by kodein()

    private lateinit var viewModel: HomeViewModel
    private val factory: HomeViewModelFactory by instance()
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.cirgetthebusButton.setOnClickListener {
            getBus()
        }
        return binding.root
    }

    private fun getBus(){
        var currentLocation= binding.editTextCurrentLocation.text.toString().trim()
        var destinationLocation=binding.editDestinationLocation.text.toString().trim()
        lifecycleScope.launch {
            try {
                val authResponse = viewModel.getlocation(user)
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
