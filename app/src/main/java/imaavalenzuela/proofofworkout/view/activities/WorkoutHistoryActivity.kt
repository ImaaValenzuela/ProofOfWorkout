package imaavalenzuela.proofofworkout.view.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import imaavalenzuela.proofofworkout.databinding.ActivityWorkoutHistoryBinding
import imaavalenzuela.proofofworkout.model.WorkoutSession
import imaavalenzuela.proofofworkout.view.adapters.WorkoutHistoryAdapter

class WorkoutHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutHistoryBinding
    private val gson = Gson()
    private val prefs by lazy { getSharedPreferences("workouts_prefs", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workouts = loadWorkouts()
        val adapter = WorkoutHistoryAdapter(workouts)

        binding.rvWorkoutHistory.layoutManager = LinearLayoutManager(this)
        binding.rvWorkoutHistory.adapter = adapter
    }

    private fun loadWorkouts(): List<WorkoutSession> {
        val json = prefs.getString("workout_list", null)
        return if (json != null) {
            val type = object : TypeToken<List<WorkoutSession>>() {}.type
            gson.fromJson(json, type)
        } else emptyList()
    }
}
