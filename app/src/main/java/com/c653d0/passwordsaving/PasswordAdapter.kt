package com.c653d0.passwordsaving

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.c653d0.passwordsaving.database.PasswordInfo

class PasswordAdapter : RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>() {
    private var dataList: List<PasswordInfo>? = null

    fun setData(list: List<PasswordInfo>) {
        this.dataList = list
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        return create(parent)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        if(dataList == null){
            return
        }
        val password = dataList!![position]

        holder.setContentLabel(password.label)
        holder.setContentId(password.uid)

        holder.itemView.setOnClickListener {
            Toast.makeText(it.context,"id:${password.id}",Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_homeFragment_to_detailInfoFragment)
        }
    }

    override fun getItemCount(): Int {
        return if (dataList == null) {
            0
        } else {
            dataList!!.size
        }
    }


    companion object {
        fun create(parent: ViewGroup): PasswordViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_edit_content_card_view, parent, false)

            return PasswordViewHolder(view)
        }
    }


    class PasswordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val homeContentLabel: TextView = itemView.findViewById(R.id.home_content_label)
        private val homeContentId: TextView = itemView.findViewById(R.id.home_content_id)

        fun setContentLabel(text:String){
            homeContentLabel.text = text
        }

        fun setContentId(text:String){
            homeContentId.text = text
        }
    }
}