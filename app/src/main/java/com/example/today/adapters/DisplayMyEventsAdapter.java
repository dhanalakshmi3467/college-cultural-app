package com.example.today.adapters;

import androidx.appcompat.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.today.MyEvents.DisplayMyEvent;
import com.example.today.R;
import com.example.today.models.Events;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class DisplayMyEventsAdapter extends RecyclerView.Adapter<DisplayMyEventsAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Events> events;
    private OnItemClicked onClick;
    private CardView cardview;
    private TextView eventNotRegistered;


    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public DisplayMyEventsAdapter(Context context, List<Events> events) {
        this.mCtx = context;
        this.events = events;
    }


    @NonNull
    @Override
    public DisplayMyEventsAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.display_my_events, null);
        return new DisplayMyEventsAdapter.ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DisplayMyEventsAdapter.ProductViewHolder holder, int position) {
        Events event = events.get(position);
        holder.id.setText(event.getId());
        holder.title.setText(event.getTitle());
        holder.date.setText(event.getDate());
        holder.time.setText(event.getTime());
        holder.id.setVisibility(View.GONE);
        setBackGroundColor(position, holder.relativeLayout);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView id, title, date, time, eventNotRegistered;
        RelativeLayout relativeLayout;

        public ProductViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.displayMyEventsAdopter);
            id = itemView.findViewById(R.id.displayMyEventId);
            title = itemView.findViewById(R.id.displayMyEventTitle);
            date = itemView.findViewById(R.id.displayMyEventDate);
            time = itemView.findViewById(R.id.displayMyEventTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DisplayMyEvent.class);
                    intent.putExtra("response", getEvent(id.getText().toString()));
                    context.startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                    CharSequence[] dialogItem = {"View Data", "Edit Data", "Delete Data"};
                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            builder.create();
                    return false;
                }
            });
        }
    }

    private void setBackGroundColor(int position, RelativeLayout relativeLayout) {
        if (position % 4 == 0) {
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color5));
        } else if (position % 4 == 1) {
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color2));
        } else if (position % 4 == 2) {
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color3));
        } else if (position % 4 == 3){
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color4));
        }
        else if (position % 4 == 4){
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color5));
        }
        else if (position % 4 == 5){
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color_6));
        }
        else {
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color7));
        }
    }

    private String getEvent(String id) {
        ObjectMapper objectMapper = new ObjectMapper();
        for (Events event : events) {
            if (id.equals(event.getId())) {
                try {
                    return objectMapper.writeValueAsString(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
