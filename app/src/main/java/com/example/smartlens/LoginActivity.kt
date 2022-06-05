package com.example.smartlens

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_screen.*

class LoginActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait!!")
        progressDialog.setMessage("Logging In....")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        //checkUser()

        btnLogin.setOnClickListener {
            validateData()
        }

        btnSignUp.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
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
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Welcome!",Toast.LENGTH_SHORT).show()
                val intent = Intent(this,SplashActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Login Failed due to ${it.message}",Toast.LENGTH_LONG).show()
            }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser != null) {
            val intent = Intent(this,SplashActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}