package assem.com.nanaodegree.sandwich_club_udacity.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import assem.com.nanaodegree.sandwich_club_udacity.model.Sandwich;

public class JsonUtils {

    // TAG for debugging
    private static final String TAG = JsonUtils.class.getSimpleName();
    // Vars
    private static final String NAME_OBJ = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";
    private static final String infoNotAvailable = "N/A";


    public static Sandwich parseSandwichJson(String json) {
        JSONObject jsonObject;
        Sandwich sandwich = null;

        try {
            // Parsing name object
            jsonObject = new JSONObject(json);
            Log.d(TAG, "parseSandwichJson called : jsonObject ::  " + jsonObject);

            JSONObject nameObject = jsonObject.getJSONObject(NAME_OBJ);
            String mainName = nameObject.getString(MAIN_NAME);

            // Parsing alsoKnownAs Json Array
            JSONArray alsoKnownAsJsonArray = nameObject.getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownAsList = new ArrayList<>();
            if (alsoKnownAsJsonArray.length() != 0) {
                for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAsJsonArray.optString(i));
                }
            } else {
                alsoKnownAsList.add(infoNotAvailable);
            }

            // Parsing the remaining items
            String placeOfOrigin = jsonObject.getString(PLACE_OF_ORIGIN);
            String description = jsonObject.getString(DESCRIPTION);
            String image = jsonObject.getString(IMAGE);

            // Parsing ingredients Json Array
            JSONArray ingredientsJsonArray = jsonObject.getJSONArray(INGREDIENTS);
            List<String> ingredientsList = new ArrayList<>();
            if (ingredientsJsonArray.length() != 0) {
                for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                    ingredientsList.add(ingredientsJsonArray.optString(i));
                }
            } else {
                ingredientsList.add(infoNotAvailable);
            }

            sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
        } catch (
                JSONException e)

        {
            e.printStackTrace();
        }
        return sandwich;
    }
}
