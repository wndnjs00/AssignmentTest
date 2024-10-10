package com.example.assignmenttest.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.assignmenttest.AuthUiState
import com.example.assignmenttest.R
import com.example.assignmenttest.databinding.FragmentCompleteBinding
import com.example.assignmenttest.presentation.viewModel.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompleteFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentCompleteBinding? = null

    private val viewModel: AuthViewModel by viewModels()
    private val sharedViewModel: AuthViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompleteBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nickNameUpdate()
        setObserve()

        with(binding){
            logoutBtn.setOnClickListener { viewModel.logout() }
            deleteAccountBtn.setOnClickListener { viewModel.deleteAccount() }
        }
    }


    private fun nickNameUpdate(){
        viewLifecycleOwner.lifecycleScope.launch{
            sharedViewModel.nickName.collect{ nickName ->
                if(!nickName.isNullOrEmpty()){
                    binding.completeExplainTv.text = getString(R.string.welcome_message, nickName)
                }else{
                    // nickname이 null이거나 비어있을 경우 email로 대체
                    sharedViewModel.email.collect{ email ->
                        binding.completeExplainTv.text = getString(R.string.welcome_message, email)
                    }
                }
            }
        }
    }
    
    
    private fun setObserve(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect{ state ->
                when(state){
                    is AuthUiState.Logout -> {
                        Snackbar.make(binding.root, getString(R.string.logout_message), Snackbar.LENGTH_SHORT).show()
                        // LoginFragment로 이동
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, LoginFragment())
                            .commit()
                    }
                    is AuthUiState.AccountDelete -> {
                        Snackbar.make(binding.root, getString(R.string.delete_account_message), Snackbar.LENGTH_SHORT).show()
                        // MainFragment로 이동
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, MainFragment())
                            .commit()
                    }
                    is AuthUiState.Error -> {
                        Snackbar.make(binding.root, getString(R.string.error_message), Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}