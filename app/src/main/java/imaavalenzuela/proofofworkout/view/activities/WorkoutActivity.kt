package imaavalenzuela.proofofworkout.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import imaavalenzuela.proofofworkout.R
import imaavalenzuela.proofofworkout.databinding.ActivityHomeBinding
import imaavalenzuela.proofofworkout.databinding.ActivityWorkoutBinding

class WorkoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddExercise.setOnClickListener {
            startActivity(Intent(this, WorkoutSessionActivity::class.java))
        }

        binding.btnSaveSession.setOnClickListener {
            startActivity(Intent(this, WorkoutHistoryActivity::class.java))
        }
    }
}
