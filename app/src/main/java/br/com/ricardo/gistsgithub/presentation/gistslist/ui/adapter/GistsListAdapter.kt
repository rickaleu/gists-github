package br.com.ricardo.gistsgithub.presentation.gistslist.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ricardo.gistsgithub.R
import br.com.ricardo.gistsgithub.data.model.Gist
import br.com.ricardo.gistsgithub.utils.ItemGistsListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_gists.view.*

class GistsListAdapter(private val gistList: List<Gist>,
                       private var favouriteGistsListener: ItemGistsListener,
                       private val onClickListener: ((gist: Gist) -> Unit))
    : RecyclerView.Adapter<GistsListAdapter.GistsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GistsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gists, parent, false)
        return GistsViewHolder(view, favouriteGistsListener, onClickListener)
    }

    override fun onBindViewHolder(holder: GistsViewHolder, position: Int) {
        holder.bindView(gistList[position])
    }

    override fun getItemCount() = gistList.count()

    class GistsViewHolder(private val itemView: View,
                          private var favouriteGistsListener: ItemGistsListener,
                          private val onClickListener: (gist: Gist) -> Unit)
        : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.item_image
        private val name = itemView.item_text_name
        private val favouriteIcon = itemView.item_icon_favourite

        fun bindView(gist: Gist) {

            Glide
                .with(itemView.context)
                .load(gist.owner.avatar)
                .into(image)

            name.text = gist.owner.login

            itemView.setOnClickListener {
                onClickListener.invoke(gist)
            }

            favouriteIcon.setOnClickListener {
                favouriteGistsListener.favouriteGist(gist)
            }
        }
    }
}