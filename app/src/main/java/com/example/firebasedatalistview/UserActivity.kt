package com.example.firebasedatalistview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.user_item.*


class UserActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<User>
//    private lateinit var listener: (User) -> Unit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        var button = findViewById<Button>(R.id.backtomain)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        userRecyclerView = findViewById(R.id.userlist)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<User>()
        getUserData()
//        userRecyclerView.adapter = MyAdapter(userArrayList, this)
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().reference
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)
                    }
                    var adapter = MyAdapter(userArrayList)
                    userRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
                        override fun onItemClick(postion: Int) {
//                            Toast.makeText(this@UserActivity, "You clicked on $postion", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@UserActivity, UserDetailActivity::class.java)
                            intent.putExtra("empid", userArrayList[postion].empid)
                            intent.putExtra("name", userArrayList[postion].ename)
                            intent.putExtra("department", userArrayList[postion].department)
                            intent.putExtra("salary", userArrayList[postion].salary)
                            intent.putExtra("phone", userArrayList[postion].ephone)

                            startActivity(intent)
                        }
                    })
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }


}