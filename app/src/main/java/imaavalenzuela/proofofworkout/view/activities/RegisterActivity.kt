package imaavalenzuela.proofofworkout.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import imaavalenzuela.proofofworkout.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        val etEmail = findViewById<EditText>(R.id.etEmail);
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Complete all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar datos localmente
            prefs.edit()
                .putString("email", email)
                .putString("password", password)
                .apply()

            Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


    }
}
