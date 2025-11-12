package imaavalenzuela.proofofworkout.view.fragments.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import imaavalenzuela.proofofworkout.databinding.FragmentWorkoutNameBinding
import imaavalenzuela.proofofworkout.R

class WorkoutNameFragment : Fragment() {

    private var _binding: FragmentWorkoutNameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.setOnClickListener {
            val name = binding.etWorkoutName.text.toString().trim()
            if (name.isNotEmpty()) {
                val bundle = Bundle().apply {
                    putString("workoutName", name)
                }
                findNavController().navigate(R.id.workoutSessionFragment, bundle)
            } else {
                binding.etWorkoutName.error = "Ingresá un nombre para la rutina"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
