package br.com.ricardo.gistsgithub.presentation.gistsdetail.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ricardo.gistsgithub.R
import br.com.ricardo.gistsgithub.data.model.Gist
import com.bumptech.glide.Glide

class GistsDetailViewModel : ViewModel() {

    private val _gist = MutableLiveData<Gist>()
    val gist: LiveData<Gist>
        get() = _gist


    fun getGistInfos(gist: Gist) {
        _gist.value = gist
    }


    companion object {
        @JvmStatic
        @BindingAdapter("profileImage")
        fun loadImage(image: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(image.context)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(image)
            }
        }
    }

}