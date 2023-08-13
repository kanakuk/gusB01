package com.example.gusb01.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gusb01.Database.Repository;
import com.example.gusb01.R;
import com.example.gusb01.entities.Course;
import com.example.gusb01.entities.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {
    String name;
    Double number;
    int courseID;
    int termID;
    EditText editName;
    EditText editNumber;
    Repository repository;
    EditText editNote;
    EditText editDate;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository=new Repository(getApplication());
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.courseName);
        editName.setText(name);
        number = getIntent().getDoubleExtra("number", -1.0);
        editNumber = findViewById(R.id.courseNumber);
        editNumber.setText(Double.toString(number));
        courseID = getIntent().getIntExtra("id", -1);
        termID = getIntent().getIntExtra("termID", -1);

        editNote=findViewById(R.id.note);
        editDate=findViewById(R.id.date);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ArrayList<Term> termArrayList = new ArrayList<>();
        termArrayList.addAll(repository.getmAllTerms());
        ArrayList<Integer> termIdList= new ArrayList<>();
        for (Term term:termArrayList) {
            termIdList.add(term.getTermID());
        }

        Spinner spinner=findViewById(R.id.spinner);
        ArrayAdapter<Term> termIdAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,repository.getmAllTerms());
        spinner.setAdapter(termIdAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                editNote.setText(termIdAdapter.getItem(i).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                editNote.setText("Nothing selected");
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;

                String info=editDate.getText().toString();
                if(info.equals(""))info="02/01/23";
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

    }
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
//
//
//
            case R.id.coursesave:
                Course course;
                if (courseID == -1) {
                    //repository=new Repository(getApplication());
                    if (repository.getAllCourses().size() == 0)
                        courseID = 1;
                    else
                        courseID = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseID() + 1;
                    course = new Course(courseID, editName.getText().toString(), Double.parseDouble(editNumber.getText().toString()), termID);
                    repository.insert(course);
                } else {
                    course = new Course(courseID, editName.getText().toString(), Double.parseDouble(editNumber.getText().toString()), termID);
                    repository.update(course);
                }
                return true;

            case R.id.share:
                Intent sendIntent=new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,editNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
                sendIntent.setType("text/plain");
                Intent shareIntent=Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

            case R.id.notifystart:
                String dateFromScreen=editDate.getText().toString();
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate=null;
                try {
                    myDate=sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger=myDate.getTime();
                Intent intent= new Intent
                        (CourseDetails.this, MyReceiver.class);
                intent.putExtra("key", dateFromScreen+ " should trigger");
                PendingIntent sender=PendingIntent.getBroadcast
                        (CourseDetails.this,
                                ++MainActivity.numAlert,
                                intent,
                                PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            ////
            case R.id.notifyend:
                return true;
        }

        return super.onOptionsItemSelected(item);

    }
}









































