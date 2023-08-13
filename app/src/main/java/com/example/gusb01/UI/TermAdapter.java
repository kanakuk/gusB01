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
import com.example.gusb01.entities.Term;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    public class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termItemView;
        private TermViewHolder(View itemview){
            super(itemview);
            termItemView=itemview.findViewById(R.id.textView);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    final Term current=mTerms.get(position);
                    Intent intent= new Intent(context, TermDetails.class);
                    intent.putExtra("id", current.getTermID());
                    intent.putExtra("name", current.getTermName());
                    intent.putExtra("number", current.getTermNumber());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Term>mTerms;
    private final Context context;
    private final LayoutInflater mInflater;
    public TermAdapter(Context context) {
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.term_list_item,parent,false);
        return new TermViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder
            (@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms!=null){
            Term current=mTerms.get(position);
            String name=current.getTermName();
            holder.termItemView.setText(name);
        }
        else{
            holder.termItemView.setText("No Term Name");
        }
    }

    public void setTerms(List<Term> terms) {
        mTerms=terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { return mTerms.size(); }
}
































