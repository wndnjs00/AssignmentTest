package com.example.assignmenttest.presentation.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.assignmenttest.AuthUiState
import com.example.assignmenttest.R
import com.example.assignmenttest.Validate
import com.example.assignmenttest.databinding.FragmentSignupBinding
import com.example.assignmenttest.presentation.viewModel.AuthViewModel
import com.example.assignmenttest.replaceFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentSignupBinding? = null

    private val viewModel: AuthViewModel by viewModels()
    private val sharedViewModel: AuthViewModel by activityViewModels()
    private val validate = Validate()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserve()

        with(binding){
            signupBtn.setOnClickListener { signUpButtonClickListener() }
            backButtonSignupIv.setOnClickListener { parentFragmentManager.popBackStack() }

            checkboxAll.setOnClickListener { onCheckedTerms(checkboxAll) }
            checkbox1.setOnClickListener { onCheckedTerms(checkbox1) }
            checkbox2.setOnClickListener { onCheckedTerms(checkbox2) }

            signupEmailEt.addTextChangedListener(textWatcher)
            signupPasswordEt.addTextChangedListener(textWatcher)
            signupPasswordCheckEt.addTextChangedListener(textWatcher)
            signupNicknameEt.addTextChangedListener(textWatcher)
        }

    }


    private fun signUpButtonClickListener(){
        with(binding){
            if(checkboxAll.isChecked){
                viewModel.signUp(
                    email = signupEmailEt.text.toString(),
                    password = signupPasswordEt.text.toString(),
                    nickName = signupNicknameEt.text.toString()
                )

            }else{
                signupErrorTv.text = getString(R.string.signup_checkbox_not_checked_text)
            }
        }
    }

    private fun setObserve(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect{ state ->
                when(state){
                    is AuthUiState.Success -> {
                        val nickName = binding.signupNicknameEt.text.toString()
                        sharedViewModel.sharedNickName(nickName)

                        Snackbar.make(binding.root, getString(R.string.signup_success_message), Snackbar.LENGTH_SHORT).show()
                        // 로그인 프래그먼트로 이동
                        replaceFragment(R.id.fragment_container, LoginFragment())
                    }
                    is AuthUiState.Error ->{
                        Snackbar.make(binding.root, getString(R.string.signup_error_message), Snackbar.LENGTH_SHORT).show()
                    }
                    is AuthUiState.Loading -> {
                        Snackbar.make(binding.root, getString(R.string.signup_loading_message), Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    // 체크박스
    private fun onCheckedTerms(compoundButton: CompoundButton){
        with(binding){
            when(compoundButton.id){
                R.id.checkbox_all -> {
                    if(checkboxAll.isChecked){
                        checkbox1.isChecked = true
                        checkbox2.isChecked = true
                    }else{
                        checkbox1.isChecked = false
                        checkbox2.isChecked = false
                    }
                }else -> checkboxAll.isChecked = (checkbox1.isChecked && checkbox2.isChecked)
            }
        }
    }


    private val textWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            signUpCheckLogic()
        }
        override fun afterTextChanged(s: Editable?) {}
    }


    // 유효성검사체크 로직 / 버튼 활성화
    private fun signUpCheckLogic(){
        with(binding){
            val emailFlag = validate.isEmailValid(signupEmailEt.text.toString())
            val passFlag = validate.isPasswordValid(signupPasswordEt.text.toString())
            val passCheckFlag = validate.isPasswordCheckValid(signupPasswordEt.text.toString(), signupPasswordCheckEt.text.toString())
            val nicknameFlag = validate.isNicknameValid(signupNicknameEt.text.toString())

            signupErrorTv.text = when {
                !emailFlag -> getString(R.string.email_flag_message)
                !passFlag -> getString(R.string.password_flag_message)
                !passCheckFlag -> getString(R.string.password_check_flag_message)
                !nicknameFlag -> getString(R.string.nickname_flag_message)
                else -> null
            }

            val allFieldsValid = emailFlag && passFlag && passCheckFlag && nicknameFlag
            if (allFieldsValid) {
                signupBtn.isEnabled = true
                signupBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.main_yellow))
            } else {
                signupBtn.isEnabled = false
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}