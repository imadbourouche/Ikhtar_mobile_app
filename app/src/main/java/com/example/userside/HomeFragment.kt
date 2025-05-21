package com.example.userside

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.userside.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.qlertBtn.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_createSignalementFragment)
        }

        binding.greenNbrBtn.setOnClickListener{
            call(view)
        }

        binding.msgvocalBtn.setOnClickListener{
            findNavController().navigate(R.id.action_home_to_messageVocale)
        }


        return view
    }
    // the call function
    fun call(view: View) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:" + "1111")
        startActivity(dialIntent)
    }
}