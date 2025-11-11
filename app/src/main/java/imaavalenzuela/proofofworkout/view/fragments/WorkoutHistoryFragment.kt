package imaavalenzuela.proofofworkout.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

        val workouts = loadWorkouts()
        val adapter = WorkoutHistoryAdapter(workouts)

        binding.rvWorkoutHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWorkoutHistory.adapter = adapter
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
