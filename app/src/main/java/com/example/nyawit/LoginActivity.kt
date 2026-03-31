package com.example.nyawit

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.nyawit.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.etEmailLogin.doOnTextChanged { _, _, _, _ ->
            binding.tlEmailLogin.error = null
        }

        binding.etPassLogin.doOnTextChanged { _, _, _, _ ->
            binding.tlPassLogin.error = null
        }

        binding.btnLogin.setOnClickListener {
            validateLogin()
        }

        binding.tvToRegister.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateLogin() {
        val email = binding.etEmailLogin.text.toString().trim()
        val pass = binding.etPassLogin.text.toString().trim()

        when {
            email.isEmpty() -> {
                binding.tlEmailLogin.error = "Email tidak boleh kosong"
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.tlEmailLogin.error = "Format email tidak valid"
            }
            pass.isEmpty() -> {
                binding.tlPassLogin.error = "Password tidak boleh kosong"
            }
            pass.length < 6 -> {
                binding.tlPassLogin.error = "Password minimal 6 karakter"
            }
            else -> {
                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
