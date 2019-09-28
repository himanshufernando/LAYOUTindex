package com.project.layoutindex.ui.home.mvvm

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.project.layoutindex.modeldata.User
import com.project.layoutindex.modeldata.Users
import com.project.layoutindex.services.api.APIInterface
import com.project.layoutindex.services.api.ApiClient
import com.project.layoutindex.services.network.InternetConnection
import com.project.layoutindex.services.network.NetworkErrorHandler
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeRepo (application: Application){

    var app: Application = application
    var apiInterface: APIInterface = ApiClient.client(application)
    var networkErrorHandler: NetworkErrorHandler = NetworkErrorHandler()



    fun getUsers(loding : ObservableField<Boolean>,page : Int) : MutableLiveData<Users> {
        val result = MutableLiveData<Users>()
        var data = Users()

        loding.set(true)

        if (!InternetConnection.checkInternetConnection(app))
            Toast.makeText(app, "No internet connection you will miss the latest information ", Toast.LENGTH_LONG).show()

        apiInterface.getAllUsers(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Users> {
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(log: Users) {
                    data = log
                }
                override fun onError(e: Throwable) {
                    data.respondStatus = false
                    data.networkError = networkErrorHandler(e)
                    result.postValue(data)
                    loding.set(false)
                }
                override fun onComplete() {
                    result.postValue(data)
                    loding.set(false)
                }
            })

        return result
    }


    fun getUserByID(loding : ObservableField<Boolean>,userID : String) : MutableLiveData<User> {
        val result = MutableLiveData<User>()
        var data = User()

        loding.set(true)

        if (!InternetConnection.checkInternetConnection(app))
            Toast.makeText(app, "No internet connection you will miss the latest information ", Toast.LENGTH_LONG).show()

        if( (userID.isNullOrEmpty()) || (userID == "null")  ){
            data.networkError.errorCode = "EMPTY"
            data.networkError.errorTitle = "Empty"
            data.networkError.errorMessage = "Users ID is empty !"
            data.respondStatus = false
            loding.set(false)
            result.postValue(data)
        }else{
            apiInterface.getUsersById(userID.toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<User> {
                    override fun onSubscribe(d: Disposable) {
                    }
                    override fun onNext(log: User) {
                        data = log
                    }
                    override fun onError(e: Throwable) {
                        data.respondStatus = false
                        data.networkError = networkErrorHandler(e)
                        result.postValue(data)
                        loding.set(false)
                    }
                    override fun onComplete() {
                        result.postValue(data)
                        loding.set(false)
                    }
                })
        }
        return result
    }

}