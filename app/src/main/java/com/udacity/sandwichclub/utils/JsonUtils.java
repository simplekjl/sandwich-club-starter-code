package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichJson = new JSONObject(json);
            List<String> namesList = new ArrayList<>();
            JSONArray names = sandwichJson.getJSONObject("name").getJSONArray("alsoKnownAs");
            for(int i=0 ; i<names.length(); i++){
                namesList.add(names.getString(i));
            }
            sandwich.setMainName(sandwichJson.getJSONObject("name").getString("mainName"));
            sandwich.setAlsoKnownAs(namesList);
            sandwich.setImage(sandwichJson.getString("image"));
            List<String> ingredientsList = new ArrayList<>();
            JSONArray ingredients = sandwichJson.getJSONArray("ingredients");
            for(int i=0 ; i<ingredients.length(); i++){
                ingredientsList.add(ingredients.getString(i));
            }
            sandwich.setIngredients(ingredientsList);
            sandwich.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichJson.getString("description"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;

    }
}
