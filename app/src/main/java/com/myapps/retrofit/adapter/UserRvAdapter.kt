package com.myapps.retrofit.adapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myapps.retrofit.R
import com.myapps.retrofit.model.UserResponse

class UserRvAdapter:RecyclerView.Adapter<UserRvAdapter.ViewHolder>() {
      var userList= mutableListOf<UserResponse>()
    var listener:OnClickListener? = null
    var listenerLong:OnLongClickListener?=null
    fun setData(newList:List<UserResponse>){
        userList.clear()
        userList.addAll(newList)
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        fun bind(user:UserResponse){
            var user: UserResponse =user
            var title=itemView.findViewById<TextView>(R.id.text_title)
            var email=itemView.findViewById<TextView>(R.id.text_email)
            title.text=user.name
            email.text=user.email
            var popButton=itemView.findViewById<Button>(R.id.menu)
            popButton.setOnClickListener {
                if (adapterPosition!=RecyclerView.NO_POSITION)
                    listener?.OnClicked(user.id,popButton,user)

            }
            itemView.setOnLongClickListener {
                if (adapterPosition!=RecyclerView.NO_POSITION)
                    listenerLong?.OnClicked(user)
                return@setOnLongClickListener true
            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent,false)
    return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return  userList.size
    }
    fun setOnClickListener(listener: OnClickListener){
        this.listener=listener

    }
    interface OnClickListener{
        fun OnClicked(position: Int,view: View,user: UserResponse)
    }
    fun setOnLongClickListener(listenerLong: OnLongClickListener){
        this.listenerLong=listenerLong

    }
    interface OnLongClickListener{
        fun OnClicked(user: UserResponse)
    }
}