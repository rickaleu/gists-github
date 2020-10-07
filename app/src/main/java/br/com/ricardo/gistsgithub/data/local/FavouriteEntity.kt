package br.com.ricardo.gistsgithub.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.FavouriteParams
import java.io.Serializable

@Entity(tableName = "favourite")
data class FavouriteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val login: String,
    val avatar: String
) : Serializable

fun FavouriteParams.toFavouriteEntity() : FavouriteEntity {
    return with(this) {
        FavouriteEntity(
            login = this.login,
            avatar = this.avatar
        )
    }
}