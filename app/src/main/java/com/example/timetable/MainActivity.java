package com.example.timetable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
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
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private LinearLayout buttonContainer;
    private LinearLayout saveContainer;
    private List<Faculty> facultyList;
    private RequestQueue requestQueue;
    private String language = "fr";

    ArrayList<Faculty> facultyArrayList = new ArrayList<>();
    ArrayList<savings> savingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize requestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Initialize facultyList
        facultyList = new ArrayList<>();

        // Initialize buttonContainer
        buttonContainer = findViewById(R.id.buttonContainer);
        saveContainer = findViewById(R.id.saveContainer);

        // Download JSON data and handle it
        FetchFac();

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);
        topAppBar.showOverflowMenu();
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        MyDbHelper DB= new MyDbHelper(this);
        Cursor res = DB.getdata();
        if (res==null){
            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();

        }

       while(res.moveToNext()){
           //Retrieve data from the cursor and add it to the list
           @SuppressLint("Range") String name = res.getString(res.getColumnIndex("name"));
           @SuppressLint("Range") int secId = res.getInt(res.getColumnIndex("sec_id"));
           @SuppressLint("Range") int groupId = res.getInt(res.getColumnIndex("group_id"));
           @SuppressLint("Range") int lvl = res.getInt(res.getColumnIndex("lvl_id"));
           @SuppressLint("Range") int spec = res.getInt(res.getColumnIndex("spec_id"));
           savingList.add(new savings(name, secId, groupId,spec,lvl));
            }
    savedButtons();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.topbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
    public void FetchFac() {
        String url = "https://num.univ-biskra.dz/psp/pspapi/faculty?key=appmob";
        final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);
        loadingDialog.startLoadingDialog();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Set<String> facultyNames = new HashSet<>(); // HashSet to store faculty names
                        loadingDialog.dissmisdialog();

                        for(int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Faculty faculty = new Faculty(
                                    jsonObject.getInt("id_fac"),
                                    jsonObject.optString("name_fac", ""),
                                    jsonObject.optString("name_fac_ar", "")
                            );

                            String facultyName = faculty.getNameFac();
                            if (!facultyNames.contains(facultyName)) {
                                facultyList.add(faculty);
                                facultyNames.add(facultyName);
                            }

                            Log.d("JsonResponse", "Faculty ID: " + faculty.getId() +
                                    ", Name (French): " + faculty.getNameFac() +
                                    ", Name (Arabic): " + faculty.getNameFacAr());
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
                    loadingDialog.dissmisdialog();
                });
        requestQueue.add(jsonArrayRequest);
    }




    private void displayFacultyInfo(Faculty faculty) {
        TextView textView = findViewById(R.id.result); // Assuming you have a TextView in your layout with id result
        String facultyInfo = "ID: " + faculty.getId() + "\n" +
                "Name (French): " + faculty.getNameFac() + "\n" +
                "Name (Arabic): " + faculty.getNameFacAr();
        textView.setText(facultyInfo);
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

        for (final Faculty faculty : facultyList) {
            Button button = new Button(this);
            if(language=="fr"){
                button.setText(faculty.getNameFac());
            }else{
                button.setText(faculty.getNameFacAr());
            }
            button.setId(faculty.getId());
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
                    Intent intent = new Intent(MainActivity.this, Department.class);
                    intent.putExtra("buttonId", button.getId());
                    intent.putExtra("lang", language);
                    startActivity(intent);
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
    private void savedButtons() {
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

        for (final savings faculty : savingList) {
            Button button = new Button(this);
            button.setText(faculty.getName());
            button.setId(faculty.getGroupID());
            button.setPadding(40, 20, 40, 20); // Adjust padding as needed
            button.setTextColor(Color.WHITE);
            button.setTypeface(poppinsSemiBold);
            // Set drawable background
            button.setBackgroundResource(R.drawable.button_saved); // Replace "button_background" with your drawable resource

            // Set OnClickListener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle button click
                    Intent intent = new Intent(MainActivity.this, TimeTable.class);
                    intent.putExtra("groupId", faculty.getGroupID());
                    intent.putExtra("specId", faculty.getSpecID());
                    intent.putExtra("lvlId", faculty.getLvlID());
                    intent.putExtra("secId", faculty.getSecID());
                    startActivity(intent);
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
       saveContainer.addView(scrollView);
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