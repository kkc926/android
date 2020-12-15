package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.regions.Regions
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
//    var auth : FirebaseAuth? = null
//    var googleSignInClient: GoogleSignInClient? =null
//    var GOOGLE_LOGIN_CODE = 9001
//    var callbackManager : CallbackManager? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupAuthButton(UserData)

        //set default screen
//        bottom_navigation.selectedItemId=R.id.action_home
//        auth = FirebaseAuth.getInstance()

//        email_login_button.setOnClickListener {
//            signinAndSignup()
//        }

//        google_sign_in_button.setOnClickListener {
//            googleLogin()
//        }
//        facebook_login_button.setOnClickListener {
//            facebookLogin()
//        }

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()



//        googleSignInClient = GoogleSignIn.getClient(this, gso)
////        printHashKey()(페이스북 해시값 가져오는 함수실행)
//        callbackManager= CallbackManager.Factory.create()


    }

//    fun printHashKey(){//(페이스북 해시값 가져오는 함수)
//        try {
//            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
//            for (signature in info.signatures) {
//                val md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                val hashKey = String(Base64.encode(md.digest(),0))
//                Log.i("TAG", "printHashKey() Hash key:$hashKey=")
//            }
//        }catch (e:NoSuchAlgorithmException){
//            Log.e("TAG","printHashKey()",e)
//        }
//        catch (e: Exception) {
//            Log.e("TAG", "printHashKey()", e)
//        }
//
//    }
//    fun googleLogin(){
//        var signInIntent = googleSignInClient?.signInIntent
//        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
//    }
//    fun facebookLogin(){
//        LoginManager.getInstance()
//            .logInWithReadPermissions(this, Arrays.asList("public_profile","email"))
//
//        LoginManager.getInstance()
//            .registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
//                override fun onSuccess(result: LoginResult?) {
//                    handleFackbookAccessToken(result?.accessToken)
//                }
//
//                override fun onCancel() {
//
//                }
//
//                override fun onError(error: FacebookException?) {
//
//                }
//
//            })
//    }
//    fun handleFackbookAccessToken(token: AccessToken?){
//        var credental = FacebookAuthProvider.getCredential(token?.token!!)
//        auth?.signInWithCredential(credental)
//            ?.addOnCompleteListener {
//                    task ->
//                if(task.isSuccessful){
//                    // Login
//                    moveMainPage(task.result?.user)
//                }else{
//                    // Show the error message
//                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()
//                }
//            }
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Backend.handleWebUISignInResponse(requestCode, resultCode, data)
//        callbackManager?.onActivityResult(requestCode,resultCode, data)
//        if(requestCode==GOOGLE_LOGIN_CODE){
//            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
//            if(result!!.isSuccess){
//                var account = result.signInAccount
////                firebaseAuthWithGoogle(account)
//            }
//        }
}

//    fun firebaseAuthWithGoogle(account:GoogleSignInAccount?){
//        var credential = GoogleAuthProvider.getCredential(account?.idToken, null)
//        auth?.signInWithCredential(credential)
//            ?.addOnCompleteListener {
//                    task ->
//                if(task.isSuccessful){
//                    // Login
//                    moveMainPage(task.result?.user)
//                }else{
//                    // Show the error message
//                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
//                }
//            }
//    }

//    fun signinAndSignup(){
//        auth?.createUserWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
//            ?.addOnCompleteListener {
//                    task ->
//                        if(task.isSuccessful){
//                            // Creating a user account
//                            moveMainPage(task.result?.user)
//                        }else if(!task.exception?.message.isNullOrEmpty()){
//                            // Show the error message
//                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
//                        }else{
//                            // Login if you have account
//                            signinEmail()
//                        }
//                    }
//    }
//    fun signinEmail(){ //로그인 코드
//        auth?.signInWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
//            ?.addOnCompleteListener {
//                    task ->
//                if(task.isSuccessful){
//                    // Login
//                    moveMainPage(task.result?.user)
//                }else{
//                    // Show the error message
//                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
//                }
//            }
//    }
//    override fun onStart() {
//        super.onStart()
//
//        //자동 로그인 설정
//        moveMainPage(auth?.currentUser)
//
//    }
    fun moveMainPage(user:UserData?){
        if(user!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    // anywhere in the MainActivity class
    private fun setupAuthButton(userData: UserData) {

        // register a click listener
        google_sign_in_button1.setOnClickListener { _ ->

            if (userData.isSignedIn.value!!) {
                Backend.signOut()
            } else {
                Backend.signIn(this)
                moveMainPage(UserData)
                Log.d("라벨", UserData.toString() )



            }
        }
    }




}