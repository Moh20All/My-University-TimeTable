package com.example.timetable;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

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

public class Department extends AppCompatActivity {
int buttonId;
    private LinearLayout buttonContainer;
    private List<DepartmentApi> DepartmentApiList;
    private RequestQueue requestQueue;
    private String language = "fr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        buttonId = getIntent().getIntExtra("buttonId", -1); // -1 is the default value if the extra is not found
        // Initialize requestQueue
        requestQueue = Volley.newRequestQueue(this);
        language = getIntent().getStringExtra("lang");
        if (language == null) {
            language = "fr";
        }
        Toast.makeText(this,language,Toast.LENGTH_SHORT).show();
        // Initialize facultyList
        DepartmentApiList = new ArrayList<>();

        // Initialize buttonContainer
        buttonContainer = findViewById(R.id.buttonContainer);
        FetchDep();
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
    public void FetchDep() {
        final LoadingDialog loadingDialog = new LoadingDialog(Department.this);
        loadingDialog.startLoadingDialog();
        String url = "https://num.univ-biskra.dz/psp/pspapi/department?faculty=" + buttonId + "&key=appmob";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Set<String> departmentNames = new HashSet<>(); // HashSet to store department names
                        loadingDialog.dissmisdialog();

                        for(int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            DepartmentApi departmentApi = new DepartmentApi(
                                    jsonObject.getInt("id"),
                                    jsonObject.optString("name_fr", ""),
                                    jsonObject.optString("name_ar", "")
                            );

                            String departmentName = departmentApi.getNameFr();
                            if (!departmentNames.contains(departmentName)) {
                                DepartmentApiList.add(departmentApi);
                                departmentNames.add(departmentName);
                            }

                            Log.d("JsonResponse", "DepartmentApi ID: " + departmentApi.getId() +
                                    ", Name (French): " + departmentApi.getNameFr() +
                                    ", Name (Arabic): " + departmentApi.getNameAr());
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



    private void displayDepartmentApiInfo(DepartmentApi departmentApi) {
        TextView textView = findViewById(R.id.result); // Assuming you have a TextView in your layout with id result
        String DepartmentApiInfo = "ID: " + departmentApi.getId() + "\n" +
                "Name (French): " + departmentApi.getNameFr() + "\n" +
                "Name (Arabic): " + departmentApi.getNameAr();
        textView.setText(DepartmentApiInfo);
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

        for (final DepartmentApi DepartmentApi : DepartmentApiList) {
            Button button = new Button(this);
            if(Objects.equals(language, "fr")){
                button.setText(DepartmentApi.getNameFr());
            }else{
                button.setText(DepartmentApi.getNameAr());
            }

            button.setId(DepartmentApi.getId());
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
                    Intent I = new Intent(Department.this,speciality.class);
                    I.putExtra("departmentId", button.getId());
                    I.putExtra("facultyId", buttonId);
                    I.putExtra("lang", language);

                    startActivity(I);
                    // Animate the button
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