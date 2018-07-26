package assem.com.nanaodegree.sandwich_club_udacity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import assem.com.nanaodegree.sandwich_club_udacity.model.Sandwich;
import assem.com.nanaodegree.sandwich_club_udacity.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    // TAG for debugging
    private static final String TAG = DetailActivity.class.getSimpleName();
    // Vars
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich sandwich;
    // Views
    private TextView mPlaceOfOrigin;
    private TextView mDescription;
    private TextView mIngredients;
    private TextView mAlsoKnownAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mPlaceOfOrigin = findViewById(R.id.origin_tv);
        mDescription = findViewById(R.id.description_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        mAlsoKnownAs = findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Log.d(TAG, "Sandwich obj img : " + sandwich.getImage());
        // Image may not shown because it need good connection or it's not available yet on wikimedia
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        mDescription.setText(sandwich.getDescription());

        StringBuilder str1 = new StringBuilder();

        for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
            //if is included to add comma.
            if (i < sandwich.getAlsoKnownAs().size()) {
                str1.append(sandwich.getAlsoKnownAs().get(i) + "," + " ");
            } else {
                str1.append(sandwich.getAlsoKnownAs().get(i) + "");
            }
        }
        mAlsoKnownAs.setText(str1);

        StringBuilder str2 = new StringBuilder();

        for (int i = 0; i < sandwich.getIngredients().size(); i++) {
            //if is included to add comma.
            if (i < sandwich.getIngredients().size()) {
                str2.append(sandwich.getIngredients().get(i) + "," + " ");
            } else {
                str2.append(sandwich.getIngredients().get(i) + "");
            }
        }
        mIngredients.setText(str2);
    }
}
