package com.example.assignmenttest.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignmenttest.R
import com.example.assignmenttest.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentMainBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            loginBtn.setOnClickListener { moveToLoginFragment() }
            signupBtn.setOnClickListener { moveToSignUpFragment() }
        }
    }


    private fun moveToLoginFragment() {
        val loginFragment = LoginFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, loginFragment)  // fragment_container는 MainActivity의 FrameLayout ID
            .addToBackStack(null)
            .commit()
    }

    private fun moveToSignUpFragment() {
        val signUpFragment = SignUpFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, signUpFragment)  // fragment_container는 MainActivity의 FrameLayout ID
            .addToBackStack(null)
            .commit()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}