package cz.cuni.mff.nutritionalassistant;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.nutritionalassistant.foodtypes.Food;
import cz.cuni.mff.nutritionalassistant.foodtypes.Recipe;
import lombok.Getter;
import lombok.Setter;

import static cz.cuni.mff.nutritionalassistant.Constants.Lifestyle;
import static cz.cuni.mff.nutritionalassistant.Constants.Goal;
import static cz.cuni.mff.nutritionalassistant.Constants.Sex;

//SINGLETON CLASS
@Getter
@Setter
public final class DataHolder {
    private static final DataHolder INSTANCE = new DataHolder();

    // We have four meals, that's why we make list of 4 lists.
    private DataHolder() {
        generatedFoods = new ArrayList<>();
        generatedFoods.add(new Pair<Food, Boolean>(new Recipe(), false));
        generatedFoods.add(new Pair<Food, Boolean>(new Recipe(), false));
        generatedFoods.add(new Pair<Food, Boolean>(new Recipe(), false));
        generatedFoods.add(new Pair<Food, Boolean>(new Recipe(), false));

        userAddedFoods = new ArrayList<>();
        userAddedFoods.add(new ArrayList<Food>());
        userAddedFoods.add(new ArrayList<Food>());
        userAddedFoods.add(new ArrayList<Food>());
        userAddedFoods.add(new ArrayList<Food>());
    }

    public static DataHolder getInstance() {
        return INSTANCE;
    }

    private Sex sex;
    private int height = 0;
    private int weight = 0;
    private int age = 0;

    private Lifestyle lifestyle;
    private Goal goal;

    // TODO mali by to byt floaty, a zobrazovacie views by sa mali starat o ich konverziu
    private float caloriesGoal = 0;
    private float fatsGoal = 0;
    private float carbohydratesGoal = 0;
    private float proteinsGoal = 0;
    private float caloriesCurrent = 0;
    private float fatsCurrent = 0;
    private float carbohydratesCurrent = 0;
    private float proteinsCurrent = 0;

    // Generated food + flag, if it is checked (eaten)
    private List<Pair<Food, Boolean>> generatedFoods;
    private List<List<Food>> userAddedFoods;
    private int lastAddedMeal;

    int convertSex(Sex sex) {
        if (sex == Sex.MALE)
            return 0;
        else
            return 1;
    }

    Sex convertSex(int i) {
        if (i == 0)
            return Sex.MALE;
        else if (i == 1)
            return Sex.FEMALE;
        else
            throw new IllegalArgumentException();
    }

    Food getLastEatenFood() {
        int lastFood = userAddedFoods.get(lastAddedMeal).size() - 1;
        return userAddedFoods.get(lastAddedMeal).get(lastFood);
    }

    public void addFoodToCurrentNH(Food food) {
        caloriesCurrent += food.getCalories();
        fatsCurrent += food.getFats();
        carbohydratesCurrent += food.getCarbohydrates();
        proteinsCurrent += food.getProteins();
    }

    void subtractFoodFromCurrentNH(Food food) {
        caloriesCurrent -= food.getCalories();
        fatsCurrent -= food.getFats();
        carbohydratesCurrent -= food.getCarbohydrates();
        proteinsCurrent -= food.getProteins();
    }
}
