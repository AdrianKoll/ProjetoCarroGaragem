package com.projetocarrogaragem.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.projetocarrogaragem.R

class CheckEmailActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignIn: Button
    private lateinit var layoutBack: LinearLayout
    private lateinit var layoutSignUp: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Vincula os componentes do layout
        editTextEmail = findViewById(R.id.editTextEmail)       // corrigido
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonSignIn = findViewById(R.id.buttonSignIn)
        layoutBack = findViewById(R.id.layoutBack)
        layoutSignUp = findViewById(R.id.layoutSignUp)

        // Botão de login
        buttonSignIn.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val senha = editTextPassword.text.toString().trim()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.error = getString(R.string.invalid_email)
                return@setOnClickListener
            }

            if (senha.isEmpty()) {
                editTextPassword.error = getString(R.string.invalid_password)
                return@setOnClickListener
            }

            // Exemplo de navegação — substitua conforme sua lógica
            Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MenuActivity::class.java) // antes estava indo para RegisterActivity
            intent.putExtra("EXTRA_EMAIL", email)
            startActivity(intent)
            finish()
        }

        // Botão "Voltar"
        layoutBack.setOnClickListener {
            finish()
        }

        // Link "Sign Up"
        layoutSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}