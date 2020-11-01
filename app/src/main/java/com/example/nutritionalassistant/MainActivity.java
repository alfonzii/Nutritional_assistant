package com.example.nutritionalassistant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.nutritionalassistant.databinding.ActivityMainBinding;

import static com.example.nutritionalassistant.Constants.FOOD_REQUEST;
import static com.example.nutritionalassistant.Constants.PARAMETERS_REQUEST;
import static com.example.nutritionalassistant.Constants.RESULT_AUTOMATIC_FAILURE;
import static com.example.nutritionalassistant.Constants.VALUES_REQUEST;

public class MainActivity extends AppCompatActivity {
    //Reference to singleton object
    private DataHolder data = DataHolder.getInstance();

    //View binding object
    private ActivityMainBinding binding;

    // Shared preferences object
    private SharedPreferences mPreferences;

    // Name of shared preferences file
    private final String sharedPrefFile =
            "com.example.nutritionalassistant";

    private int counter = 0;


    private void refreshValues() {
        binding.content.mainCaloriesValue.setText(data.getCalsCurrent() + "/" + data.getCalsGoal());
        binding.content.mainFatsValue.setText(data.getFatsCurrent() + "/" + data.getFatsGoal());
        binding.content.mainCarbsValue.setText(data.getCarbsCurrent() + "/" + data.getCarbsGoal());
        binding.content.mainProteinsValue.setText(data.getProtsCurrent() + "/" + data.getProtsGoal());
    }

    @SuppressLint("SetTextI18n") //suppress setText warning
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FoodAddingActivity.class);
                startActivityForResult(intent, FOOD_REQUEST);
            }
        });

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        data.setCalsGoal(mPreferences.getInt("CALSGOAL", data.getCalsGoal()));
        data.setFatsGoal(mPreferences.getInt("FATSGOAL", data.getFatsGoal()));
        data.setCarbsGoal(mPreferences.getInt("CARBSGOAL", data.getCarbsGoal()));
        data.setProtsGoal(mPreferences.getInt("PROTSGOAL", data.getProtsGoal()));
        data.setCalsCurrent(mPreferences.getInt("CALSCURRENT", data.getCalsCurrent()));
        data.setFatsCurrent(mPreferences.getInt("FATSCURRENT", data.getFatsCurrent()));
        data.setCarbsCurrent(mPreferences.getInt("CARBSCURRENT", data.getCalsCurrent()));
        data.setProtsCurrent(mPreferences.getInt("PROTSCURRENT", data.getProtsCurrent()));

        data.setAge(mPreferences.getInt("AGE", data.getAge()));
        data.setWeight(mPreferences.getInt("WEIGHT", data.getWeight()));
        data.setHeight(mPreferences.getInt("HEIGHT", data.getHeight()));
        data.setSex(data.convertSex(mPreferences.getInt("SEX", data.convertSex(data.getSex()))));

        refreshValues();
    }

    /**
     * Callback for activity pause.  Shared preferences are saved here.
     */
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt("CALSGOAL", data.getCalsGoal());
        preferencesEditor.putInt("FATSGOAL", data.getFatsGoal());
        preferencesEditor.putInt("CARBSGOAL", data.getCarbsGoal());
        preferencesEditor.putInt("PROTSGOAL", data.getProtsGoal());
        preferencesEditor.putInt("CALSCURRENT", data.getCalsCurrent());
        preferencesEditor.putInt("FATSCURRENT", data.getFatsCurrent());
        preferencesEditor.putInt("CARBSCURRENT", data.getCarbsCurrent());
        preferencesEditor.putInt("PROTSCURRENT", data.getProtsCurrent());

        preferencesEditor.putInt("AGE", data.getAge());
        preferencesEditor.putInt("WEIGHT", data.getWeight());
        preferencesEditor.putInt("HEIGHT", data.getHeight());
        preferencesEditor.putInt("SEX", data.convertSex(data.getSex()));

        preferencesEditor.apply();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_nutSettings:
                Intent intent = new Intent(this, NHSetActivity.class);
                startActivityForResult(intent, VALUES_REQUEST);
                break;

            case R.id.action_userParameters:
                Intent uParIntent = new Intent(this, UserParametersActivity.class);
                startActivityForResult(uParIntent, PARAMETERS_REQUEST);
                break;

            case R.id.action_resetCurrent:
                data.setCalsCurrent(0);
                data.setFatsCurrent(0);
                data.setCarbsCurrent(0);
                data.setProtsCurrent(0);
                refreshValues();
                break;

            case R.id.action_foodDatabase:
                Intent databaseIntent = new Intent(this, DatabaseActivity.class);
                startActivity(databaseIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n") //suppress setText warning
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case VALUES_REQUEST:
                if (resultCode == RESULT_OK) {
                    //tu bolo pridavanie do values
                    refreshValues();
                } else if (resultCode == RESULT_AUTOMATIC_FAILURE) {
                    Intent uParIntent = new Intent(this, UserParametersActivity.class);
                    startActivityForResult(uParIntent, PARAMETERS_REQUEST);
                }
                break;

            case FOOD_REQUEST:
                if (resultCode == RESULT_OK) {
                    //tu bolo pridavanie do foodvalues
                    refreshValues();
                }
                break;

            case PARAMETERS_REQUEST:
                if (resultCode == RESULT_OK) {
                    /*int[] userParams = data.getIntArrayExtra(UserParametersActivity.EXTRA_PARAMETERS);
                    sex = userParams[0];
                    age = userParams[1];
                    weight = userParams[2];
                    height = userParams[3];*/
                }
                break;
        }
    }

    public void exampleClick(View view) {
        TextView tv = new TextView(this);
        tv.setText("badoo" + counter);
        binding.content.linearLayout.addView(tv, 2+counter);
        counter++;
    }
}


