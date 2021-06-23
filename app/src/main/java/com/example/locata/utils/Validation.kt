package com.example.locata.utils

import android.widget.EditText
import androidx.fragment.app.Fragment



fun validateEmail(email:String,etEmail:EditText):Boolean{
    var EmailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    var flag=true
    if(email.matches(EmailPattern.toRegex())){
        flag=true
    }else {
        etEmail.error = "Input a Valid Email"
        etEmail.requestFocus()
        var flag=false
    }
    return flag
}

fun validateLength(password: String,etPassword: EditText):Boolean{
    var flag=true
    if (password.length<8){
        etPassword.error ="Password length must be greater than 8"
        etPassword.requestFocus()
        flag=false
    }
    return flag
}



fun isEmpty(name:String, email: String, password: String, contact: String, etName: EditText, etEmail: EditText, etPassword: EditText, etContact: EditText):Boolean{
    var flag=true
    if (name.isEmpty()){
        etName.error ="Enter Your Name"
        etName.requestFocus()
        flag=false
    }else if (email.isEmpty()){
        etEmail.error ="Enter Your Email"
        etEmail.requestFocus()
        flag=false
    }else if(password.isEmpty()){
        etPassword.error ="Enter Your Password"
        etPassword.requestFocus()
        flag=false
    }else if (contact.isEmpty()){
        etContact.error ="Enter Your Contact Number"
        etContact.requestFocus()
        flag=false
    }
    return flag
}

fun validateInputs(etName:EditText,etEmail: EditText,etPassword:EditText,etContact:EditText):Boolean{
    var flag=false
    val name=etName.text.toString()
    val email=etEmail.text.toString()
    val password=etPassword.text.toString()
    val contact=etContact.text.toString()
    if(isEmpty(name,email,password,contact,etName,etEmail,etPassword,etContact)){
        if(validateEmail(email,etEmail)){
            if (validateLength(password,etPassword)){
                    flag=true
            }
        }
    }
    return flag
}

fun checkLoginInputs(etEmail: EditText,etPassword: EditText,email: String,password: String):Boolean{
    var flag=true
    if(email.isEmpty()){
        etEmail.error="Please Enter your email"
        etEmail.requestFocus()
        flag=false
    }else if(validateEmail(email, etEmail)){
        etEmail.error="Please enter valid email"
        etEmail.requestFocus()
        flag= false
    }else if(password.isEmpty()){
        etPassword.error="Please Enter your Password"
        etPassword.requestFocus()
        flag=false
    }
    return flag
}

