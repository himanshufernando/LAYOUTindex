package com.project.layoutindex.ui.home.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.layoutindex.R
import com.project.layoutindex.modeldata.UserList
import kotlinx.android.synthetic.main.listview_users.view.*

class UserListAdaptar (val userList: ArrayList<UserList>, val context: Context) :
    RecyclerView.Adapter<UserListAdaptar.ViewHolderUserList>() {

    lateinit var mClickListener: ClickListener

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUserList {
        return ViewHolderUserList(LayoutInflater.from(context).inflate(R.layout.listview_users, parent, false))

    }


    override fun onBindViewHolder(holder: ViewHolderUserList, position: Int) {
        var itemPostion = userList[position]

        holder.textviewUserID.text = itemPostion.userID.toString()
        holder.textviewUserName.text = itemPostion.userFirstName
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }
    interface ClickListener {
        fun onClick(user: UserList, aView: View)
    }

    inner class ViewHolderUserList(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener  {
        val textviewUserID = view.textview_user_id
        val textviewUserName = view.textview_user_name

        init {
            view.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            mClickListener.onClick(userList[adapterPosition], p0!!)

        }

    }
}
