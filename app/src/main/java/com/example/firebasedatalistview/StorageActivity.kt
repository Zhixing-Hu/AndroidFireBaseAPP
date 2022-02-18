package com.example.firebasedatalistview

import android.app.ProgressDialog
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebasedatalistview.databinding.ActivityStorageBinding
import com.google.firebase.storage.FirebaseStorage
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class StorageActivity : AppCompatActivity() {

    lateinit var binding : ActivityStorageBinding
    lateinit var ImageUri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.storagebtmain.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        binding.selectImageBtn.setOnClickListener {
            selectImage()
        }

        binding.uploadImg.setOnClickListener {
            uploadIamge()
        }
    }

    private fun uploadIamge() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading File ...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        var fileName = binding.editimgID.text.toString() + ".jpeg"
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
        binding.editimgID.text.clear()
        storageReference.putFile(ImageUri).
                addOnSuccessListener {
                    binding.firebaseImage.setImageURI(null)
                    Toast.makeText(this@StorageActivity, "Successfully uploaded", Toast.LENGTH_SHORT).show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                }.addOnFailureListener {
                    if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this@StorageActivity, "Uploaded Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK){
            ImageUri = data?.data!!
            binding.firebaseImage.setImageURI(ImageUri)
        }
    }
}