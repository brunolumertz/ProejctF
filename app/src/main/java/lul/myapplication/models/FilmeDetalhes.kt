package lul.myapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "FilmeDetalhes")
data class FilmeDetalhes(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val tittle: String,
    @SerializedName("backdrop_path")
    val poster: String,
    @SerializedName("release_date")
    val release: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("vote_average")
    val vote: String,

    )

