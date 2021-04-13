package lul.myapplication.models

import com.google.gson.annotations.SerializedName

data class FilmePesquisaResponse(
    @SerializedName("results") val results: MutableList<Filme>
)