package imaavalenzuela.proofofworkout.view.fragments.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import imaavalenzuela.proofofworkout.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import imaavalenzuela.proofofworkout.databinding.FragmentWorkoutSessionBinding
import imaavalenzuela.proofofworkout.model.Exercise
import imaavalenzuela.proofofworkout.view.adapters.ExerciseAdapter
import imaavalenzuela.proofofworkout.viewmodel.WorkoutViewModel

class WorkoutSessionFragment : Fragment() {

    private var _binding: FragmentWorkoutSessionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WorkoutViewModel
    private lateinit var exerciseAdapter: ExerciseAdapter

    private var isReadOnly = false
    private var workoutName: String? = null
    private var sessionId: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutSessionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[WorkoutViewModel::class.java]

        isReadOnly = arguments?.getBoolean("isReadOnly", false) ?: false
        sessionId = arguments?.getLong("sessionId", -1)
        workoutName = arguments?.getString("workoutName") ?: "Rutina sin nombre"

        if (sessionId != null && sessionId != -1L) {
            viewModel.selectWorkout(sessionId!!)
        }

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        exerciseAdapter = ExerciseAdapter(
            mutableListOf(),
            onEdit = { editExercise(it) },
            onDelete = { viewModel.deleteExercise(it) }
        )

        binding.rvExercises.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = exerciseAdapter
        }

        if (isReadOnly) {
            binding.btnAddExercise.isEnabled = false
            binding.btnSaveSession.visibility = View.GONE
            binding.etExerciseName.isEnabled = false
            binding.etReps.isEnabled = false
            binding.etSets.isEnabled = false
            binding.etWeight.isEnabled = false
        }

        binding.btnAddExercise.setOnClickListener { addExercise() }
        binding.btnSaveSession.setOnClickListener { saveWorkout() }
        binding.btnBack.setOnClickListener { showConfirmationDialog() }
    }

    private fun observeViewModel() {
        viewModel.currentExercises.observe(viewLifecycleOwner) { exercises ->
            exerciseAdapter.apply {
                this@WorkoutSessionFragment.exerciseAdapter =
                    ExerciseAdapter(exercises, onEdit = { editExercise(it) }, onDelete = { viewModel.deleteExercise(it) })
                binding.rvExercises.adapter = this
            }
        }
    }

    private fun addExercise() {
        val name = binding.etExerciseName.text.toString()
        val reps = binding.etReps.text.toString().toIntOrNull() ?: 0
        val sets = binding.etSets.text.toString().toIntOrNull() ?: 0
        val weight = binding.etWeight.text.toString().toFloatOrNull() ?: 0f

        if (name.isNotBlank() && reps > 0) {
            viewModel.addExercise(Exercise(name, reps, sets, weight))
            binding.etExerciseName.text?.clear()
            binding.etReps.text?.clear()
            binding.etSets.text?.clear()
            binding.etWeight.text?.clear()
        }
    }

    private fun saveWorkout() {
        viewModel.saveWorkout(workoutName ?: "Rutina sin nombre", sessionId)
        findNavController().navigate(R.id.workoutFragment)
    }

    private fun editExercise(exercise: Exercise) {
        // Igual que antes: abre diálogo, actualiza viewModel.updateExercise()
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Descartar entrenamiento")
            .setMessage("¿Seguro que querés salir? Se perderá el progreso no guardado.")
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Salir") { _, _ -> findNavController().popBackStack() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


