package com.myapps.retrofit

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.myapps.retrofit.adapter.UserRvAdapter
import com.myapps.retrofit.databinding.ActivityMainBinding
import com.myapps.retrofit.model.UserRequest
import com.myapps.retrofit.model.UserResponse
import com.myapps.retrofit.model.UserUpdateRequest
import com.myapps.retrofit.utility.Resource
import com.myapps.retrofit.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity(),UserRvAdapter.OnClickListener,UserRvAdapter.OnLongClickListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserRvAdapter
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        initialize()
        binding.newUser.setOnClickListener { dialog() }
        binding.swipeRefresh.setOnRefreshListener { viewModel.getUsers()
            binding.swipeRefresh.isRefreshing = false
        }



        viewModel.getUsers()
        viewModel.observeUser().observe(this){
           when(it){
               is Resource.Empty->{
                   binding.progressbar.visibility=View.VISIBLE
                   binding.error.visibility=View.INVISIBLE


               }
               is Resource.Error->{
                   binding.progressbar.visibility=View.INVISIBLE
                   binding.error.visibility=View.VISIBLE
                   binding.error.text=it.message

               }
               is Resource.Success->{
                   userAdapter?.setData(it.data!!)
                   binding.progressbar.visibility=View.INVISIBLE
                   binding.error.visibility=View.INVISIBLE

               }


           }
        }
    }
    fun initialize(){
        val decoration=DividerItemDecoration(this,LinearLayoutManager.VERTICAL)
        binding.recyclerview.layoutManager=LinearLayoutManager(this)
        binding.recyclerview.addItemDecoration(decoration)
        userAdapter= UserRvAdapter()
        userAdapter.setOnClickListener(this)
        userAdapter.setOnLongClickListener(this)
        binding.recyclerview.adapter=userAdapter
    }

    override fun OnClicked(position: Int,view: View,userResponse: UserResponse) {

        val popupMenu = PopupMenu(applicationContext, view)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->


            when (item!!.itemId) {
                R.id.delete_menu_button -> {
                    viewModel.deleteUser(position)
                }
                R.id.edit->{
                    dialogUpdate(position,userResponse)





                }

            }

            return@setOnMenuItemClickListener true

            }
        popupMenu.show()
        }
    fun dialogUpdate(position: Int,UserObj:UserResponse){
        val dialog=Dialog(this)
        dialog.setContentView(R.layout.dialog_update)
        var email= dialog.findViewById<EditText>(R.id.email_ed)
        email.setText(UserObj.email)
        var name= dialog.findViewById<EditText>(R.id.name_ed)
        name.setText(UserObj.name)
        var status= dialog.findViewById<EditText>(R.id.status_Ed)
        status.setText(UserObj.status)
        var saveButton=dialog.findViewById<Button>(R.id.save_user_ed)
        saveButton.setOnClickListener {
            if (email.text.isNotEmpty()&& name.text.isNotEmpty()&&status.text.isNotEmpty()) {
                viewModel.updateUser(position,
                    UserUpdateRequest(
                        name.text.toString(),
                        email.text.toString(),
                        status.text.toString()
                    )
                )
                Toast.makeText(this, " user successfully updated", Toast.LENGTH_SHORT).show()
                viewModel.getUsers()
                dialog.dismiss()


            }
            else{
                Toast.makeText(this, "Please check user inputs", Toast.LENGTH_SHORT).show()
            }
        }
        var exitBtn=dialog.findViewById<Button>(R.id.exit_btn_ed)
        exitBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
        dialog.setCancelable(false)


    }
    fun showUser(userResponse: UserResponse){
        val dialog=Dialog(this)
        dialog.setContentView(R.layout.show_user)
        var email= dialog.findViewById<TextView>(R.id.email_show)
        var name= dialog.findViewById<TextView>(R.id.name_show)
        var gender= dialog.findViewById<TextView>(R.id.gender_show)
        var status= dialog.findViewById<TextView>(R.id.status_show)
        var id=dialog.findViewById<TextView>(R.id.id_show)
        "Email:->${userResponse.email}".also { email.text = it }
        "Name:->${userResponse.name}".also { name.text = it }
        "Gender:->${userResponse.gender}".also { gender.text = it }
        "Status:->${userResponse.status}".also { status.text = it }
        "Id:->${userResponse.id.toString()}".also { id.text = it }
        var exitBtn=dialog.findViewById<Button>(R.id.exit_btn_show)
        exitBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
        dialog.setCancelable(false)

    }
    fun dialog(){
        val dialog=Dialog(this)
        dialog.setContentView(R.layout.dialog)
        var email= dialog.findViewById<EditText>(R.id.email)
        var name= dialog.findViewById<EditText>(R.id.name)
        var gender= dialog.findViewById<EditText>(R.id.gender)
        var status= dialog.findViewById<EditText>(R.id.status)
        var saveButton=dialog.findViewById<Button>(R.id.save_user)
        saveButton.setOnClickListener {
            if (email.text.isNotEmpty()&& name.text.isNotEmpty()&&gender.text.isNotEmpty()&&status.text.isNotEmpty()) {
                viewModel.createUser(
                    UserRequest(
                        name.text.toString(),
                        gender.text.toString(),
                        email.text.toString(),
                        status.text.toString()
                    )
                )
                Toast.makeText(this, "new user successfully created", Toast.LENGTH_SHORT).show()

            }
            else{
                Toast.makeText(this, "Please check user inputs", Toast.LENGTH_SHORT).show()
            }
        }
        var exitBtn=dialog.findViewById<Button>(R.id.exit_btn)
        exitBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
        dialog.setCancelable(false)

    }

    override fun OnClicked(user: UserResponse) {
        showUser(user)
        Toast.makeText(this, "fwfwd", Toast.LENGTH_SHORT).show()
    }
}