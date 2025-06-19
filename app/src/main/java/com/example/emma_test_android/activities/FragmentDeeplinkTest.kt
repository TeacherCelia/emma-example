package com.example.emma_test_android.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.emma_test_android.R

class FragmentDeepLinkTest : Fragment(R.layout.fragment_deeplink_test) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnVolver = view.findViewById<Button>(R.id.btn_volver)
        btnVolver.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }
}