package imaavalenzuela.proofofworkout.view.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import imaavalenzuela.proofofworkout.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        screenSplash.setKeepOnScreenCondition { false }
        showWelcomeModal()
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
