package com.example.myapplication

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.util.Log.i
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amazonaws.logging.Log
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.core.Amplify
import com.google.android.datatransport.runtime.logging.Logging.i
//import com.example.myapplication.navigation.*
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.FirebaseStorage.getInstance
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_alarm.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.security.Policy.getInstance
import java.util.Calendar.getInstance
import java.util.Currency.getInstance

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(p0 :MenuItem): Boolean {
        setToolbarDefault()
//        when(p0.itemId){
//            R.id.action_home->{
//                var detailViewFragment =DetailViewFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.main_content,detailViewFragment).commit()
//                return true
//            }
//            R.id.action_search->{
//                var gridFragment =GridFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.main_content,gridFragment).commit()
//                return true
//            }
//            R.id.action_add_photo->{
//                if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
//                    startActivity(Intent(this,
//                        AddPhotoActivity::class.java))
//                }
//
//                return true
//        }
//            R.id.action_favorite_alarm->{
//            var alarmFragment =AlarmFragment()
//            supportFragmentManager.beginTransaction().replace(R.id.main_content,alarmFragment).commit()
//                return true
//        }
//            R.id.action_account->{ //유저페이지
//            var userFragment =UserFragment()
//                var bundle = Bundle()
//                var uid = FirebaseAuth.getInstance().currentUser?.uid
//                bundle.putString("destinationUid",uid)
//                userFragment.arguments = bundle
//                supportFragmentManager.beginTransaction().replace(R.id.main_content,userFragment).commit()
//                return true
//        }

//        }
        return false

    }
    fun setToolbarDefault() {
        toolbar_title_image.visibility = View.VISIBLE
        toolbar_btn_back.visibility = View.GONE
        toolbar_username.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.selectedItemId = R.id.action_home
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)




    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode ==UserFragment.PICK_PROFILE_FROM_ALBUM && resultCode == Activity.RESULT_OK){
//            var imageUri = data?.data
//            var uid = FirebaseAuth.getInstance().currentUser!!.uid
//            var storageRef = FirebaseStorage.getInstance().reference.child("userProfileImages").child(uid)
//            storageRef.putFile(imageUri!!).continueWithTask{_: Task<UploadTask.TaskSnapshot>->
//                 return@continueWithTask storageRef.downloadUrl
//            }.addOnSuccessListener { uri->
//                var map = HashMap<String,Any>()
//                map["image"] = uri.toString()
//                FirebaseFirestore.getInstance().collection("profileImages").document(uid).set(map)
//
//            }
//        }
//    }
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    Backend.handleWebUISignInResponse(requestCode, resultCode, data)
}

}
