package com.example.quizzey

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzey.databinding.ActivityWelcomeBinding
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null && currentUser.isEmailVerified) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnGuest.setOnClickListener {
            loginAsGuest()
        }
    }

    private fun loginAsGuest() {
        val guestEmail = "kikejo8607@lucvu.com"
        val guestPassword = "1234567"

        auth.signInWithEmailAndPassword(guestEmail, guestPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Guest login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
