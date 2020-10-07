package br.com.ricardo.gistsgithub.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavouriteDAO {

    @Insert
    suspend fun save(favourite: FavouriteEntity)

    @Query("SELECT * FROM favourite")
    suspend fun getFavourite() : List<FavouriteEntity>

    @Query("DELETE FROM favourite WHERE id = :id")
    suspend fun delete(id: Int)

}