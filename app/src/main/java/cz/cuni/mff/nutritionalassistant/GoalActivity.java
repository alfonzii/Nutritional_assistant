package cz.cuni.mff.nutritionalassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class GoalActivity extends AppCompatActivity {
    // Reference to singleton object
    private DataHolder data = DataHolder.getInstance();

    private float calculateBasal() {
        float rmr = 10 * data.getWeight() + 6.25f * data.getHeight() - 5 * data.getAge();
        if (data.getSex() == Constants.Sex.MALE)
            rmr += 5;
        else
            rmr -= 161;

        switch (data.getLifestyle()) {
            case SEDENTARY:
                rmr *= 1.2f;
                break;

            case MILD_ACTIVITY:
                rmr *= 1.375f;
                break;

            case MEDIUM_ACTIVITY:
                rmr *= 1.55f;
                break;

            case HIGH_ACTIVITY:
                rmr *= 1.8f;
                break;
        }

        return rmr;
    }

    private void calculateMacronutrients(float fatsVar, float carbsVar, float protsVar) {
        int carbs, fats, proteins;
        fats = Math.round((float) data.getCalsGoal() * fatsVar);
        carbs = Math.round((float) data.getCalsGoal() * carbsVar);
        proteins = Math.round((float) data.getCalsGoal() * protsVar);

        fats /= 9;
        carbs /= 4;
        proteins /= 4;

        data.setFatsGoal(fats);
        data.setCarbsGoal(carbs);
        data.setProtsGoal(proteins);
    }

    private void calculateMacronutrients() {
        if (data.getGoal() == Constants.Goal.GAIN)
            //GAIN - 20%fats, 50%carbs, 30%proteins
            calculateMacronutrients(0.2f, 0.5f, 0.3f);

        else if (data.getGoal() == Constants.Goal.MAINTAIN)
            //MAINTAIN - 35%fats, 40%carbs, 25%protein
            calculateMacronutrients(0.35f, 0.4f, 0.25f);

        else if (data.getGoal() == Constants.Goal.LOSE)
            //LOSE - 40%fats, 25%carbs, 35%protein
            calculateMacronutrients(0.4f, 0.25f, 0.35f);
    }

    private void endAcivity() {
        calculateMacronutrients();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
    }

    public void gainClick(View view) {
        data.setCalsGoal(Math.round(calculateBasal()) + 400);
        data.setGoal(Constants.Goal.GAIN);
        endAcivity();
    }

    public void loseClick(View view) {
        data.setCalsGoal(Math.round(calculateBasal()) - 400);
        data.setGoal(Constants.Goal.LOSE);
        endAcivity();
    }

    public void maintainClick(View view) {
        data.setCalsGoal(Math.round(calculateBasal()));
        data.setGoal(Constants.Goal.MAINTAIN);
        endAcivity();
    }
}