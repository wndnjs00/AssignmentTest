package com.example.assignmenttest.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignmenttest.R
import com.example.assignmenttest.databinding.FragmentMainBinding
import com.example.assignmenttest.replaceFragment
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
        replaceFragment(R.id.fragment_container, LoginFragment(), addToBackStack = true)
    }

    private fun moveToSignUpFragment() {
        replaceFragment(R.id.fragment_container, SignUpFragment(), addToBackStack = true)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}