package lathigara.harsh.doitcart.ui.home.retrofit.data.classes


import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("iso_639_1")
    val iso6391: String,
    val name: String
)