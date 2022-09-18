package com.myapps.retrofit.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myapps.retrofit.api.ApiService
import com.myapps.retrofit.api.RetrofitInstance
import com.myapps.retrofit.model.UserRequest
import com.myapps.retrofit.model.UserResponse
import com.myapps.retrofit.model.UserUpdateRequest
import com.myapps.retrofit.utility.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception

class AppRepository {

    val list: MutableList<UserResponse> = ArrayList()
    var userObj=MutableLiveData<UserResponse>()
    val userList=MutableLiveData<Resource<List<UserResponse>>>(Resource.Empty)


    fun fetchUsers():MutableLiveData<Resource<List<UserResponse>>>
    {
        try {

            val api = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
            val call = api.getAllUsers()
            call.enqueue(object : Callback<List<UserResponse>> {
                override fun onResponse(
                    call: Call<List<UserResponse>>,
                    response: Response<List<UserResponse>>
                ) {
                    if (response.isSuccessful) {
                        userList.postValue(Resource.Success(response.body()))
                    }

                }

                override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                    userList.postValue(Resource.Error("No internet connection"))
                }
            })
        }
        catch (e:Exception){
            userList.postValue(Resource.Error("Some error occured"))
        }
        return userList
    }
    fun getUser(id: Int):MutableLiveData<UserResponse>
    {
        try {

            val api = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
            val call = api.getUser(id)
            call.enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {


                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    userList.postValue(Resource.Error("No internet connection"))
                }
            })
        }
        catch (e:Exception){
            userList.postValue(Resource.Error("Some error occured"))
        }
        return userObj
    }
    fun createUser(userObj:UserRequest,onResult: (UserResponse?) -> Unit){
        val api = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
        val call = api.createUser(userObj)
        call.enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val newUser=response.body()
                onResult(newUser)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                onResult(null)            }
        })

    }
    fun updateUser(position:Int,userObj:UserUpdateRequest,onResult: (UserResponse?) -> Unit){
        val api = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
        val call = api.updateUser(position,userObj)
        call.enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val updateUser=response.body()
                onResult(updateUser)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                onResult(null)
            }
        })

    }
    fun deleteUser(id:Int){

        val api = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
        val call = api.deleteUser(id)
        call.enqueue(object :Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
            }

        })
    }

}