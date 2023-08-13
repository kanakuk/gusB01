package com.example.gusb01.UI;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gusb01.R;
import com.example.gusb01.entities.Course;
import com.example.gusb01.entities.Term;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;
        private final TextView courseItemView2;
        private CourseViewHolder(View itemview){
            super(itemview);
            courseItemView=itemview.findViewById(R.id.textViewcoursename);
            courseItemView2=itemview.findViewById(R.id.textViewcoursenumber);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    final Course current=mCourses.get(position);
                    Intent intent= new Intent(context, CourseDetails.class);
                    intent.putExtra("id", current.getCourseID());
                    intent.putExtra("name", current.getCourseName());
                    intent.putExtra("number", current.getNumber());
                    intent.putExtra("termID", current.getTermID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.course_list_item,parent,false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if(mCourses!=null){
            Course current=mCourses.get(position);
            String name=current.getCourseName();
            //int to double
            int termID=current.getTermID();
            holder.courseItemView.setText(name);
            holder.courseItemView2.setText(Integer.toString(termID));
            //Integer to Double
        }
        else{
            holder.courseItemView.setText("No Course Name");
            holder.courseItemView.setText("No Term Name");
        }
    }

    public void setCourse(List<Course> courses) {
        mCourses=courses;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mCourses.size();
    }
}







































