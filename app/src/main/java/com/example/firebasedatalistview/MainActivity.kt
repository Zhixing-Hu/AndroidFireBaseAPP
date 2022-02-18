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
    }
//    private fun sendNotification(){
//        dbref = FirebaseDatabase.getInstance().reference
//
//        dbref.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//
//        })
//    }
    private fun sendNotification(phoneNumber : String, userName : String, salary: String, department : String?= null){
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.SEND_SMS),
//                111)
//        }
//        else
//            receiveMsg()
//        val sms = SmsManager.getDefault() as SmsManager
//        sms.sendTextMessage(phoneNumber, "Admin", "message", null, null)

//    Second Method
        val uri = Uri.parse("smsto:$phoneNumber")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra("sms_body", "Hi $userName, Your profile have changed. Salary: $salary                Department: $department           Contact Info: $phoneNumber")
        startActivity(intent)
    }
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            receiveMsg()
//    }
//    private fun receiveMsg() {
//        var br = object : BroadcastReceiver(){
//            override fun onReceive(p0: Context?, p1: Intent?) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//                    for (sms in Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
////                        Toast.makeText(applicationContext, sms.displayMessageBody, Toast.LENGTH_LONG).show()
//                        editTextPhone.setText(sms.originatingAddress)
//                        editTextTextMultiLine.setText(sms.displayMessageBody)
//                    }
//                }
//            }
//
//        }
//        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
//    }

}