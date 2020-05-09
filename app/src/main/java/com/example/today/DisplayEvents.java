package com.example.today;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.today.models.Events;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class DisplayEvents extends RecyclerView.Adapter<DisplayEvents.ProductViewHolder> {

    private Context mCtx;
    private List<Events> events;
    private OnItemClicked onClick;


    public interface OnItemClicked {
        void onItemClick(int position);
    }

    DisplayEvents(Context context, List<Events> events) {
        this.mCtx = context;
        this.events = events;
    }


    @NonNull
    @Override
    public DisplayEvents.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.display_events, null);
        return new DisplayEvents.ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DisplayEvents.ProductViewHolder holder, int position) {
        Events event = events.get(position);
        holder.id.setText(event.getId());
        holder.title.setText(event.getTitle());
        holder.date.setText(event.getDate());
        holder.time.setText(event.getTime());

        holder.id.setVisibility(View.GONE);
       // setBackGroundColor(position, holder.itemView);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView id, title, date, time;

        public ProductViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.displayEventId);
            title = itemView.findViewById(R.id.displayEventTitle);
            date = itemView.findViewById(R.id.displayEventDate);
            time = itemView.findViewById(R.id.displayEventTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DisplayEvent.class);
                    intent.putExtra("response", getEvent(id.getText().toString()));
                    context.startActivity(intent);
                }
            });
        }
    }

    private void setBackGroundColor(int position, View view) {
//        FF4081
        if (position % 4 == 0) {
            view.setBackgroundColor(Color.parseColor("#CCFFCC"));
        } else if (position % 4 == 1) {
            view.setBackgroundColor(Color.parseColor("#99FF66"));
        } else if (position % 4 == 2) {
            view.setBackgroundColor(Color.parseColor("#FF9966"));
        } else {
            view.setBackgroundColor(Color.parseColor("#669900"));
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
