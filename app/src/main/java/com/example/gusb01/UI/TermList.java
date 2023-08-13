package com.example.gusb01.UI;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.gusb01.Database.Repository;
import com.example.gusb01.R;
import com.example.gusb01.entities.Course;
import com.example.gusb01.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermList extends AppCompatActivity {
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TermList.this,TermDetails.class);
                startActivity(intent);
            }
        });
        repository=new Repository(getApplication());
        List<Term> allTerms=repository.getmAllTerms();
        RecyclerView recyclerView=findViewById(R.id.termRecylerView);
        final TermAdapter termAdapter=new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }
//termDelete?
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_termdetails, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                //
                //
            return true;

            case R.id.addSampleTerms:
                Repository repo = new Repository(getApplication());
                Term term = new Term(1, "term1", 100.0);
                repo.insert(term);
                term = new Term(2, "term2", 150.0);
                repo.insert(term);
                List<Term> allTerms=repository.getmAllTerms();
                RecyclerView recyclerView=findViewById(R.id.termRecylerView);
                final TermAdapter termAdapter=new TermAdapter(this);
                recyclerView.setAdapter(termAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                termAdapter.setTerms(allTerms);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {

        super.onResume();
        List<Term> allTerms=repository.getmAllTerms();
        RecyclerView recyclerView=findViewById(R.id.termRecylerView);
        final TermAdapter termAdapter=new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }
}
/*RecyclerView recyclerView=findViewById(R.id.termRecylerView);
        final TermAdapter termAdapter= new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository=new Repository(getApplication());
        List<Term> allTerms=repository.getmAllTerms();
        termAdapter.setTerms(allTerms);
        protected void onResume() {
          */