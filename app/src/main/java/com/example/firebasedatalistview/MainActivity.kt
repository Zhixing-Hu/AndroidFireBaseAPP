package com.example.firebasedatalistview

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.widget.Button
import androidx.core.app.ActivityCompat
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
//    private lateinit var dbref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var database = FirebaseDatabase.getInstance().reference
        var button1 = findViewById<Button>(R.id.button)
        button1.setOnClickListener {
            var empid = editTextTextPersonName1.text.toString().toInt()
            var ename = editTextTextPersonName2.text.toString()
            var edep = editTextTextPersonName3.text.toString()
            var esal = editTextTextPersonName4.text.toString()
            var ephone = editTextTextPersonName5.text.toString()

            database.child(empid.toString()).setValue(User(empid, ename, edep, esal, ephone))
            val i = Intent(this, MessageActivity::class.java)
            i.putExtra("name", ename)
            i.putExtra("phone", ephone)
            i.putExtra("salary", esal)
            i.putExtra("department", edep)
            startActivity(i)
        }


        var button2 = findViewById<Button>(R.id.gotolistview)
        button2.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }
        var button3 = findViewById<Button>(R.id.mainuploadimg)
        button3.setOnClickListener {
            val intent = Intent(this, StorageActivity::class.java)
            startActivity(intent)
        }
    }

}