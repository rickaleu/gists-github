package br.com.ricardo.gistsgithub.presentation.gistsdetail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.ricardo.gistsgithub.R
import br.com.ricardo.gistsgithub.data.model.Gist
import br.com.ricardo.gistsgithub.databinding.ActivityGistsDetailBinding
import br.com.ricardo.gistsgithub.presentation.gistsdetail.viewmodel.GistsDetailViewModel
import java.io.Serializable

class GistsDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_OBJ = "obj"

        fun <T> getStartIntent(context: Context, obj: T): Intent {
            return Intent(context, GistsDetailActivity::class.java).apply {
                putExtra(EXTRA_OBJ, obj as Serializable)
            }
        }
    }

    private lateinit var gistObject: Gist

    private val viewModel: GistsDetailViewModel by viewModels()
    private val binding: ActivityGistsDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_gists_detail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gistObject = intent.getSerializableExtra(EXTRA_OBJ) as Gist

        setSupportActionBar(binding.gistsDetailToolbar).apply {
            title = gistObject.owner.login
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this


        viewModel.getGistInfos(gistObject)
        GistsDetailViewModel.loadImage(binding.gitDetailImageAvatar, gistObject.owner.avatar)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}