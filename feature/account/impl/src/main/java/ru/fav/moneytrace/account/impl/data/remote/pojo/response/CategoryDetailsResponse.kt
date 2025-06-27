package ru.fav.moneytrace.account.impl.data.remote.pojo.response

import com.google.gson.annotations.SerializedName

data class CategoryDetailsResponse(
    @SerializedName("categoryId")
    val categoryId: Int? = null,
    @SerializedName("categoryName")
    val categoryName: String? = null,
    @SerializedName("emoji")
    val emoji: String? = null,
    @SerializedName("amount")
    val amount: String? = null,
)