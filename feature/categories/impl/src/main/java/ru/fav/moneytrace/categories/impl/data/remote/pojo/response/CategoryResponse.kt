package ru.fav.moneytrace.categories.impl.data.remote.pojo.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("emoji")
    val emoji: String? = null,
    @SerializedName("isIncome")
    val isIncome: Boolean? = null,
)