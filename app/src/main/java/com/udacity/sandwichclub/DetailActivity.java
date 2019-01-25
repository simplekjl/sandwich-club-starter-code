package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private int position = -1;
    private Sandwich sandwich = null;
    private TextView descriptionTv;
    private ImageView ingredientsIv;
    private TextView originTv;
    private TextView alsoKnownTv;
    private TextView ingredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        validateExtras();
        ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_label);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_label);
        descriptionTv = findViewById(R.id.description_label);

        if(savedInstanceState != null){
            sandwich = savedInstanceState.getParcelable("sandwich");
        }else{
            getSandwichDetails();
        }
        if(sandwich != null) {
            populateUI(sandwich);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void getSandwichDetails() {
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
        }
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());
        //sandwich details
        originTv.setText(sandwich.getPlaceOfOrigin());
        for(int i = 0; i< sandwich.getAlsoKnownAs().size(); i ++){
            String name = sandwich.getAlsoKnownAs().get(i);
            if(sandwich.getAlsoKnownAs().size()-1 == i ){
                alsoKnownTv.append(name + ".");
            }else{
                alsoKnownTv.append(name + ",");
            }
        }

        for (int z=0 ; z<sandwich.getIngredients().size() ; z++){
            String ingredient = sandwich.getIngredients().get(z);
            if (sandwich.getIngredients().size() -1 == z){
                ingredientsTv.append(ingredient + ".");
            }else{
                ingredientsTv.append(ingredient + ",");
            }
        }
        descriptionTv.setText(sandwich.getDescription());
    }

    private void validateExtras() {

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState!= null)
        {
            sandwich = savedInstanceState.getParcelable("sandwich");
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (sandwich != null) {
            outState.putParcelable("sandwich", sandwich);
        }
        super.onSaveInstanceState(outState);

    }
}
