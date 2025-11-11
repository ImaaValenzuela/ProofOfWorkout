package imaavalenzuela.proofofworkout.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import imaavalenzuela.proofofworkout.data.AchievementsData
import imaavalenzuela.proofofworkout.databinding.FragmentAchievementsBinding
import imaavalenzuela.proofofworkout.view.adapters.AchievementsAdapter

class AchievementsFragment : Fragment() {

    private var _binding: FragmentAchievementsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAchievementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvAchievements.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAchievements.adapter = AchievementsAdapter(AchievementsData.achievements)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
