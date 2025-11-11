package imaavalenzuela.proofofworkout.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.semantics.dismiss
import androidx.fragment.app.Fragment
import imaavalenzuela.proofofworkout.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import imaavalenzuela.proofofworkout.databinding.FragmentWorkoutSessionBinding
import imaavalenzuela.proofofworkout.model.Exercise
import imaavalenzuela.proofofworkout.model.WorkoutSession
import imaavalenzuela.proofofworkout.view.adapters.ExerciseAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WorkoutSessionFragment : Fragment() {

    private var _binding: FragmentWorkoutSessionBinding? = null
    private val binding get() = _binding!!

    private val gson = Gson()
    private val workouts = mutableListOf<WorkoutSession>()
    private var currentExercises = mutableListOf<Exercise>()

    private var workoutName : String? = null

    private var isReadOnly = false
    private var sessionId: Long? = null

    private lateinit var exerciseAdapter: ExerciseAdapter

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

        sessionId = arguments?.getLong("sessionId", -1)
        isReadOnly = arguments?.getBoolean("isReadOnly", false) ?: false

        if (sessionId != null && sessionId != -1L) {
            loadSessionData(sessionId!!)
        }

        if (isReadOnly) {
            binding.btnAddExercise.isEnabled = false
            binding.btnSaveSession.visibility = View.GONE
            binding.etExerciseName.isEnabled = false
            binding.etReps.isEnabled = false
            binding.etSets.isEnabled = false
            binding.etWeight.isEnabled = false
        }

        workoutName = arguments?.getString("workoutName") ?: "Rutina sin nombre"

        loadWorkouts()

        exerciseAdapter = ExerciseAdapter(
            currentExercises,
            onEdit = { editExercise(it) },
            onDelete = { deleteExercise(it) }
        )

        binding.rvExercises.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = exerciseAdapter
        }


        binding.btnAddExercise.setOnClickListener { addExercise() }
        binding.btnSaveSession.setOnClickListener { saveWorkoutSession() }
        binding.btnBack.setOnClickListener { showConfirmationDialog() }
    }

    private fun loadSessionData(id: Long) {
        val json = prefs.getString("workout_list", null)
        val type = object : TypeToken<MutableList<WorkoutSession>>() {}.type
        val saved = gson.fromJson<MutableList<WorkoutSession>>(json, type)

        val session = saved.firstOrNull { it.id == id }
        session?.let {
            currentExercises = it.exercises.toMutableList()
            workoutName = it.name
        }
    }
    private fun loadWorkouts() {
        val json = prefs.getString("workout_list", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<WorkoutSession>>() {}.type
            val saved = gson.fromJson<MutableList<WorkoutSession>>(json, type)
            workouts.addAll(saved)
        }
    }

    private fun editExercise(exercise: Exercise) {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_edit_exercise, null)

        val nameField = dialogView.findViewById<android.widget.EditText>(R.id.etName)
        val repsField = dialogView.findViewById<android.widget.EditText>(R.id.etReps)
        val setsField = dialogView.findViewById<android.widget.EditText>(R.id.etSets)
        val weightField = dialogView.findViewById<android.widget.EditText>(R.id.etWeight)

        nameField.setText(exercise.name)
        repsField.setText(exercise.reps.toString())
        setsField.setText(exercise.sets.toString())
        weightField.setText(exercise.weight.toString())

        com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setTitle("Editar ejercicio")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                exercise.name = nameField.text.toString()
                exercise.reps = repsField.text.toString().toIntOrNull() ?: 0
                exercise.sets = setsField.text.toString().toIntOrNull() ?: 0
                exercise.weight = weightField.text.toString().toFloatOrNull() ?: 0f
                exerciseAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun deleteExercise(exercise: Exercise) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Eliminar ejercicio")
            .setMessage("¿Seguro que querés eliminar '${exercise.name}'?")
            .setPositiveButton("Eliminar") { _, _ ->
                currentExercises.remove(exercise)
                exerciseAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }


    private fun saveWorkoutSession() {
        val sessionId = System.currentTimeMillis()
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val newSession = WorkoutSession(sessionId, workoutName ?: "Rutina sin nombre", date, currentExercises)
        workouts.add(newSession)
        saveWorkouts()
        currentExercises.clear()

        findNavController().navigate(R.id.workoutFragment)
    }

    private fun showConfirmationDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Descartar entrenamiento")
            .setMessage("¿Estás seguro de que quieres salir? Se perderá el progreso no guardado.")
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Salir") { dialog, _ ->
                findNavController().popBackStack()
            }
            .show()
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
