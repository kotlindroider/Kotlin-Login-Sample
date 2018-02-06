package com.kotlindroider.devaj

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class SignupActivity : AppCompatActivity() {

    var _nameText: EditText? = null
    var _addressText: EditText? = null
    var _emailText: EditText? = null
    var _mobileText: EditText? = null
    var _passwordText: EditText? = null
    var _reEnterPasswordText: EditText? = null
    var _signupButton: Button? = null
    var _loginLink: TextView? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kotlindroider.devaj.R.layout.activity_signup)

        _nameText = findViewById(R.id.input_name) as EditText
        _addressText = findViewById(R.id.input_address) as EditText
        _emailText = findViewById(R.id.input_email) as EditText
        _mobileText = findViewById(R.id.input_mobile) as EditText
        _passwordText = findViewById(R.id.input_password) as EditText
        _reEnterPasswordText = findViewById(R.id.input_reEnterPassword) as EditText

        _signupButton = findViewById(R.id.btn_signup) as Button
        _loginLink = findViewById(R.id.link_login) as TextView

        _signupButton!!.setOnClickListener { signup() }

        _loginLink!!.setOnClickListener {
            // Finish the registration screen and return to the Login activity
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(com.kotlindroider.devaj.R.anim.push_left_in, com.kotlindroider.devaj.R.anim.push_left_out)
        }
    }

    fun signup() {
        Log.d(TAG, "Signup")

        if (!validate()) {
            onSignupFailed()
            return
        }

        _signupButton!!.isEnabled = false

        val progressDialog = ProgressDialog(this@SignupActivity,
                com.kotlindroider.devaj.R.style.AppTheme_Dark_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()

        val name = _nameText!!.text.toString()
        val address = _addressText!!.text.toString()
        val email = _emailText!!.text.toString()
        val mobile = _mobileText!!.text.toString()
        val password = _passwordText!!.text.toString()
        val reEnterPassword = _reEnterPasswordText!!.text.toString()

        // TODO: Implement your own signup logic here.

        android.os.Handler().postDelayed(
                {
                    // On complete call either onSignupSuccess or onSignupFailed
                    // depending on success
                    onSignupSuccess()
                    // onSignupFailed();
                    progressDialog.dismiss()
                }, 3000)
    }


    fun onSignupSuccess() {
        _signupButton!!.isEnabled = true
//        setResult(Activity.RESULT_OK, null)
//        finish()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun onSignupFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()

        _signupButton!!.isEnabled = true
    }

    fun validate(): Boolean {
        var valid = true

        val name = _nameText!!.text.toString()
        val address = _addressText!!.text.toString()
        val email = _emailText!!.text.toString()
        val mobile = _mobileText!!.text.toString()
        val password = _passwordText!!.text.toString()
        val reEnterPassword = _reEnterPasswordText!!.text.toString()

        if (name.isEmpty() || name.length < 3) {
            _nameText!!.error = "at least 3 characters"
            valid = false
        } else {
            _nameText!!.error = null
        }

        if (address.isEmpty()) {
            _addressText!!.error = "Enter Valid Address"
            valid = false
        } else {
            _addressText!!.error = null
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText!!.error = "enter a valid email address"
            valid = false
        } else {
            _emailText!!.error = null
        }

        if (mobile.isEmpty() || mobile.length != 10) {
            _mobileText!!.error = "Enter Valid Mobile Number"
            valid = false
        } else {
            _mobileText!!.error = null
        }

        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            _passwordText!!.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            _passwordText!!.error = null
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length < 4 || reEnterPassword.length > 10 || reEnterPassword != password) {
            _reEnterPasswordText!!.error = "Password Do not match"
            valid = false
        } else {
            _reEnterPasswordText!!.error = null
        }

        return valid
    }

    companion object {
        private val TAG = "SignupActivity"
    }
}