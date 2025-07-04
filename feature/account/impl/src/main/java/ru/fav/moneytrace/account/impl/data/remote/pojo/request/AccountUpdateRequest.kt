package ru.fav.moneytrace.account.impl.data.remote.pojo.request

import com.google.gson.annotations.SerializedName

data class AccountUpdateRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("balance")
    val balance: String,
    @SerializedName("currency")
    val currency: String
)