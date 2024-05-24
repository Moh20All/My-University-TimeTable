package com.example.timetable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.toolbox.JsonArrayRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class TimeTable extends AppCompatActivity {
    int secId;
    int groupId;
    int facultyId;
    int departmentId;
    int specId;
    int lvlId;
    String option = "null";
    private MyDbHelper dbHelper;

    private LinearLayout buttonContainer;
    List<TimetableEntry> timetableEntries = new ArrayList<>();

    ArrayList<String> days= new ArrayList<>();

    TimetableEntry t1;
    private RequestQueue requestQueue;
    List<TimeSlot> timeSlotList = new ArrayList<>();
    Button lastClickedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        requestQueue = Volley.newRequestQueue(this);
        timetableEntries = new ArrayList<>();
        //Retriving information
        dbHelper = new MyDbHelper(this);
        secId = getIntent().getIntExtra("secId", -1);
        facultyId = getIntent().getIntExtra("facultyId", -1);
        departmentId = getIntent().getIntExtra("departmentId", -1);
        specId = getIntent().getIntExtra("specId", -1);
        lvlId = getIntent().getIntExtra("lvlId", -1);
        groupId = getIntent().getIntExtra("groupId", -1);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);
        topAppBar.showOverflowMenu();
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        timeSlotList.add(new TimeSlot()) ;
        timeSlotList.add(new TimeSlot(1,"08:00","09:30")) ;
        timeSlotList.add(new TimeSlot(2,"09:40","11:10")) ;
        timeSlotList.add(new TimeSlot(3,"11:20","12:50")) ;
        timeSlotList.add(new TimeSlot(4,"13:10","14:40")) ;
        timeSlotList.add(new TimeSlot(5,"14:50","16:20")) ;
        timeSlotList.add(new TimeSlot(6,"16:30","18:00")) ;
        timeSlotList.add(new TimeSlot(7,"unknown","unknown")) ;
        fetchTimetablefinal(secId,2,"fr",groupId,2);

        // Get references to your buttons
        Button allButton = findViewById(R.id.all);
        Button sunday = findViewById(R.id.Sunday);
        Button monday = findViewById(R.id.Monday);
        Button tuesday = findViewById(R.id.Tuesday);
        Button wednesday = findViewById(R.id.Wednesday);
        Button thursday = findViewById(R.id.Thursday);

        sunday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
        monday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
        tuesday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
        wednesday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
        thursday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));

        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change background tint colors
                allButton.setBackgroundTintList(getResources().getColorStateList(R.color.defalt));
                sunday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                monday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                tuesday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                wednesday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                thursday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));

                displayTimetable("");
            }
        });        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change background tint colors
                allButton.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                sunday.setBackgroundTintList(getResources().getColorStateList(R.color.defalt));
                monday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                tuesday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                wednesday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                thursday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));

                // Handle click action for Sunday button
                displayTimetable("Sunday");
            }
        });

        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change background tint colors
                allButton.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                sunday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                monday.setBackgroundTintList(getResources().getColorStateList(R.color.defalt));
                tuesday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                wednesday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                thursday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));

                // Handle click action for Monday button
                displayTimetable("Monday");
            }
        });

        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change background tint colors
                allButton.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                sunday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                monday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                tuesday.setBackgroundTintList(getResources().getColorStateList(R.color.defalt));
                wednesday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                thursday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));

                // Handle click action for Tuesday button
                displayTimetable("Tuesday");
            }
        });

        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change background tint colors
                allButton.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                sunday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                monday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                tuesday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                wednesday.setBackgroundTintList(getResources().getColorStateList(R.color.defalt));
                thursday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));

                // Handle click action for Wednesday button
                displayTimetable("Wednesday");
            }
        });

        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change background tint colors
                allButton.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                sunday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                monday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                tuesday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                wednesday.setBackgroundTintList(getResources().getColorStateList(R.color.defaltdis));
                thursday.setBackgroundTintList(getResources().getColorStateList(R.color.defalt));

                // Handle click action for Thursday button
                displayTimetable("Thursday");
            }
        });

        days.add("Sunday");
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        Button buttonTP = findViewById(R.id.buttonTP);
        Button buttonTD = findViewById(R.id.buttonTD);
        Button buttonCOUR = findViewById(R.id.buttonCOUR);
        LinearLayout cardcontainer = findViewById(R.id.cardcontainer);
        // Set onClick listeners for each button
        buttonTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle TP button click
                cardcontainer.removeAllViews();
                option= "TP";
                displayTimetable("");
            }
        });

        buttonTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle TD button click
                cardcontainer.removeAllViews();
                option= "TD";
                displayTimetable("");
            }
        });

        buttonCOUR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle COUR button click
                cardcontainer.removeAllViews();
                option= "COUR";
                displayTimetable("");
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
            showInputDialog();

            // Do something
            return true;
        }
        if (id == R.id.search) {
            showSearchDialog();
            return true;
        }
        if (id== R.id.more){
            nextsession();

        }

        return super.onOptionsItemSelected(item);
    }


