package ru.fav.moneytrace.transaction.impl.data.remote.pojo.response

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("account")
    val account: AccountStateResponse? = null,
    @SerializedName("category")
    val category: CategoryResponse? = null,
    @SerializedName("amount")
    val amount: String? = null,
    @SerializedName("transactionDate")
    val transactionDate: String? = null,
    @SerializedName("comment")
    val comment: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
)