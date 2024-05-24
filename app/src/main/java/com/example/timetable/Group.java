package com.example.timetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Group extends AppCompatActivity {
    int secId;
    int facultyId;
    int departmentId;
    int specId;
    int lvlId;
    private LinearLayout buttonContainer;
    private List<GroupApi> groupList;
    private RequestQueue requestQueue;
    private String language = "fr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);



        secId = getIntent().getIntExtra("secId", -1); // Retrieve departmentId from intent
        facultyId = getIntent().getIntExtra("facultyId", -1); // Retrieve departmentId from intent
        departmentId = getIntent().getIntExtra("departmentId", -1); // Retrieve departmentId from intent
        specId = getIntent().getIntExtra("specId", -1); // Retrieve departmentId from intent
        lvlId = getIntent().getIntExtra("lvlId", -1); // Retrieve departmentId from intent
        requestQueue = Volley.newRequestQueue(this);
        groupList = new ArrayList<>();
        buttonContainer = findViewById(R.id.buttonContainer);
        language = getIntent().getStringExtra("lang");
        if (language == null) {
            language = "fr";
        }
        FetchGroup();

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);
        topAppBar.showOverflowMenu();
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.topbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.favorite) {

            // Do something
            return true;
        }
        if (id == R.id.search) {

            // Do something
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void FetchGroup() {
        String url = "https://num.univ-biskra.dz/psp/pspapi/group?section="+secId+"&semester=2&key=appmob";
        final LoadingDialog loadingDialog = new LoadingDialog(Group.this);
        loadingDialog.startLoadingDialog();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Set<String> existingSectionNames = new HashSet<>(); // HashSet to store existing section names
                        loadingDialog.dissmisdialog();

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            GroupApi group = new GroupApi(
                                    jsonObject.getInt("groupe_id"),
                                    jsonObject.getString("groupe_name"),
                                    jsonObject.getInt("sectionn_id"),
                                    jsonObject.getString("Abrev_fr"),
                                    jsonObject.getString("Abrev_ar"),
                                    jsonObject.getString("capacite_grp"),
                                    jsonObject.getString("active")
                            );



                            if (!existingSectionNames.contains(group.getGroupName())) {
                                // If not, add the section to the sectionList and the existingSectionNames set
                                groupList.add(group);
                                existingSectionNames.add(group.getGroupName());
                            }
                            Log.d("LevelApiResponse", "LevelApi: " + group.toString());
                        }

                        createButtons(); // Create buttons after processing JSON response
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("JsonError", "Error parsing JSON", e);
                        Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("JsonResponse", "Volley Error: " + error.toString());
                    if(error instanceof NetworkError) {
                        if(error.networkResponse != null) {
                            Log.e("Network Error", "Status code: " + error.networkResponse.statusCode);
                        }
                        Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        Log.e("Parse Error", "Error parsing response", error);
                        Toast.makeText(this, "Error parsing response", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Volley Error", "Error fetching data: " + error.getMessage());
                        Toast.makeText(this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }


    private void createButtons() {
        // Create a ScrollView to contain the buttons
        ScrollView scrollView = new ScrollView(this);

        // Create a LinearLayout to hold the buttons vertically
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // Add the LinearLayout to the ScrollView
        scrollView.addView(linearLayout);

        // Define margin in pixels
        int marginPx = getResources().getDimensionPixelSize(R.dimen.button_margin);
        Typeface poppinsSemiBold = ResourcesCompat.getFont(this, R.font.poppinssemibold);

        for (final GroupApi group : groupList) {
            Button button = new Button(this);
            if(Objects.equals(language, "fr")){
                button.setText(group.getAbbreviationFr());
            }else {
                button.setText(group.getAbbreviationAr());
            }
            button.setId(group.getGroupId());
            button.setPadding(40, 20, 40, 20); // Adjust padding as needed
            button.setTextColor(Color.WHITE);
            button.setTypeface(poppinsSemiBold);
            // Set drawable background
            button.setBackgroundResource(R.drawable.button_background); // Replace "button_background" with your drawable resource

            // Set OnClickListener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle button click
                    // You can add your action here, for example:
                    Intent intent = new Intent(Group.this, TimeTable.class);
                    intent.putExtra("groupId",button.getId());
                    intent.putExtra("facultyId",facultyId);
                    intent.putExtra("departmentId",departmentId);
                    intent.putExtra("specId",specId);
                    intent.putExtra("secId",secId);
                    intent.putExtra("lvlId",lvlId);
                    intent.putExtra("lang", language);
                    startActivity(intent);
                    animateButton(button);
                }
            });

            // Create LayoutParams to set margins and width
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            // Set margins
            layoutParams.setMargins(marginPx, marginPx, marginPx, marginPx);

            // Apply LayoutParams to the button
            button.setLayoutParams(layoutParams);

            // Add button to the LinearLayout
            linearLayout.addView(button);
        }

        // Add the ScrollView to the main layout
        buttonContainer.addView(scrollView);
    }

    private void animateButton(final Button button) {
        // Define animation
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.9f, 1, 0.9f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(100);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);

        // Start animation
        button.startAnimation(scaleAnimation);
    }
    public void switchLanguage(View view) {
        // Toggle between languages
        if (language.equals("fr")) {
            language = "ar"; // Assuming "ar" represents Arabic language
        } else {
            language = "fr";
        }

        // Update UI according to the selected language
        updateUI();
    }

    private void updateUI() {
        // Clear existing buttons from buttonContainer
        buttonContainer.removeAllViews();

        // Recreate buttons with updated text
        createButtons();
    }
}