package com.example.assignmenttest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignmenttest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Activity위에 MainFragment올리기
        setMainFragment()
    }



    private fun setMainFragment() {
        val mainFragment = MainFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, mainFragment)
            .commit()
    }
}