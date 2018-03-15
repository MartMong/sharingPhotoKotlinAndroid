package com.example.mart.sharingphoto

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_view.view.*

//import kotlinx.android.synthetic.main.custom_view.view

/**
 * Created by Mart on 15/3/2561.
 */
class PostClass (private val userEmail:ArrayList<String>,private  val userImage: ArrayList<String>,
                 private val userComment:ArrayList<String>,private val context: Activity)
    :ArrayAdapter<String>(context,com.example.mart.sharingphoto.R.layout.custom_view,userEmail){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater = context.layoutInflater

        val customView = layoutInflater.inflate(R.layout.custom_view,null,true)

        customView.customUserName.text = userEmail[position]
        customView.customCommentText.text = userComment[position]

//        Picasso.get().load(userImage[position]).
        Picasso.get().load(userImage[position]).into(customView.customImageView)

        return customView
    }


}