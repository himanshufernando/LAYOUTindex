package com.project.layoutindex.ui.home.mvvm

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.project.layoutindex.modeldata.User
import com.project.layoutindex.modeldata.Users

class HomeModelView (application: Application) : AndroidViewModel(application) {


    var userRepository: HomeRepo = HomeRepo(application)
    val isUserListLoading = ObservableField<Boolean>()

    var editUserID = MutableLiveData<String>()


    fun getUserDetails(): MutableLiveData<Users> {
        return userRepository.getUsers(isUserListLoading, 2)
    }

    fun getUserByID(): MutableLiveData<User> {
        return userRepository.getUserByID(isUserListLoading, editUserID.value.toString())
    }

}