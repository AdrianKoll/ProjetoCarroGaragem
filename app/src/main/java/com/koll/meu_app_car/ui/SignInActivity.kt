package com.koll.meu_app_car.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.koll.meu_app_car.R

class SignInActivity : AppCompatActivity() {

    private lateinit var editTextUserName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var checkBoxRemember: CheckBox
    private lateinit var buttonSignIn: Button
    private lateinit var layoutBack: LinearLayout
    private lateinit var layoutSignUp: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        editTextUserName = findViewById(R.id.editTextUserName)
        editTextPassword = findViewById(R.id.editTextPassword)
        checkBoxRemember = findViewById(R.id.checkBoxRemember)
        buttonSignIn = findViewById(R.id.buttonSignIn)
        layoutBack = findViewById(R.id.layoutBack)
        layoutSignUp = findViewById(R.id.layoutSignUp)
    }

    private fun setupClickListeners() {
        buttonSignIn.setOnClickListener {
            performSignIn()
        }

        layoutBack.setOnClickListener {
            finish()
        }

        layoutSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performSignIn() {
        val userName = editTextUserName.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

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

        // Validação simples (credenciais de teste)
        // Aceita múltiplas combinações para facilitar o teste
        val validCredentials = listOf(
            Pair("admin", "123456"),
            Pair("admin", "admin"),
            Pair("teste", "123456"),
            Pair("teste", "teste"),
            Pair("user", "123456"),
            Pair("corex", "123456")
        )

        val isValidLogin = validCredentials.any { (user, pass) ->
            userName.equals(user, ignoreCase = true) && password == pass
        }

        if (isValidLogin) {
            // Login bem-sucedido
            Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
            
            // Ir para o menu principal
            val intent = Intent(this, MenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_LONG).show()
            
            // Mostrar dica das credenciais válidas
            Toast.makeText(this, "Dica: Use 'admin' e '123456'", Toast.LENGTH_LONG).show()
        }
    }
}

