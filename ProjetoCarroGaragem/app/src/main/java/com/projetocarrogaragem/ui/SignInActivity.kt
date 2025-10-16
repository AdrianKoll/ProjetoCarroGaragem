package com.projetocarrogaragem.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.projetocarrogaragem.R

class SignInActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etSenha: EditText
    private lateinit var btnEntrar: Button
    private lateinit var layoutBack: LinearLayout
    private lateinit var layoutSignUp: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        etEmail = findViewById(R.id.editTextEmail)
        etSenha = findViewById(R.id.editTextPassword)
        btnEntrar = findViewById(R.id.buttonSignIn)
        layoutBack = findViewById(R.id.layoutBack)
        layoutSignUp = findViewById(R.id.layoutSignUp)

        btnEntrar.setOnClickListener { performLogin() }
        layoutBack.setOnClickListener { finish() }
        layoutSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun performLogin() {
        val email = etEmail.text.toString().trim()
        val senha = etSenha.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = getString(R.string.invalid_email)
            etEmail.requestFocus()
            return
        }

        if (senha.length < 6) {
            etSenha.error = getString(R.string.password_min_length)
            etSenha.requestFocus()
            return
        }

        Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }
}