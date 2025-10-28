package imaavalenzuela.proofofworkout.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import imaavalenzuela.proofofworkout.R

class HomeActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        splashScreen.setKeepOnScreenCondition { false }

        checkSession()
    }

    private fun checkSession() {
        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)
        val isFirstTime = prefs.getBoolean("isFirstTime", true)

        if (!isLoggedIn) {
            // Si no está logueado, va al login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            // Si está logueado, mostramos el modal solo la primera vez
            if (isFirstTime) {
                showWelcomeModal()
                prefs.edit().putBoolean("isFirstTime", false).apply()
            }
        }
    }

    private fun showWelcomeModal() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Welcome!")
            .setMessage("This is your Proof Of Workout home screen.\n\nGet ready to track your progress!")
            .setPositiveButton("Got it") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }
}
