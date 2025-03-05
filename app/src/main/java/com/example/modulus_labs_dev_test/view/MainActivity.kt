package com.example.modulus_labs_dev_test.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.modulus_labs_dev_test.R
import com.example.modulus_labs_dev_test.view.screens.PokemonFragment

class MainActivity : AppCompatActivity(R.layout.layout_activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
//                add<PokemonFragment>(R.id.pokemon_screen_fragment)
                replace(android.R.id.content, PokemonFragment())
            }
        }
    }
}