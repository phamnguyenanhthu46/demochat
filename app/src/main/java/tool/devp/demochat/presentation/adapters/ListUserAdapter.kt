package tool.devp.demochat.presentation.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.item_user.view.*
import tool.devp.demochat.R
import tool.devp.demochat.common.DemoChatApp
import tool.devp.demochat.data.entities.UserEntity
import tool.devp.demochat.data.model.UserModel
import tool.devp.demochat.extension.invisible
import tool.devp.demochat.extension.loadImage
import tool.devp.demochat.extension.visible
import tool.devp.demochat.presentation.viewmodels.TopViewModel

class ListUserAdapter(context: Context, private val viewModel: TopViewModel, var users: ArrayList<UserModel>) : RecyclerView.Adapter<ListUserAdapter.UserViewHolder>(), Filterable {
    private var mData: MutableList<UserModel> = users
    private val layoutInflater = LayoutInflater.from(context)
    var me: UserModel? = null

    init {
        me = DemoChatApp.INTANCE.store.userInfo
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UserViewHolder = UserViewHolder(layoutInflater.inflate(R.layout.item_user, p0, false))

    override fun getItemCount(): Int = mData.count()

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindata(mData[position])
    }

    override fun getFilter(): Filter = UserFilter()

    private inner class UserFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence?): FilterResults {
            val result = FilterResults()
            if (charSequence.isNullOrEmpty()) {
                result.values = users
            } else {
                var filterFriend = users.filter {
                    it.userName.toLowerCase().contains(charSequence.toString().toLowerCase())
                }
                result.values = filterFriend
            }
            return result
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            mData = p1?.values as MutableList<UserModel>
            notifyDataSetChanged()
        }

    }

    fun addData(listUser: List<UserModel>) {
        users.addAll(listUser)
        /**
         * move my account to top
         */
        me?.let { user ->
            var myAcc = users.firstOrNull {
                it.email == user.email
            }
            myAcc?.let {
                users.remove(it)
                users.add(0, it)
            }
        }
        notifyDataSetChanged()
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindata(user: UserModel) {
            if (user.email == me!!.email) itemView.btnChat.invisible()
            else itemView.btnChat.visible()
            when (user.gender) {
                UserEntity.GENDER.MALE.value -> {
                    itemView.imgAvatar.loadImage(itemView.context, R.drawable.ic_boy)
                }
                else -> {
                    itemView.imgAvatar.loadImage(itemView.context, R.drawable.ic_girl)
                }
            }
            itemView.tvUserName.text = user.userName
            itemView.tvMail.text = user.email
            itemView.btnChat.setOnClickListener {
                viewModel.onChatClick.value = user
            }
        }
    }
}