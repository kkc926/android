package com.example.myapplication.navigation

import android.content.Intent
import com.example.myapplication.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.model.AlarmDTO
import com.example.myapplication.model.ContentDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item_detail.view.*

class DetailViewFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    var uid : String?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_detail, container, false)
        firestore = FirebaseFirestore.getInstance()
        uid = FirebaseAuth.getInstance().currentUser?.uid
        view.detailviewfragment_recyclerview.adapter=DetailRecyclerViewAdapter()
        view.detailviewfragment_recyclerview.layoutManager=LinearLayoutManager(activity)
        return view
    }
    inner class DetailRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var contentDTOs : ArrayList<ContentDTO> = arrayListOf()
        var contentUidList : ArrayList<String> = arrayListOf()

        init {

                firestore?.collection("images")?.orderBy("timestamp")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    contentDTOs.clear()
                    contentUidList.clear()
                for(snapshot in querySnapshot!!.documents){
                    var item = snapshot.toObject(ContentDTO::class.java)
                    contentDTOs.add(item!!)
                    contentUidList.add(snapshot.id)
                }
                notifyDataSetChanged()
            }
        }


        override fun onCreateViewHolder(p0: ViewGroup, p1:Int): RecyclerView.ViewHolder {

            var view = LayoutInflater.from(p0.context).inflate(R.layout.item_detail,p0, false)
            return CustomViewHolder(view)

        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun getItemCount(): Int {
            return contentDTOs.size
        }

        override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {

            var viewholder = (p0 as CustomViewHolder).itemView

            //User ID
            viewholder.detailviewitem_profile_textview.text = contentDTOs!![p1].userId
            Glide.with(p0.itemView.context).load(contentDTOs!![p1].imageUrl).into(viewholder.detailviewitem_imageview_content)
            //Image
            viewholder.detailviewitem_explain_textview.text=contentDTOs!![p1].explain
            //Explain of content

            viewholder.detailviewitem_favoritecounter_textview.text="Likes"+contentDTOs!![p1].favoriteCount

            Glide.with(p0.itemView.context).load(contentDTOs!![p1].imageUrl).into(viewholder.detailviewitem_profile_image)



            //좋아요눌렀을때 이벤트
            viewholder.detailviewitem_favorite_imageview.setOnClickListener {
                favoriteEvent(p1) }

            if (contentDTOs!![p1].favorites.containsKey(uid)) {

            viewholder.detailviewitem_favorite_imageview.setImageResource(R.drawable.ic_favorite)

             } else {

            viewholder.detailviewitem_favorite_imageview.setImageResource(R.drawable.ic_favorite_border)
        }
            viewholder.detailviewitem_profile_image.setOnClickListener {
                var fragment=UserFragment()
                var bundle = Bundle()
                bundle.putString("destinationUid",contentDTOs[p1].uid)
                bundle.putString("userId",contentDTOs[p1].userId)
                fragment.arguments = bundle
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_content,fragment)?.commit()

            }
        viewholder.detailviewitem_comment_imageview.setOnClickListener { v ->
            var intent = Intent(v.context,CommentActivity::class.java)
            intent.putExtra("contentUid",contentUidList[p1])
            intent.putExtra("destinationUid",contentDTOs[p1].uid)
            startActivity(intent)

        }
        }


        fun favoriteEvent(position: Int) { //좋아요버튼
            var tsDoc = firestore?.collection("images")?.document(contentUidList[position])
            firestore?.runTransaction { transaction ->


                var contentDTO = transaction.get(tsDoc!!).toObject(ContentDTO::class.java)

                if (contentDTO!!.favorites.containsKey(uid)) { //좋아요 버튼 눌려있음
                    // Unstar the post and remove self from stars
                    contentDTO?.favoriteCount = contentDTO?.favoriteCount - 1
                    contentDTO?.favorites.remove(uid)

                } else { //좋아요 안함

                    contentDTO?.favoriteCount = contentDTO?.favoriteCount + 1
                    contentDTO?.favorites[uid!!] = true

                    favoriteAlarm(contentDTOs[position].uid!!)
                }
                transaction.set(tsDoc, contentDTO)
            }
        }

            fun favoriteAlarm(destinationUid : String){
                var alarmDTO = AlarmDTO()
                alarmDTO.destinationUid = destinationUid
                alarmDTO.userId=FirebaseAuth.getInstance().currentUser?.email
                alarmDTO.uid = FirebaseAuth.getInstance().currentUser?.uid
                alarmDTO.kind = 0
                alarmDTO.timestamp = System.currentTimeMillis()
                FirebaseFirestore.getInstance().collection("alarms").document().set(alarmDTO)


        }

    }
}