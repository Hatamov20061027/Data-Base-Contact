package com.example.contact_1.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.contact6.Models.MyContact
import com.example.contact6.R
import kotlinx.android.synthetic.main.item_rv.view.*

class ContactAdapter (var list: List<MyContact>, var onMyItemListener: OnMyItemListener): RecyclerView.Adapter<ContactAdapter.VH>() {
    inner class VH(var itemRv:View):RecyclerView.ViewHolder(itemRv){
        fun onBind(myContact: MyContact,position: Int){
             itemRv.ismi.text = myContact.name
             itemRv.raqami.text = myContact.number
            itemRv.image_more.setOnClickListener {
                onMyItemListener.onClickMyItem(myContact, position, itemRv.image_more)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
          holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int = list.size

    interface OnMyItemListener{
        fun onClickMyItem(contact: MyContact, position: Int, imageView: ImageView)
    }
}