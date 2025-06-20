package ru.fav.moneytrace.network.pojo.response

import com.google.gson.annotations.SerializedName

data class AccountDetailsResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("balance")
    val balance: String? = null,
    @SerializedName("currency")
    val currency: String? = null,
    @SerializedName("incomeStats")
    val incomeStats: List<CategoryDetailsResponse>? = null,
    @SerializedName("expenseStats")
    val expenseStats: List<CategoryDetailsResponse>? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
)