// Inside your TimeTable class

    public void fetchTimetablefinal(int sectionId, int semester, String language, int subgroup, int yearId) {
        String url = "https://num.univ-biskra.dz/psp/emploi/section2_public?select_spec="+specId+"&niveau="+lvlId+"&section="+sectionId+"&groupe="+groupId+"&sg=0&langu="+language+"&sem=2&id_year=2";
        final LoadingDialog loadingDialog = new LoadingDialog(TimeTable.this);
        loadingDialog.startLoadingDialog();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Log.d("JsonResponse", "Response received: " + response.toString()); // Log the entire JSON response
                        loadingDialog.dissmisdialog();
                        for (int i = 0; i < response.length(); i++) {
                            JSONArray entryArray = response.getJSONArray(i);
                            Log.d("JsonResponse", "Entry array " + i + ": " + entryArray.toString()); // Log the inner array

                            // Create a TimetableEntry object using the values in the inner array
                            TimetableEntry entry = new TimetableEntry(
                                    entryArray.getString(0), // dataType
                                    entryArray.getString(1), // Location
                                    entryArray.getString(2), // Type of course
                                    entryArray.getString(3), // Level of study
                                    entryArray.getString(4), // Field
                                    entryArray.getString(5), // Professor's last name
                                    entryArray.getString(6), // Professor's first name
                                    entryArray.getString(7), // Unknown field
                                    entryArray.getString(8), // Module name
                                    entryArray.getString(12), // Day of week
                                    entryArray.getString(13), // Time slot
                                    entryArray.getString(14), // Sub-group
                                    entryArray.getInt(19) == 1, // Online
                                    entryArray.getInt(20) == 1, // Biweekly
                                    entryArray.getString(21), // Online link
                                    entryArray.getString(22) // Location GPS
                            );

                            Comparator<TimetableEntry> comparator = new Comparator<TimetableEntry>() {
                                @Override
                                public int compare(TimetableEntry entry1, TimetableEntry entry2) {
                                    // Parse dayOfWeek strings to integers for comparison
                                    int dayOfWeek1 = Integer.parseInt(entry1.getDayOfWeek());
                                    int dayOfWeek2 = Integer.parseInt(entry2.getDayOfWeek());

                                    // Compare dayOfWeek
                                    int dayOfWeekComparison = Integer.compare(dayOfWeek1, dayOfWeek2);
                                    if (dayOfWeekComparison != 0) {
                                        return dayOfWeekComparison; // Return result if dayOfWeek is different
                                    }

                                    // If dayOfWeek is the same, compare timeSlot
                                    int timeSlot1 = Integer.parseInt(entry1.getTimeSlot());
                                    int timeSlot2 = Integer.parseInt(entry2.getTimeSlot());
                                    return Integer.compare(timeSlot1, timeSlot2);
                                }
                            };

                            Collections.sort(timetableEntries, comparator);

                            timetableEntries.add(entry);
                        }

                        // After parsing, you can display or process the timetableEntries list as needed
                        displayTimetable("");
                        //createButtons();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("JsonError", "Error parsing JSON", e);
                        Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("JsonResponse", "Volley Error: " + error.toString());
                    if (error instanceof NetworkError) {
                        if (error.networkResponse != null) {
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


    private void displayTimetable(String dayOfWeek) {


        int dayIndex = -1; // Default value to indicate no specific day filter
        if (!dayOfWeek.isEmpty()) {
            dayIndex = days.indexOf(dayOfWeek); // Get the index of the specified day
        }

        LinearLayout container = findViewById(R.id.cardcontainer); // Assuming container is the ID of your LinearLayout inside ScrollView
        container.removeAllViews(); // Clear existing views

        for (TimetableEntry entry : timetableEntries) {
            if ((option.equals("TP")&&entry.getCourseType().equals("TP"))||(option.equals("TD")&&entry.getCourseType().equals("TD"))||(option.equals("COUR")&&entry.getCourseType().equals("Cours"))||option.equals("null")) {
                // Check if the entry should be filtered based on the specified day
                if (dayIndex == -1 || Integer.parseInt(entry.getDayOfWeek()) - 1 == dayIndex) {
                    View cardView = LayoutInflater.from(this).inflate(R.layout.cardtable, null);

                    // Find views within the card layout
                    ImageView imageView = cardView.findViewById(R.id.imageView);
                    TextView timeslotTextView = cardView.findViewById(R.id.timeslot);
                    TextView moduleTextView = cardView.findViewById(R.id.module);
                    TextView locationTextView = cardView.findViewById(R.id.location);
                    TextView profTextView = cardView.findViewById(R.id.prof);
                    TextView gpsTextView = cardView.findViewById(R.id.gps);
                    TextView type = cardView.findViewById(R.id.type);


                    // Modify views with data from the TimetableEntry object
                    timeslotTextView.setText(days.get(Integer.parseInt(entry.getDayOfWeek()) - 1) + " " + timeSlotList.get(Integer.parseInt(entry.getTimeSlot())).toString());
                    moduleTextView.setText(entry.getDataType());
                    locationTextView.setText(entry.getLocation());
                    profTextView.setText(entry.getProfessorFirstName() + " " + entry.getProfessorLastName());
                    gpsTextView.setText(Html.fromHtml("<a href=\"" + (entry.isOnline() ? entry.getOnlineLink() : entry.getLocationGPS()) + "\">Click here</a>"));
                    type.setText(entry.getCourseType());
                    // Set text color based on type
                    int colorResId;
                    switch (entry.getCourseType()) {
                        case "Online":
                            colorResId = R.color.online;
                            break;
                        case "TP":
                            colorResId = R.color.TP;
                            break;
                        case "TD":
                            colorResId = R.color.TD;
                            break;
                        case "Cours":
                            colorResId = R.color.Cours;
                            break;
                        default:
                            colorResId = android.R.color.black; // Default color if type does not match any case
                            break;
                    }
                    gpsTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Split the coordinates string using the comma as the delimiter


                            openPlace(v.getContext(), entry);

                        }
                    });
                    type.setTextColor(ContextCompat.getColor(this, colorResId));

                    container.addView(cardView);
                }
            }
        }
    }
    private void openPlace(Context context, TimetableEntry session) {
        try {
            if (session == null) {
                Log.e("openPlace", "TimetableEntry object is null");
                Toast.makeText(context, "TimetableEntry object is null", Toast.LENGTH_SHORT).show();
                return;
            }

            if (context == null) {
                Log.e("openPlace", "Context object is null");
                Toast.makeText(context, "Context object is null", Toast.LENGTH_SHORT).show();
                return;
            }

            if (session.isOnline()) {
                if (!session.getOnlineLink().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(session.getOnlineLink()));
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "URL Is Empty", Toast.LENGTH_SHORT).show();
                }
            } else {   // Open GPS
                if (session.getLocationGPS() == null || session.getLocationGPS().isEmpty()) {
                    Log.e("openPlace", "Location GPS is null or empty");
                    Toast.makeText(context, "Location GPS is null or empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Split the string using comma
                String[] parts = session.getLocationGPS().split(",");

                if (parts.length < 2) {
                    Log.e("openPlace", "Invalid format for location GPS");
                    Toast.makeText(context, "Invalid format for location GPS", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Extract latitude and longitude values
                double latitude = Double.parseDouble(parts[0].trim());
                double longitude = Double.parseDouble(parts[1].trim());

                // Create an Intent to view the location in Google Maps
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(String.format(Locale.US,
                                "geo:%f,%f?q=%f,%f",
                                latitude,
                                longitude,
                                latitude,
                                longitude)));

                // Ensure that Google Maps is used
                intent.setPackage("com.google.android.apps.maps");

                // Start the intent
                context.startActivity(intent);
            }
        } catch (Exception e) {
            Log.e("openPlace", "Exception occurred", e);
            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
        }
    }
    void nextsession() {
        Calendar calendar = Calendar.getInstance();

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String dayOfWeekString;
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                dayOfWeekString = "1";
                break;
            case Calendar.MONDAY:
                dayOfWeekString = "2";
                break;
            case Calendar.TUESDAY:
                dayOfWeekString = "3";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeekString = "4";
                break;
            case Calendar.THURSDAY:
                dayOfWeekString = "5";
                break;
            case Calendar.FRIDAY:
                dayOfWeekString = "6";
                break;
            case Calendar.SATURDAY:
                dayOfWeekString = "7";
                break;
            default:
                dayOfWeekString = "-1";
                break;
        }

        Calendar time = Calendar.getInstance();

        // Get the current hour and minute
        int hour = time.get(Calendar.HOUR_OF_DAY); // 24-hour format
        int minute = time.get(Calendar.MINUTE);

        // Find the next session
        TimetableEntry nextSession = null;
        for (TimetableEntry entry : timetableEntries) {
            if (true) {//dayOfWeekString.equals(entry.getDayOfWeek())
                // Calculate the start and end times of the session based on time slot
                int timeSlotIndex = Integer.parseInt(entry.getTimeSlot());
                if (timeSlotIndex < 1 || timeSlotIndex >= timeSlotList.size()) {
                    continue; // Skip invalid time slots
                }
                TimeSlot timeSlot = timeSlotList.get(timeSlotIndex);

                // Parse the start time of the session
                String[] startTimeParts = timeSlot.getStartTime().split(":");
                int startTimeHour = Integer.parseInt(startTimeParts[0]);
                int startTimeMinute = Integer.parseInt(startTimeParts[1]);

                // Check if the session is after the current time
                if (startTimeHour > hour || (startTimeHour == hour && startTimeMinute > minute)) {
                    nextSession = entry;
                    break;
                }
            }
        }

        if (nextSession != null) {
            // Print or display information about the next session
        showCustomAlertDialog(nextSession);
        } else {
            // No sessions found after the current time
            Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_SHORT).show();
        }
    }


    public void showCustomAlertDialog(TimetableEntry entry) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.cardtable, null);
        builder.setView(dialogView);

        // Initialize views in the custom dialog layout
        TextView typeTextView = dialogView.findViewById(R.id.type);
        TextView timeSlotTextView = dialogView.findViewById(R.id.timeslot);
        TextView moduleTextView = dialogView.findViewById(R.id.module);
        TextView locationTextView = dialogView.findViewById(R.id.location);
        TextView profTextView = dialogView.findViewById(R.id.prof);
        TextView gpsTextView = dialogView.findViewById(R.id.gps);

        // Set data to the views
        timeSlotTextView.setText(days.get(Integer.parseInt(entry.getDayOfWeek())-1)+" "+timeSlotList.get(Integer.parseInt(entry.getTimeSlot())).toString());
        moduleTextView.setText(entry.getDataType());
        locationTextView.setText(entry.getLocation());
        profTextView.setText(entry.getProfessorFirstName()+" "+ entry.getProfessorLastName());
        gpsTextView.setText(Html.fromHtml("<a href=\"" + (entry.isOnline() ? entry.getOnlineLink() : entry.getLocationGPS()) + "\">Click here</a>"));
        typeTextView.setText(entry.getCourseType());

        // Set title
        builder.setTitle("Next Session");

        // Add OK button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do something when OK button is clicked
            }
        });

        // Create and show the dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showInputDialog() {
        // Inflate the input dialog layout
        View dialogView = LayoutInflater.from(TimeTable.this).inflate(R.layout.input_dialog, null);

        // Create the dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(TimeTable.this)
                .setTitle("Enter Name")
                .setView(dialogView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Retrieve the input text from the EditText
                        EditText inputEditText = dialogView.findViewById(R.id.input);
                        String name = inputEditText.getText().toString();

                        // Check if name is not empty
                        if (!TextUtils.isEmpty(name)) {
                            // Store (secID, groupID, name) in the SQLite database
                            storeData(secId, groupId, name,lvlId,specId);
                        } else {
                            // Show an error message if the name is empty
                            Toast.makeText(TimeTable.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null);

        // Show the dialog
        builder.show();
    }



    private void storeData(int secId, int groupId, String name,int lvlId,int specId) {
        MyDbHelper DB= new MyDbHelper(this);
        Boolean checkinsertdata = DB.storedata(name, secId, groupId,lvlId,specId);
        if(checkinsertdata==true)
            Toast.makeText(TimeTable.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(TimeTable.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
    }
    // Inside MyDbHelper class
    private void showSearchDialog() {
        // Inflate the search dialog layout
        View dialogView = LayoutInflater.from(TimeTable.this).inflate(R.layout.search_dialog, null);

        // Create the dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(TimeTable.this)
                .setTitle("Search")
                .setView(dialogView)
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Retrieve the search query from the EditText
                        EditText searchEditText = dialogView.findViewById(R.id.searchEditText);
                        String query = searchEditText.getText().toString().trim();

                        // Filter timetable entries based on the search query
                        filterTimetableEntries(query);
                    }
                })
                .setNegativeButton("Cancel", null);

        // Show the dialog
        builder.show();
    }
    private void filterTimetableEntries(String query) {
        List<TimetableEntry> filteredEntries = new ArrayList<>();
        for (TimetableEntry entry : timetableEntries) {
            // Check if the entry's data type contains the search query
            if (query.isEmpty()) {
                // If the query is empty, add all entries
                filteredEntries.add(entry);
            } else {
                // If the query is not empty, check if it exactly matches or is contained in the dataType
                String dataType = entry.getDataType().toLowerCase();
                String queryLowerCase = query.toLowerCase();

                if (dataType.equals(queryLowerCase) || dataType.contains(queryLowerCase)) {
                    filteredEntries.add(entry);

                }
            }

        }

        // Display the filtered timetable entries
        displayFilteredTimetable(filteredEntries);
    }
    private void displayFilteredTimetable(List<TimetableEntry> filteredEntries) {
        LinearLayout container = findViewById(R.id.cardcontainer);
        container.removeAllViews(); // Clear existing views

        for (TimetableEntry entry : filteredEntries) {
            // Create and add views for each filtered entry
            View cardView = LayoutInflater.from(this).inflate(R.layout.cardtable, null);

            // Find views within the card layout
            ImageView imageView = cardView.findViewById(R.id.imageView);
            TextView timeslotTextView = cardView.findViewById(R.id.timeslot);
            TextView moduleTextView = cardView.findViewById(R.id.module);
            TextView locationTextView = cardView.findViewById(R.id.location);
            TextView profTextView = cardView.findViewById(R.id.prof);
            TextView gpsTextView = cardView.findViewById(R.id.gps);
            TextView type = cardView.findViewById(R.id.type);

            // Modify views with data from the TimetableEntry object
            timeslotTextView.setText(days.get(Integer.parseInt(entry.getDayOfWeek()) - 1) + " " + timeSlotList.get(Integer.parseInt(entry.getTimeSlot())).toString());
            moduleTextView.setText(entry.getDataType());
            locationTextView.setText(entry.getLocation());
            profTextView.setText(entry.getProfessorFirstName() + " " + entry.getProfessorLastName());
            gpsTextView.setText(Html.fromHtml("<a href=\"" + (entry.isOnline() ? entry.getOnlineLink() : entry.getLocationGPS()) + "\">Click here</a>"));
            type.setText(entry.getCourseType());

            // Set text color based on type
            int colorResId;
            switch (entry.getCourseType()) {
                case "Online":
                    colorResId = R.color.online;
                    break;
                case "TP":
                    colorResId = R.color.TP;
                    break;
                case "TD":
                    colorResId = R.color.TD;
                    break;
                case "Cours":
                    colorResId = R.color.Cours;
                    break;
                default:
                    colorResId = android.R.color.black; // Default color if type does not match any case
                    break;
            }
            type.setTextColor(ContextCompat.getColor(this, colorResId));

            gpsTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPlace(v.getContext(), entry);
                }
            });

            container.addView(cardView);
        }
    }


}

