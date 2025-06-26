package ru.fav.moneytrace.account.impl.data.remote.pojo.response

import com.google.gson.annotations.SerializedName

class AccountResponse (
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("balance")
    val balance: String? = null,
    @SerializedName("currency")
    val currency: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
)