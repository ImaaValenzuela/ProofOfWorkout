package imaavalenzuela.proofofworkout.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import imaavalenzuela.proofofworkout.R
import imaavalenzuela.proofofworkout.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        splashScreen.setKeepOnScreenCondition { false }

        checkSession()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_home -> {
                    // Ya estás en Home, no hacer nada
                    true
                }

                R.id.nav_routines -> {
                    startActivity(Intent(this, WorkoutActivity::class.java))
                    true
                }

                R.id.nav_achievements -> {
                    startActivity(Intent(this, AchievementsActivity::class.java))
                    true
                }

                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }

                else -> false
            }
        }

    }

    private fun checkSession() {
        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)
        val isFirstTime = prefs.getBoolean("isFirstTime", true)

        if (!isLoggedIn) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            if (isFirstTime) {
                showWelcomeModal()
                prefs.edit().putBoolean("isFirstTime", false).apply()
            }
        }
    }

    private fun showWelcomeModal() {
        AlertDialog.Builder(this)
            .setTitle("Welcome!")
            .setMessage("This is your Proof Of Workout home screen.\n\nGet ready to track your progress!")
            .setPositiveButton("Got it") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
