package lul.myapplication.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//@Parcelize
data class FilmeResponse(
    @SerializedName("results")
    val filmes : List<Filme>
)

//) : Parcelable{
//    constructor() : this(mutableListOf())
//}