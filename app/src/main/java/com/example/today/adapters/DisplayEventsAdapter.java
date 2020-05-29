package com.example.today.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.today.Dashboard;
import com.example.today.DeleteEvent;
import com.example.today.DisplayEvent;
import com.example.today.EditActivity;
import com.example.today.R;
import com.example.today.models.Events;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class DisplayEventsAdapter extends RecyclerView.Adapter<DisplayEventsAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Events> events;
    private OnItemClicked onClick;


    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public DisplayEventsAdapter(Context context, List<Events> events) {
        this.mCtx = context;
        this.events = events;
    }


    @NonNull
    @Override
    public DisplayEventsAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.display_events, null);
        return new DisplayEventsAdapter.ProductViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull DisplayEventsAdapter.ProductViewHolder holder, int position) {
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
        TextView id, title, date, time;
        RelativeLayout relativeLayout;

        public ProductViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.displyEventsAdopter);
            id = itemView.findViewById(R.id.displayEventId);
            title = itemView.findViewById(R.id.displayEventTitle);
            date = itemView.findViewById(R.id.displayEventDate);
            time = itemView.findViewById(R.id.displayEventTime);
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (Dashboard.LoggedInUserInfo.isAdmin() || isCreatedBySameUser(id.getText().toString(), Dashboard.LoggedInUserInfo.getUuid())) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                        ProgressDialog progressDialog = new ProgressDialog(mCtx);
                        CharSequence[] dialogItem = {"View", "Edit", "Delete"};
//                    builder.setTitle("User Options");
                        builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//TODO if user selects View then call viewEvent(view); in the case condition
                                switch (which) {
                                    case 0:
                                        viewEvent(id.getText().toString());
                                        break;
                                    case 1:
                                        Intent intent = new Intent(mCtx, EditActivity.class);
                                        intent.putExtra("response", getEvent(id.getText().toString()));
                                        mCtx.startActivity(intent);
                                        break;
                                    case 2:
                                        String eventId = id.getText().toString();
                                        DeleteEvent deleteEvent = new DeleteEvent(mCtx,getEventTypeById(eventId));
                                        deleteEvent.execute(eventId);
                                        break;


                                }
                            }
                        });
                        builder.create().show();
                    } else {
                        viewEvent(id.getText().toString());
                    }
                }
            });
        }
    }

    private void viewEvent(String eventId) {
//        Context context = view.getContext();
        Intent intent = new Intent(mCtx, DisplayEvent.class);
        intent.putExtra("response", getEvent(eventId));
        mCtx.startActivity(intent);
    }

    private void setBackGroundColor(int position, RelativeLayout relativeLayout) {
        if (position % 4 == 0) {
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color7));
        } else if (position % 4 == 1) {
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color2));
        } else if (position % 4 == 2) {
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color3));
        } else {
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color4));
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

    private boolean isCreatedBySameUser(String eventId, String userId) {
        for (Events event : events) {
            if (eventId.equals(event.getId()) && userId.equals(Integer.toString(event.getCreatedBy()))) {
                return true;
            }
        }
        return false;
    }

    private String getEventTypeById(String id) {
        for (Events event : events) {
            if (event.getId().equals(id)) {
                return event.getType();
            }
        }
        return null;
    }
}
