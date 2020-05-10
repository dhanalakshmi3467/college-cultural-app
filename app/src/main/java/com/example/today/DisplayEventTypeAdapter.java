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

import com.example.today.models.EventType;

import java.util.List;

public class DisplayEventTypeAdapter extends RecyclerView.Adapter<DisplayEventTypeAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<EventType> eventTypes;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    DisplayEventTypeAdapter(Context context, List<EventType> eventTypes) {
        this.mCtx = context;
        this.eventTypes = eventTypes;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.display_event_type, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
//        TODO
//        https://stackoverflow.com/questions/40692214/changing-background-color-of-selected-item-in-recyclerview
        EventType eventType = eventTypes.get(position);
        holder.id.setText(eventType.getId());
        holder.type.setText(eventType.getType());
        holder.id.setVisibility(View.GONE);
        //  setBackGroundColor(position, holder.itemView);

    }

    @Override
    public int getItemCount() {
        return eventTypes.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView id, type;

        public ProductViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.listEventId);
            type = itemView.findViewById(R.id.listEventType);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, EventsManagement.class);
                    intent.putExtra("type", id.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}
   /* private void setBackGroundColor(int position, View view) {
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
}
*/

