package com.project.layoutindex.ui.home.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.project.layoutindex.R
import com.project.layoutindex.databinding.ActivityHomeBinding
import com.project.layoutindex.modeldata.NetworkError
import com.project.layoutindex.modeldata.User
import com.project.layoutindex.modeldata.Users
import com.project.layoutindex.modeldata.UserList
import com.project.layoutindex.ui.home.adaptor.UserListAdaptar
import com.project.layoutindex.ui.home.mvvm.HomeModelView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_user_detail.*

class HomeActivity : AppCompatActivity() {


    lateinit var bindingHome: ActivityHomeBinding
    lateinit var viewModelHome: HomeModelView


    lateinit var dialogUserDetails: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        bindingHome = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModelHome = ViewModelProviders.of(this).get(HomeModelView::class.java)
        bindingHome.user = viewModelHome


        setSupportActionBar(toolbar)
    }


    override fun onStart() {
        super.onStart()
        viewModelHome!!.getUserDetails().observe(this, Observer<Users> {
            it?.let { result ->
                if (result.respondStatus) {
                    var userAdaptar = UserListAdaptar(result.userist, this)
                    recyclerView_user.adapter = userAdaptar
                    userAdaptar.setOnItemClickListener(object : UserListAdaptar.ClickListener {
                        override fun onClick(user: UserList, aView: View) {
                            userDialog(user)
                        }
                    })
                } else {
                    errorAlertDialog(result.networkError)
                }
            }
        })
    }

    override fun isDestroyed(): Boolean {

        return super.isDestroyed()

    }

    override fun onStop() {
        super.onStop()


    }

    override fun onLowMemory() {
        super.onLowMemory()

    }

    override fun onResume() {
        super.onResume()

    }


    fun searchOnClick(view: View) {
        viewModelHome!!.getUserByID().observe(this, Observer<User> {
            it?.let { result ->
                if (result.respondStatus) {
                    userDialog(result.user)
                } else {
                    errorAlertDialog(result.networkError)
                }
            }
        })
    }


    fun userDialog(user: UserList) {

        dialogUserDetails = Dialog(this)
        dialogUserDetails.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogUserDetails.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialogUserDetails.setContentView(R.layout.dialog_user_detail)
        dialogUserDetails.setCancelable(true)


        dialogUserDetails.textview_user_fname.text = user.userFirstName
        dialogUserDetails.textview_user_lname.text = user.userLastName
        dialogUserDetails.textview_user_email.text = user.userEmail


        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.logo)
        requestOptions.error(R.drawable.logo)


        val requestListener = object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Bitmap>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Bitmap,
                model: Any,
                target: Target<Bitmap>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        }

        Glide.with(this)
            .asBitmap()
            .load(user.userAvatar)
            .apply(requestOptions)
            .listener(requestListener)
            .into(dialogUserDetails.imageview_avatar)



        dialogUserDetails.show()

    }


    fun userDetailsDialogClose(view: View) {
        dialogUserDetails.dismiss()
    }


    fun errorAlertDialog(networkError: NetworkError) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(networkError.errorTitle)
        alertDialogBuilder.setMessage(networkError.errorMessage)
        alertDialogBuilder.setPositiveButton(
            "OK",
            DialogInterface.OnClickListener { _, _ -> return@OnClickListener })
        alertDialogBuilder.show()
    }

    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Exit!")
        alertDialogBuilder.setMessage("Do you really want to exit ?")
        alertDialogBuilder.setPositiveButton(
            "YES"
        ) { _, _ -> super.onBackPressed() }
        alertDialogBuilder.setNegativeButton(
            "NO",
            DialogInterface.OnClickListener { _, _ -> return@OnClickListener })
        alertDialogBuilder.show()

    }


}
