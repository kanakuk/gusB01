package com.example.gusb01.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.gusb01.Database.Repository;
import com.example.gusb01.R;
import com.example.gusb01.entities.Course;
import com.example.gusb01.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class TermDetails extends AppCompatActivity {
    EditText editName;
    EditText editNumber;
    String name;
    double number;
    int termID;
    Repository repository;
    Term currentTerm;
    int numCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        editName=findViewById(R.id.termname);
        editNumber=findViewById(R.id.termnumber);
        //id=getIntent().getIntExtra("id", -1);
        name=getIntent().getStringExtra("name");
        number=getIntent().getDoubleExtra("number", -1.0);
        editName.setText(name);
        editNumber.setText(Double.toString(number));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository=new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        termID = getIntent().getIntExtra("id", -1);
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()){
            if(c.getTermID() == termID) filteredCourses.add(c);
        }
        courseAdapter.setCourse(filteredCourses);

        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TermDetails.this,CourseDetails.class);
                intent.putExtra("termID", termID);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.termsave:
                Term term;
                if (termID == -1) {
                    if (repository.getmAllTerms().size() == 0) termID = 1;
                    else
                        termID = repository.getmAllTerms().
                                get(repository.getmAllTerms().size() - 1)
                                .getTermID() + 1;
                    term = new Term(termID, editName.getText().toString(),
                            Double.parseDouble(editNumber.getText().toString()));
                    repository.insert(term);
                } else {
                    term = new Term(termID, editName.getText().toString(),
                            Double.parseDouble(editNumber.getText().toString()));
                    repository.update(term);
                }
                return true;
            case R.id.termdelete:
                for (Term ter : repository.getmAllTerms()) {
                    if (ter.getTermID() == termID) currentTerm = ter;
                }

                numCourses = 0;
                for (Course course : repository.getAllCourses()) {
                    if (course.getTermID() == termID) ++numCourses;
                }

                if (numCourses == 0) {
                    repository.delete(currentTerm);
                    Toast.makeText(TermDetails.this,
                            currentTerm.getTermName() + "was deleted",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TermDetails.this,
                            "can't delete a term with courses",
                            Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.addSamplecourses:
                if (termID == -1)
                    Toast.makeText(TermDetails.this,
                            "save term before adding courses",
                            Toast.LENGTH_LONG).show();
                else {
                    int courseID;
                        if (repository.getAllCourses().size() == 0) courseID = 1;
                        else
                            courseID = repository.getAllCourses().
                                    get(repository.getAllCourses().size() - 1).getCourseID() +1;
                        Course course = new Course(courseID,
                                "anthrpology", 10, termID);
                        repository.insert(course);
                        course = new Course(++courseID,
                                "biology", 10, termID);
                        repository.insert(course);
                        RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
                        final CourseAdapter courseAdapter = new CourseAdapter(this);
                        recyclerView.setAdapter(courseAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        List<Course> filteredCourses = new ArrayList<>();
                        for (Course c : repository.getAllCourses()) {
                            if (c.getTermID() == termID) filteredCourses.add(c);
                        }
                        courseAdapter.setCourse(filteredCourses);
                        return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermID() == termID) filteredCourses.add(c);
        }
        courseAdapter.setCourse(filteredCourses);
    }


}


















































