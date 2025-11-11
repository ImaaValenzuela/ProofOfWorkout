package imaavalenzuela.proofofworkout.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import imaavalenzuela.proofofworkout.R
import imaavalenzuela.proofofworkout.databinding.FragmentWorkoutHistoryBinding
import imaavalenzuela.proofofworkout.model.WorkoutSession
import imaavalenzuela.proofofworkout.view.adapters.WorkoutHistoryAdapter

class WorkoutHistoryFragment : Fragment() {

    private var _binding: FragmentWorkoutHistoryBinding? = null
    private val binding get() = _binding!!

    private val gson = Gson()
    private val prefs by lazy {
        requireContext().getSharedPreferences("workouts_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val workouts = loadWorkouts().toMutableList()
        val adapter = WorkoutHistoryAdapter(workouts){it ->
            showWorkoutOptions(it, workouts)
        }

        binding.rvWorkoutHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWorkoutHistory.adapter = adapter
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun showWorkoutOptions(
        workout: WorkoutSession,
        workouts: MutableList<WorkoutSession>
    ){
        val options = arrayOf("Ver", "Editar", "Eliminar")

        com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setTitle(workout.name)
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> viewWorkout(workout)
                    1 -> editWorkout(workout)
                    2 -> deleteWorkout(workout, workouts)
                }
            }
            .show()
    }


    private fun viewWorkout(workout: WorkoutSession) {
        val bundle = Bundle().apply {
            putLong("sessionId", workout.id)
            putBoolean("isReadOnly", true)
        }
        findNavController().navigate(R.id.workoutSessionFragment, bundle)
    }


    private fun editWorkout(workout: WorkoutSession) {
        val bundle = Bundle().apply {
            putLong("sessionId", workout.id)
            putBoolean("isReadOnly", false)
        }
        findNavController().navigate(R.id.workoutSessionFragment, bundle)
    }

    private fun deleteWorkout(
        workout: WorkoutSession,
        workouts: MutableList<WorkoutSession>
    ) {
        com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setTitle("Eliminar rutina")
            .setMessage("¿Seguro que querés eliminar '${workout.name}'?")
            .setPositiveButton("Eliminar") { _, _ ->
                workouts.remove(workout)
                saveWorkouts(workouts)
                binding.rvWorkoutHistory.adapter?.notifyDataSetChanged()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun saveWorkouts(workouts: List<WorkoutSession>) {
        val json = gson.toJson(workouts)
        prefs.edit().putString("workout_list", json).apply()
    }

    private fun loadWorkouts(): List<WorkoutSession> {
        val json = prefs.getString("workout_list", null)
        return if (json != null) {
            val type = object : TypeToken<List<WorkoutSession>>() {}.type
            gson.fromJson(json, type)
        } else emptyList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
