package com.projetocarrogaragem.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.projetocarrogaragem.R

class SignUpActivity : AppCompatActivity() {

    private lateinit var editTextUserName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var checkBoxTerms: CheckBox
    private lateinit var buttonSignUp: Button
    private lateinit var layoutBack: LinearLayout
    private lateinit var layoutSignIn: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        editTextUserName = findViewById(R.id.editTextUserName)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPhone = findViewById(R.id.editTextPhone)
        checkBoxTerms = findViewById(R.id.checkBoxTerms)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        layoutBack = findViewById(R.id.layoutBack)
        layoutSignIn = findViewById(R.id.layoutSignIn)
    }

    private fun setupClickListeners() {
        buttonSignUp.setOnClickListener {
            performSignUp()
        }

        layoutBack.setOnClickListener {
            finish()
        }

        layoutSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performSignUp() {
        val userName = editTextUserName.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val phone = editTextPhone.text.toString().trim()

        // Validações
        if (userName.isEmpty()) {
            editTextUserName.error = getString(R.string.field_required)
            editTextUserName.requestFocus()
            return
        }

        if (password.isEmpty()) {
            editTextPassword.error = getString(R.string.field_required)
            editTextPassword.requestFocus()
            return
        }

        if (password.length < 6) {
            editTextPassword.error = getString(R.string.password_min_length)
            editTextPassword.requestFocus()
            return
        }

        if (email.isEmpty()) {
            editTextEmail.error = getString(R.string.field_required)
            editTextEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.error = getString(R.string.invalid_email)
            editTextEmail.requestFocus()
            return
        }

        if (phone.isEmpty()) {
            editTextPhone.error = getString(R.string.field_required)
            editTextPhone.requestFocus()
            return
        }

        if (!checkBoxTerms.isChecked) {
            Toast.makeText(this, getString(R.string.accept_terms), Toast.LENGTH_SHORT).show()
            return
        }

        // Cadastro bem-sucedido (em um app real, isso seria salvo no banco de dados)
        Toast.makeText(this, getString(R.string.signup_success), Toast.LENGTH_SHORT).show()

        // Ir para a tela de login
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}