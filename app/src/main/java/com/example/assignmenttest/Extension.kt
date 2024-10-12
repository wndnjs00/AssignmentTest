package com.example.assignmenttest

import androidx.fragment.app.Fragment

// Fragment -> Fragment 이동 확장함수
fun Fragment.replaceFragment(id: Int, fragment: Fragment, addToBackStack: Boolean = false){
    parentFragmentManager.beginTransaction().apply {
        replace(id, fragment)
        if(addToBackStack){addToBackStack(null)}
        commit()
    }
}