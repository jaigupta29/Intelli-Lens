package com.example.smartlens

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.btnLogin
import kotlinx.android.synthetic.main.activity_sign_up.btnSignUp
import kotlinx.android.synthetic.main.activity_sign_up.emailAddress
import kotlinx.android.synthetic.main.activity_sign_up.emailPass
import kotlinx.android.synthetic.main.login_screen.*

class SignUp : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait!!")
        progressDialog.setMessage("Signing Up....")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()


        btnSignUp.setOnClickListener {
            validateData()
        }

        btnLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    private fun validateData() {
        email = emailAddress.text.toString().trim()
        password = emailPass.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailAddress.error = "Invalid Email Format"
        }
        else if (TextUtils.isEmpty(password)) {
            emailPass.error = "Please Enter password"
        }
        else {
            firebaseSignIn()
        }
    }

    private fun firebaseSignIn() {
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Welcome!",Toast.LENGTH_LONG).show()
                startActivity(Intent(this,SplashActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this,"SignUp Failed due to ${it.message}",Toast.LENGTH_LONG).show()
            }
    }
}