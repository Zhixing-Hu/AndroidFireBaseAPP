package com.example.firebasedatalistview

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class UserDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        val ename = findViewById<TextView>(R.id.empname)
        val edep = findViewById<TextView>(R.id.empdepartment)
        val esal = findViewById<TextView>(R.id.empsalary)
        val ephone = findViewById<TextView>(R.id.empphone)
        val pic = findViewById<ImageView>(R.id.pic)

        val bundle : Bundle?= intent.extras
        val name = bundle!!.getString("name")
        val department = bundle.getString("department")
        val salary = bundle.getString("salary")
        val phone = bundle.getString("phone")
        val empid = bundle.getString("empid")

        ename.text = name
        edep.text = department
        esal.text = salary
        ephone.text = phone

        val imageName = empid.toString()
        val storageRef = FirebaseStorage.getInstance().reference.child("images/$imageName.jpeg")
        val localfile = File.createTempFile("tempImage", "jpeg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            pic.setImageBitmap(bitmap)
        }.addOnFailureListener {
            Toast.makeText(this, "Fail to load the picture", Toast.LENGTH_SHORT).show()
        }

        var button3 = findViewById<Button>(R.id.goback)
        button3.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

    }
}