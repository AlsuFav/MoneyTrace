package ru.fav.moneytrace.transaction.impl.data.remote.pojo.response

import com.google.gson.annotations.SerializedName

data class AccountStateResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("balance")
    val balance: String? = null,
    @SerializedName("currency")
    val currency: String? = null,
)