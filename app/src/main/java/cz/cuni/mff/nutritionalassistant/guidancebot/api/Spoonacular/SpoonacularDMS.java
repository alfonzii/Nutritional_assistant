package cz.cuni.mff.nutritionalassistant.guidancebot.api.Spoonacular;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.cuni.mff.nutritionalassistant.foodtypes.FoodAdapterType;
import cz.cuni.mff.nutritionalassistant.foodtypes.RecipeAdapterType;
import cz.cuni.mff.nutritionalassistant.guidancebot.api.AdapterDataCallback;
import cz.cuni.mff.nutritionalassistant.guidancebot.api.DetailedFoodCallback;
import cz.cuni.mff.nutritionalassistant.guidancebot.api.PojoConverter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpoonacularDMS {

    private Retrofit retrofit;
    private SpoonacularApi spoonacularApi;
    private String apiKey = "54483141f36447f38d9451d5ea8236cd";

    public SpoonacularDMS() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        spoonacularApi = retrofit.create(SpoonacularApi.class);
    }

    public void getRecipeDetails(FoodAdapterType recipeAdapterType, DetailedFoodCallback callback) {
        RecipeAdapterType recipe = (RecipeAdapterType) recipeAdapterType;

        Call<SpoonacularDetailedRecipePojo> call = spoonacularApi.detailsRecipe(recipe.getId(),true, apiKey);

        call.enqueue(new Callback<SpoonacularDetailedRecipePojo>() {
            @Override
            public void onResponse(Call<SpoonacularDetailedRecipePojo> call, Response<SpoonacularDetailedRecipePojo> response) {
                if (!response.isSuccessful()) {
                    Log.d(SpoonacularDMS.class.getName(), "Code: " + response.code());
                    return;
                }

                if (callback != null) {
                    callback.onSuccess(PojoConverter.Spoonacular.fromSpoonacularDetailedRecipePojo(response.body()));
                }
            }

            @Override
            public void onFailure(Call<SpoonacularDetailedRecipePojo> call, Throwable t) {
                Log.d(SpoonacularDMS.class.getName(), t.getMessage());
                if (callback != null) {
                    callback.onFail(t);
                }
            }
        });
    }

    public void listRecipes(String query, HashMap<Integer, Integer> nutritionFilterTable, AdapterDataCallback callback) {
        Call<SpoonacularAdapterFullReposnsePojo> call;

        //if(nutritionFilterTable.isEmpty()) {
        call = spoonacularApi.listRecipes(query, true, 0, 50, apiKey);
        //}

        call.enqueue(new Callback<SpoonacularAdapterFullReposnsePojo>() {
            @Override
            public void onResponse(Call<SpoonacularAdapterFullReposnsePojo> call, Response<SpoonacularAdapterFullReposnsePojo> response) {
                if (!response.isSuccessful()) {
                    Log.d(SpoonacularDMS.class.getName(), "Code: " + response.code());
                    return;
                }
                final List<FoodAdapterType> correctResponse = new ArrayList<>();


                if (response.body() != null) {
                    correctResponse.addAll(PojoConverter.Spoonacular.fromSpoonacularPojoList(response.body().getResults()));
                }


                if (callback != null) {
                    callback.onSuccess(correctResponse);
                }
            }

            @Override
            public void onFailure(Call<SpoonacularAdapterFullReposnsePojo> call, Throwable t) {
                Log.d(SpoonacularDMS.class.getName(), t.getMessage());
                if (callback != null) {
                    callback.onFail(t);
                }
            }
        });
    }
}