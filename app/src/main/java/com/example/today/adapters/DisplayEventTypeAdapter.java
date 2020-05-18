package com.example.today.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.today.EventsManagement;
import com.example.today.R;
import com.example.today.models.EventType;

import java.util.List;

public class DisplayEventTypeAdapter extends RecyclerView.Adapter<DisplayEventTypeAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<EventType> eventTypes;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public DisplayEventTypeAdapter(Context context, List<EventType> eventTypes) {
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

        EventType eventType = eventTypes.get(position);
        holder.id.setText(eventType.getId());
        holder.type.setText(eventType.getType());
        holder.id.setVisibility(View.GONE);
        setBackGroundColor(position, holder.relativeLayout);

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
        RelativeLayout relativeLayout;

        public ProductViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.displayEventTypeLayoutAdaptor);
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

    private void setBackGroundColor(int position, RelativeLayout relativeLayout) {
        if (position % 4 == 0) {
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color1));
        } else if (position % 4 == 1) {
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color2));
        } else if (position % 4 == 2) {
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color3));
        } else {
            relativeLayout.setBackground(ContextCompat.getDrawable(relativeLayout.getContext(), R.drawable.home_gradient_color4));
        }
    }
}

