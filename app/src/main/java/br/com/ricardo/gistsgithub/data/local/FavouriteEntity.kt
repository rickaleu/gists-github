package br.com.ricardo.gistsgithub.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.ricardo.gistsgithub.data.model.Favourite
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.FavouriteParams

@Entity(tableName = "favourite")
data class FavouriteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val login: String,
    val avatar: String
)

fun FavouriteParams.toFavouriteEntity() : FavouriteEntity {
    return with(this) {
        FavouriteEntity(
            login = this.login,
            avatar = this.avatar
        )
    }
}

//fun FavouriteEntity.toFavourite() : Favourite {
//    return Favourite(
//        id = this.id,
//        login = this.login,
//        avatar = this.avatar
//    )
//}