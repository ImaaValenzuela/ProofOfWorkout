package imaavalenzuela.proofofworkout.view.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import imaavalenzuela.proofofworkout.R
import imaavalenzuela.proofofworkout.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val TAG = "HomeActivity" // Tag for Logcat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Starting HomeActivity.")
        enableEdgeToEdge()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: View has been set.")

        try {
            Log.d(TAG, "onCreate: Attempting to find NavHostFragment.")
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.navHostFragment) as NavHostFragment
            Log.d(TAG, "onCreate: NavHostFragment found successfully.")

            val navController = navHostFragment.navController
            Log.d(TAG, "onCreate: NavController obtained successfully.")

            NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
            Log.d(TAG, "onCreate: NavigationUI setup with NavController is complete.")
        } catch (e: Exception) {
            // If the app crashes, this will print the detailed error to Logcat
            Log.e(TAG, "CRASH: An error occurred during navigation setup in HomeActivity.", e)
        }
    }
}
