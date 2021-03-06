package cz.cuni.mff.nutritionalassistant.activity.overview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import cz.cuni.mff.nutritionalassistant.MainActivity;
import cz.cuni.mff.nutritionalassistant.R;
import cz.cuni.mff.nutritionalassistant.activity.BaseAbstractActivity;
import cz.cuni.mff.nutritionalassistant.databinding.ActivityRecipeOverviewBinding;
import cz.cuni.mff.nutritionalassistant.foodtypes.Recipe;

// TODO use GeneralOverviewUtil to work + implement specific init of ingredients + instructions

public class RecipeOverviewActivity extends BaseAbstractActivity {

    private ActivityRecipeOverviewBinding binding;

    private GeneralOverviewUtil overviewUtil;
    private Recipe recipe;
    private boolean metricUnits = true;

    Switch.OnCheckedChangeListener switchListener = (buttonView, isChecked) -> {
        metricUnits = !metricUnits;
        initIngredients();
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeOverviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recipe = (Recipe) getIntent().getSerializableExtra(MainActivity.EXTRA_SERIALIZABLE_FOOD);
        overviewUtil = new GeneralOverviewUtil(
                binding.textRecipeName, binding.thumbnail, binding.numberQuantity, binding.spinnerMeal, binding.textCaloriesValue,
                binding.textFatsValue, binding.textCarbsValue, binding.textProteinsValue, recipe
        );
        binding.switch1.setOnCheckedChangeListener(switchListener);

        overviewUtil.initialSetupGeneral(this);
        this.initialSetupSpecific();

        if (getIntent().getAction().equals(MainActivity.ACTION_EXAMINE_DETAILS)) {
            overviewUtil.examineDetailsSetupGeneral(
                    this, binding.ConstraintLayoutHeader, binding.textQuantity, binding.textMeal, binding.buttonAdd);
        } else { // equals(FoodViewHolder.ACTION_ADD_FOOD)
            overviewUtil.addFoodSetupGeneral();
        }
    }

    private void initialSetupSpecific() {
        initIngredients();
        initInstructions();
        initServings();
        initTimeToPrepare();
    }

    private void initIngredients() {
        int childCount = binding.LinearLayoutIngredients.getChildCount();
        binding.LinearLayoutIngredients.removeViews(1, childCount - 1);
        for (Recipe.Ingredient ingredient : recipe.getIngredients()) {
            TextView textViewIngredient = new TextView(this);
            if (metricUnits) {
                textViewIngredient.setText(
                        ingredient.getMetricAmount() + " " + ingredient.getMetricUnit() + " " + ingredient.getName());
            } else {
                textViewIngredient.setText(
                        ingredient.getUsAmount() + " " + ingredient.getUsUnit() + " " + ingredient.getName());
            }
            binding.LinearLayoutIngredients.addView(textViewIngredient);
        }
    }

    private void initInstructions() {
        TextView textViewInstructions = new TextView(this);
        textViewInstructions.setText(recipe.getInstructions());
        binding.LinearLayoutInstructions.addView(textViewInstructions);
    }

    private void initServings() {
        binding.textServingsVariable.setText(String.valueOf(recipe.getNumberOfServings()));
    }

    private void initTimeToPrepare() {
        binding.textPreparationTimeVariable.setText(String.valueOf(recipe.getReadyInMinutes()));
    }

    public void onAddButtonClick(View view) {
        overviewUtil.onAddButtonClickGeneral(this);
        //setResult(RESULT_OK);
        //finish();
    }
}
