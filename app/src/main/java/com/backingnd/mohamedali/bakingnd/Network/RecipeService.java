package com.backingnd.mohamedali.bakingnd.Network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<ResponseBody> getRecipeJsonString();
}
