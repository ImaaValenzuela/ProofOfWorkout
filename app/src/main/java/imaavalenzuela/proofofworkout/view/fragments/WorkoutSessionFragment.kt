package imaavalenzuela.proofofworkout.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import imaavalenzuela.proofofworkout.R
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import imaavalenzuela.proofofworkout.databinding.FragmentWorkoutSessionBinding
import imaavalenzuela.proofofworkout.model.Exercise
import imaavalenzuela.proofofworkout.model.WorkoutSession
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WorkoutSessionFragment : Fragment() {

    private var _binding: FragmentWorkoutSessionBinding? = null
    private val binding get() = _binding!!

    private val gson = Gson()
    private val workouts = mutableListOf<WorkoutSession>()
    private var currentExercises = mutableListOf<Exercise>()

    private val prefs by lazy {
        requireContext().getSharedPreferences("workouts_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutSessionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadWorkouts()

        binding.btnAddExercise.setOnClickListener { addExercise() }
        binding.btnSaveSession.setOnClickListener { saveWorkoutSession() }
    }

    private fun loadWorkouts() {
        val json = prefs.getString("workout_list", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<WorkoutSession>>() {}.type
            val saved = gson.fromJson<MutableList<WorkoutSession>>(json, type)
            workouts.addAll(saved)
        }
    }

    private fun saveWorkoutSession() {
        val sessionId = System.currentTimeMillis()
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val newSession = WorkoutSession(sessionId, date, currentExercises)
        workouts.add(newSession)
        saveWorkouts()
        currentExercises.clear()

        findNavController().navigate(R.id.workoutFragment)
    }

    private fun saveWorkouts() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
