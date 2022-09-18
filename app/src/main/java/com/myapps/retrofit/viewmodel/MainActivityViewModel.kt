package com.myapps.retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapps.retrofit.model.UserRequest
import com.myapps.retrofit.model.UserResponse
import com.myapps.retrofit.model.UserUpdateRequest
import com.myapps.retrofit.repository.AppRepository
import com.myapps.retrofit.utility.Resource

class MainActivityViewModel:ViewModel() {
    private var repository= AppRepository()
    private var  userList:MutableLiveData<Resource<List<UserResponse>>> = MutableLiveData()
    private lateinit var userObj:MutableLiveData<UserResponse>

    fun observeUser():LiveData<Resource<List<UserResponse>>> {
       return userList
   }

    fun getUsers(){
        userList = repository.fetchUsers()
    }
    fun getUser(id:Int){
       userObj= repository.getUser(id)
    }
    fun deleteUser(id: Int){
        repository.deleteUser(id)
    }
    fun updateUser(id: Int,userObj:UserUpdateRequest){
        repository.updateUser(id,userObj){

        }
    }
    fun createUser(userResponse: UserRequest){
        val newUser=userResponse
        repository.createUser(userResponse){
            if (it?.id != null) {

            } else {
                Log.d("TTT","Error registering new user")
            }
        }
        }
    }
