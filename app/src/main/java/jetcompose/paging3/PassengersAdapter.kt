package jetcompose.paging3

import android.annotation.SuppressLint
import android.provider.Contacts
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.model.main.PhotoListModel
import jetcompose.paging3.databinding.ItemPassengerBinding

class PassengersAdapter:PagingDataAdapter<PhotoListModel, PassengersAdapter.PassengersViewHolder>(PassengersComparator) {

    override fun onBindViewHolder(holder: PassengersAdapter.PassengersViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bindPassenger(it)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PassengersAdapter.PassengersViewHolder {
        return PassengersViewHolder(
            ItemPassengerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class PassengersViewHolder(private val binding: ItemPassengerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindPassenger(item: PhotoListModel) = with(binding) {
//            imageViewAirlinesLogo.loadImage(item.airline!!.logo!!)
  //          textViewHeadquarters.text = item.airline!!.headQuaters
            textViewNameWithTrips.text = "${item.title}, ${item.title} Trips"
        }
    }

    object PassengersComparator : DiffUtil.ItemCallback<PhotoListModel>() {
        override fun areItemsTheSame(oldItem: PhotoListModel, newItem: PhotoListModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoListModel, newItem: PhotoListModel): Boolean {
            return oldItem == newItem
        }
    }

}
