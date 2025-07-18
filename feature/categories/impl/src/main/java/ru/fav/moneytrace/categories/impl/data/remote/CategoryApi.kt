package ru.fav.moneytrace.categories.impl.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import ru.fav.moneytrace.categories.impl.data.remote.pojo.response.CategoryResponse

interface CategoryApi {

    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(
        @Path("isIncome") isIncome: Boolean,
    ): List<CategoryResponse>?
}
