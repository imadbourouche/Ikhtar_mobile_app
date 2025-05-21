package com.example.userside

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentgo.AlerteAdapter
import com.example.rentgo.AlerteModel
import com.example.userside.databinding.FragmentHomeBinding
import com.example.userside.databinding.FragmentSignalementsBinding


class SignalementsFragment : Fragment() {

    lateinit var binding: FragmentSignalementsBinding
    lateinit var alerteModel: AlerteModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignalementsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alerteModel = ViewModelProvider(requireActivity()).get(AlerteModel::class.java)
        binding.signalementsRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        binding.signalementsRecyclerView.adapter = AlerteAdapter(requireActivity(),alerteModel.alertes)
        val itemDecor1 = DividerItemDecoration(requireActivity(),1)
        binding.signalementsRecyclerView.addItemDecoration(itemDecor1)
    }


}