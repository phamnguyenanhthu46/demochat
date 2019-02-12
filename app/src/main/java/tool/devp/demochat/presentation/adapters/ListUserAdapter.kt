package tool.devp.demochat.presentation.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_user.view.*
import tool.devp.demochat.R
import tool.devp.demochat.data.entities.UserEntity
import tool.devp.demochat.data.model.UserModel

class ListUserAdapter(context: Context, var users: ArrayList<UserModel>) : RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UserViewHolder = UserViewHolder(layoutInflater.inflate(R.layout.item_user, p0, false))

    override fun getItemCount(): Int = users.count()

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindata(users[position])
    }

    fun addData(listUser: List<UserModel>){
        users.addAll(listUser)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         fun bindata(user: UserModel){
            when(user.gender){
                UserEntity.GENDER.MALE.value -> {

                }
                else -> {}
            }
             itemView.tvUserName.text = user.userName
             itemView.tvMail.text = user.email
        }
    }
}