package imaavalenzuela.proofofworkout.view.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import imaavalenzuela.proofofworkout.databinding.FragmentHomeBinding
import imaavalenzuela.proofofworkout.view.activities.AuthActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        checkSession()
    }

    private fun checkSession() {
        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)
        val isFirstTime = prefs.getBoolean("isFirstTime", true)

        if (!isLoggedIn) {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            requireActivity().finish()
        } else {
            if (isFirstTime) {
                showWelcomeModal()
                prefs.edit().putBoolean("isFirstTime", false).apply()
            }
        }
    }

    private fun showWelcomeModal() {
        AlertDialog.Builder(requireContext())
            .setTitle("Welcome!")
            .setMessage("This is your Proof Of Workout home screen.\n\nGet ready to track your progress!")
            .setPositiveButton("Got it") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
