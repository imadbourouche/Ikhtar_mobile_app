package com.example.rentgo

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.userside.Alerte
import com.example.userside.databinding.SignalementCardLayoutBinding

class AlerteAdapter (val context: Context, var alertes:List<Alerte>):
    RecyclerView.Adapter<ViewHolder2>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        return ViewHolder2(SignalementCardLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {


        holder.binding.apply {

            dateTextView.text = alertes[position].date
        }
    }

    override fun getItemCount() = alertes.size
}

class ViewHolder2(val binding: SignalementCardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

}