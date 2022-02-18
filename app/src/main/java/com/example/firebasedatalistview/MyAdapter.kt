package com.example.firebasedatalistview

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.File

class MyAdapter(private val userList : ArrayList<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(postion : Int)
    }

    fun setOnItemClickListener(listener : onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)

        return MyViewHolder(itemView, mListener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]
        val imageName = currentitem.empid.toString()
        val storageRef = FirebaseStorage.getInstance().reference.child("images/$imageName.jpeg")
        val localfile = File.createTempFile("tempImage", "jpeg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            holder.epic.setImageBitmap(bitmap)
        }

        holder.ename.text = currentitem.ename
        holder.department.text = currentitem.department
        holder.salary.text = currentitem.salary


    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class  MyViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val ename : TextView = itemView.findViewById(R.id.ename)
        val department : TextView = itemView.findViewById(R.id.department)
        val salary : TextView = itemView.findViewById(R.id.salary)
        val epic : ImageView = itemView.findViewById(R.id.profile_pic)



        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }

}
