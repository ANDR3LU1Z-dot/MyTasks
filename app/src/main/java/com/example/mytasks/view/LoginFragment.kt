package com.example.mytasks.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mytasks.databinding.FragmentLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginFragment : Fragment() {

    companion object {
        const val TAG = "LoginFragment"
    }

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.signInButton.setOnClickListener {
            login()
        }

        binding.signUpText.setOnClickListener {
            signUp()
        }
    }

    private fun login() {
        auth = Firebase.auth
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(
                context,
                "Por favor, preencha os campos de e-mail e senha.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail: success")
                        val directions =
                            LoginFragmentDirections.actionLoginFragmentToTasksFragment()
                        findNavController().navigate(directions)
                    } else {
                        Log.d(TAG, "signInWithEmail: failure", task.exception)
                        Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
        }
    }

    private fun signUp() {
        val directions = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
        findNavController().navigate(directions)
    }

}