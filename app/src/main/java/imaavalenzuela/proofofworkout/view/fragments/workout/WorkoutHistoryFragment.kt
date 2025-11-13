package imaavalenzuela.proofofworkout.view.fragments.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import imaavalenzuela.proofofworkout.R
import imaavalenzuela.proofofworkout.databinding.FragmentWorkoutHistoryBinding
import imaavalenzuela.proofofworkout.model.WorkoutSession
import imaavalenzuela.proofofworkout.view.adapters.WorkoutHistoryAdapter
import imaavalenzuela.proofofworkout.view.repositories.WorkoutRepository
import imaavalenzuela.proofofworkout.viewmodel.WorkoutViewModel

class WorkoutHistoryFragment : Fragment() {

    private var _binding: FragmentWorkoutHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WorkoutViewModel
    private lateinit var adapter: WorkoutHistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkoutHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[WorkoutViewModel::class.java]

        adapter = WorkoutHistoryAdapter(mutableListOf()) { workout ->
            showWorkoutOptions(workout)
        }

        binding.rvWorkoutHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWorkoutHistory.adapter = adapter

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        viewModel.workouts.observe(viewLifecycleOwner) { workouts ->
            adapter.updateData(workouts)
        }
    }

    private fun showWorkoutOptions(workout: WorkoutSession) {
        val options = arrayOf("Ver", "Editar", "Eliminar")

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(workout.name)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> navigateTo(workout, true)
                    1 -> navigateTo(workout, false)
                    2 -> viewModel.deleteWorkout(workout.id)
                }
            }
            .show()
    }

    private fun navigateTo(workout: WorkoutSession, readOnly: Boolean) {
        val bundle = Bundle().apply {
            putLong("sessionId", workout.id)
            putBoolean("isReadOnly", readOnly)
        }
        findNavController().navigate(R.id.workoutSessionFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

