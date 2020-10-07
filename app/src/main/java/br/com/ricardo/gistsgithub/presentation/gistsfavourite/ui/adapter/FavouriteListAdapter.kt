package br.com.ricardo.gistsgithub.presentation.gistsfavourite.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ricardo.gistsgithub.R
import br.com.ricardo.gistsgithub.data.local.FavouriteEntity
import br.com.ricardo.gistsgithub.utils.ItemGistsListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_gists.view.*

class FavouriteListAdapter(private val favouriteList: List<FavouriteEntity>,
                           private var favouriteGistListener: ItemGistsListener,
                           private val onClickListener: ((fav: FavouriteEntity) -> Unit))
    : RecyclerView.Adapter<FavouriteListAdapter.FavouriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gists, parent, false)
        return FavouriteViewHolder(view, favouriteGistListener, onClickListener)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bindView(favouriteList[position])
    }

    override fun getItemCount() = favouriteList.count()

    class FavouriteViewHolder(private val itemView: View,
                              private var favouriteGistListener: ItemGistsListener,
                              private val onClickListener: (fav: FavouriteEntity) -> Unit)
        : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.item_image
        private val name = itemView.item_text_name
        private val favouriteIcon = itemView.item_icon_favourite

        fun bindView(fav: FavouriteEntity) {

            Glide
                .with(itemView.context)
                .load(fav.avatar)
                .into(image)

            name.text = fav.login

            itemView.setOnClickListener {
                onClickListener.invoke(fav)
            }

            favouriteIcon.setOnClickListener {
                favouriteGistListener.getIdFav(fav.id)
            }
        }
    }
}