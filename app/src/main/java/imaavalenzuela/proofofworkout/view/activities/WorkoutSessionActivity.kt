package imaavalenzuela.proofofworkout.view.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.intl.Locale
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import imaavalenzuela.proofofworkout.R
import imaavalenzuela.proofofworkout.databinding.ActivityWorkoutSessionBinding
import imaavalenzuela.proofofworkout.model.Exercise
import imaavalenzuela.proofofworkout.model.WorkoutSession
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

class WorkoutSessionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutSessionBinding
    private val gson = Gson()
    private val prefs by lazy { getSharedPreferences("workouts:prefs", Context.MODE_PRIVATE) }
    private val workouts = mutableListOf<WorkoutSession>()
    private var currentExercises = mutableListOf<Exercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWorkoutSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadWorkouts()

        binding.btnAddExercise.setOnClickListener { addExercise() }
        binding.btnSaveSession.setOnClickListener { saveWorkoutSession() }
    }

    private fun loadWorkouts() {
        val json = prefs.getString("workout_list", null)
        if(json != null){
            val type = object : TypeToken<MutableList<WorkoutSession>>(){}.type
            val saved = gson.fromJson<MutableList<WorkoutSession>>(json, type)
            workouts.addAll(saved)
        }
    }

    private fun saveWorkoutSession() {
        val sessionId = System.currentTimeMillis()
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault()).format(Date())
        val newSession = WorkoutSession(sessionId, date, currentExercises)
        workouts.add(newSession)
        saveWorkouts()
        currentExercises = mutableListOf()
    }

    private fun saveWorkouts(){
        val json = gson.toJson(workouts)
        prefs.edit().putString("workout_list", json).apply()
    }

    private fun addExercise() {
        val name = binding.etExerciseName.text.toString()
        val reps = binding.etReps.text.toString().toIntOrNull() ?: 0
        val sets = binding.etSets.text.toString().toIntOrNull() ?: 0
        val weight = binding.etWeight.text.toString().toFloatOrNull() ?: 0f

        if (name.isNotBlank() && reps > 0) {
            val exercise = Exercise(name, reps, sets, weight)
            currentExercises.add(exercise)
            binding.etExerciseName.text?.clear()
            binding.etReps.text?.clear()
            binding.etSets.text?.clear()
            binding.etWeight.text?.clear()
        }
    }
}
