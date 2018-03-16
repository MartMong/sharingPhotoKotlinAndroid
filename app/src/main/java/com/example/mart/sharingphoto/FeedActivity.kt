package com.example.mart.sharingphoto

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {

    var useremailFromFiebase : ArrayList<String> = ArrayList<String>()
    var userImageFromFirebase : ArrayList<String> = ArrayList<String>()
    var userCommentFromFirebase : ArrayList<String> = ArrayList<String>()

    var firebaseDatabase : FirebaseDatabase? = null
    var myRef : DatabaseReference? = null
    var adapter : PostClass? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        firebaseDatabase = FirebaseDatabase.getInstance()
        myRef = firebaseDatabase!!.getReference()

        getDataFromFirebase()

        println(userCommentFromFirebase)

        adapter = PostClass(useremailFromFiebase,userImageFromFirebase,userCommentFromFirebase,this)
        listView.adapter = adapter



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_in_feed,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.add_post){

//            Go to upload page

            val intent = Intent (applicationContext,UploadActivity::class.java)
            startActivity(intent)
        }
        else if(item?.itemId == R.id.log_out){
//            log out clear user data
            var shredPreferences : SharedPreferences = this.getSharedPreferences("com.example.mart.sharingphoto",android.content.Context.MODE_PRIVATE)
            shredPreferences!!.edit().putString("email","-").apply()
            shredPreferences!!.edit().putString("password","-").apply()

            var intend = Intent(applicationContext,SignUpActivity::class.java)
            startActivity(intend)
        }

        return super.onOptionsItemSelected(item)
    }

    fun getDataFromFirebase(){
//      set data on list view from post class custom view
        val newReference = firebaseDatabase!!.getReference("Posts")

        newReference.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(p0: DataSnapshot?) {

//                clear old data
                adapter!!.clear()
                useremailFromFiebase.clear()
                userImageFromFirebase.clear()
                userCommentFromFirebase.clear()

                for (snapShot in p0!!.children){

                    //hash map has <dowloadUrl,comment>
                    val hashMap = snapShot.value as HashMap<String,String>

                    if (hashMap.size>0){
                        val email = hashMap["useremail"]
                        val comment = hashMap["comment"]
                        val image = hashMap["downloadUrl"]

                        if(email!=null){
                            useremailFromFiebase.add(email)
                        }

                        if(comment != null){

                            userCommentFromFirebase.add(comment)
                        }

                        if(image != null){

                            userImageFromFirebase.add(image)
                        }

                        adapter!!.notifyDataSetChanged()
                    }
                println(useremailFromFiebase)

                }


            }



            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        )

    }
}
