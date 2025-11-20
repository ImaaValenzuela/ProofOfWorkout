package imaavalenzuela.proofofworkout.view.fragments.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import imaavalenzuela.proofofworkout.R
import imaavalenzuela.proofofworkout.databinding.FragmentLoginBinding
import imaavalenzuela.proofofworkout.view.activities.HomeActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    private val googleLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->

            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                val googleIdToken = credential.googleIdToken

                if (googleIdToken != null) {
                    val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)

                    auth.signInWithCredential(firebaseCredential)
                        .addOnSuccessListener {
                            goToHome()
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }

                } else {
                    Toast.makeText(requireContext(), "No se recibió ID Token", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()

        // Google One Tap client
        oneTapClient = Identity.getSignInClient(requireContext())

        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(false)
            .build()

        binding.btnGoogle.setOnClickListener {
            startGoogleSignIn()
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) goToHome()
    }

    private fun goToHome() {
        startActivity(Intent(requireContext(), HomeActivity::class.java))
        requireActivity().finish()
    }

    private fun startGoogleSignIn() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                val intentSenderRequest =
                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                googleLauncher.launch(intentSenderRequest)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "No se pudo iniciar Google Sign-In", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
