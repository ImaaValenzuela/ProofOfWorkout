package imaavalenzuela.proofofworkout.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import imaavalenzuela.proofofworkout.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        btnLogin.setOnClickListener {
            val inputEmail = etEmail.text.toString().trim()
            val inputPassword = etPassword.text.toString().trim()
            val savedEmail = prefs.getString("email", null)
            val savedPassword = prefs.getString("password", null)

            if (inputEmail == savedEmail && inputPassword == savedPassword) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                prefs.edit().putBoolean("isLoggedIn", true).apply()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        if (prefs.getBoolean("isLoggedIn", false)) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}
