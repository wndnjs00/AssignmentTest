package com.example.assignmenttest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.assignmenttest.databinding.FragmentCompleteBinding
import com.example.assignmenttest.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompleteFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentCompleteBinding? = null
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
    }


    private fun nickNameUpdate(){
        viewLifecycleOwner.lifecycleScope.launch{
            sharedViewModel.nickName.collect{ nickName ->
                if(!nickName.isNullOrEmpty()){
                    binding.completeExplainTv.text = "\"${nickName}\"님 환영합니다!"
                }else{
                    // nickname이 null이거나 비어있을 경우 email로 대체
                    sharedViewModel.email.collect{ email ->
                        binding.completeExplainTv.text = "\"${email}\"님 환영합니다!"
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}