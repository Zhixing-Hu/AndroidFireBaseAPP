package com.example.firebasedatalistview

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.widget.Button
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.button
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        var button1 = findViewById<Button>(R.id.btomain)
        button1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val bundle : Bundle?= intent.extras
        val name = bundle!!.getString("name")
        val department = bundle.getString("department")
        val salary = bundle.getString("salary")
        val phone = bundle.getString("phone")
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS),
                111
            )
        } else
            sendMsg(name, department, salary, phone)
        button.setOnClickListener {
            var sms = SmsManager.getDefault()
            sms.sendTextMessage(
                editTextPhone.text.toString(),
                "Admin",
                editTextTextMultiLine.text.toString(),
                null,
                null
            )
            editTextTextMultiLine.setText("")

        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            receiveMsg()
    }
    private fun sendMsg(ename : String ?= null, department : String ?= null, salary : String ?= null, ephone : String ?= null){
        editTextPhone.setText(ephone.toString())
        editTextTextMultiLine.setText("Hi $ename, Your profile have changed. Salary: $salary                Department: $department")
    }
    private fun receiveMsg(ename : String ?= null, department : String ?= null, salary : String ?= null, ephone : String ?= null) {
        var br = object : BroadcastReceiver(){
            override fun onReceive(p0: Context?, p1: Intent?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                    for (sms in Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
//                        Toast.makeText(applicationContext, sms.displayMessageBody, Toast.LENGTH_LONG).show()
                        editTextPhone.setText(sms.originatingAddress)
                        editTextTextMultiLine.setText(sms.displayMessageBody)
                    }
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }
}