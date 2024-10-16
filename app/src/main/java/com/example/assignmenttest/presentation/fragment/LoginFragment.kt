package com.example.assignmenttest.presentation.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.assignmenttest.AuthUiState
import com.example.assignmenttest.R
import com.example.assignmenttest.Validate
import com.example.assignmenttest.databinding.FragmentLoginBinding
import com.example.assignmenttest.presentation.viewModel.AuthViewModel
import com.example.assignmenttest.replaceFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentLoginBinding? = null

    private val viewModel: AuthViewModel by viewModels()
    private val sharedViewModel: AuthViewModel by activityViewModels()
    private val validate = Validate()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserve()

        with(binding){
            loginBtn.setOnClickListener { loginButtonClickListener() }
            signupTv.setOnClickListener {
                replaceFragment(R.id.fragment_container, SignUpFragment())
            }
            backButtonLoginIv.setOnClickListener { parentFragmentManager.popBackStack() }

            loginEmailEt.addTextChangedListener(textWatcher)
            loginPasswordEt.addTextChangedListener(textWatcher)
        }
    }


    private fun loginButtonClickListener(){
        with(binding){
            viewModel.login(
                email = loginEmailEt.text.toString(),
                password = loginPasswordEt.text.toString()
            )
        }
    }

    private fun setObserve(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect{ state ->
                when(state){
                    is AuthUiState.Success ->{
                        val email = binding.loginEmailEt.text.toString()
                        sharedViewModel.sharedEmail(email)

                        Snackbar.make(binding.root, getString(R.string.login_success_message), Snackbar.LENGTH_SHORT).show()
                        // CompleteFragment로 이동
                        replaceFragment(R.id.fragment_container,CompleteFragment())
                    }
                    is AuthUiState.Error ->{
                        Snackbar.make(binding.root, getString(R.string.login_error_message), Snackbar.LENGTH_SHORT).show()
                    }
                    is AuthUiState.Loading -> {
                        Snackbar.make(binding.root, getString(R.string.login_loading_message), Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }


    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            loginCheckLogic()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    // 유효성검사체크 로직 / 버튼 활성화
    private fun loginCheckLogic(){
        with(binding){
            val emailFlag = validate.isEmailValid(loginEmailEt.text.toString())
            val passFlag = validate.isPasswordValid(loginPasswordEt.text.toString())

            loginErrorTv.text = when{
                !emailFlag -> getString(R.string.email_flag_message)
                !passFlag -> getString(R.string.password_flag_message)
                else -> null
            }

            val allFieldsValid = emailFlag && passFlag
            if (allFieldsValid){
                loginBtn.isEnabled = true
                loginBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.main_yellow))
            }else{
                loginBtn.isEnabled = false
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}