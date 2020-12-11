package cz.cuni.mff.nutritionalassistant.guidancebot;

import android.content.Context;

import java.util.HashMap;
import java.util.List;

import cz.cuni.mff.nutritionalassistant.Constants;
import cz.cuni.mff.nutritionalassistant.foodtypes.Food;
import cz.cuni.mff.nutritionalassistant.foodtypes.FoodAdapterType;
import cz.cuni.mff.nutritionalassistant.foodtypes.Recipe;

public final class Brain {
    private DataSupplier dataSupplier;
    private Generator generator;
    private Mathematics mathematics;

    private static final Brain INSTANCE = new Brain();

    private Brain() {
        dataSupplier = new DataSupplier();
        generator = new Generator();
        mathematics = new Mathematics();
    }

    public static Brain getInstance() {
        return INSTANCE;
    }

    // TODO local
    public List<FoodAdapterType> requestFoodAdapterTypeData(String query, int foodTypeFilter, Context context) {
        return dataSupplier.localDBrequest(query, context);
    }

    // TODO local
    public Food requestFoodDetailedInfo(FoodAdapterType foodAdapterType, Context context) {
        return dataSupplier.localDetailedInfo(foodAdapterType, context);
    }


    // To be used API
    public List<FoodAdapterType> requestFoodAdapterTypeData(
            String query, int foodTypeFilter, HashMap<Integer, Integer> nutritionFilterTable) {
        return null;
    }

    public Food requestFoodDetailedInfo(String detailedInfoURL) {
        return null;
    }

    public List<FoodAdapterType> requestSwapFoodAdapterTypeData(
            Food foodToSwap, int foodTypeFilter, int restaurantRadius) {
        return null;
    }

    // TODO delete context, only local
    // list of meals (index of list) to be regenerated (false meaning not checked -> to be regenerated)
    public List<Food> requestRegenerate(List<Boolean> generatedFoodsFlags, Context context) {
        return generator.requestDummyGeneratedFoods(generatedFoodsFlags, context);
    }

    public List<Float> requestNHConstraintsCalculation(
            Constants.Sex sex, int height, int weight, int age, Constants.Lifestyle lifestyle, Constants.Goal goal) {
        return null;
    }

    //requestDialog - after adding non-generated food
}