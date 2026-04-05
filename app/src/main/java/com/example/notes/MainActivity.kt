package com.example.notes

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.transition.Visibility
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        navController = (supportFragmentManager.findFragmentById(R.id.fragmenet_container) as NavHostFragment).navController

        binding.floatBtn.setOnClickListener {
            navController.navigate(R.id.writeNoteFragment, null, NavOptions.Builder().setPopUpTo(R.id.writeNoteFragment,true).build())
        }
        navController.addOnDestinationChangedListener {_,destination,_ ->
            when(destination.id){
                R.id.writeNoteFragment ->{
                    binding.floatBtn.visibility= View.GONE
                    binding.headerLayout.visibility=View.GONE
                }
                R.id.homwFragment ->{
                    binding.floatBtn.visibility= View.VISIBLE
                    binding.headerLayout.visibility=View.VISIBLE
                }
            }

        }

    }
}