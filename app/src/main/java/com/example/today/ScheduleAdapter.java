package com.example.today;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ProductViewHolder> {

    public static boolean scheduleReturnFlag = false;
    private Context mCtx;
    private List<ListModel> productList;

    //declare interface
    private OnItemClicked onClick;

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public ScheduleAdapter(Context context, List<ListModel> productList) {
        this.mCtx = context;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.schedule1, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        ListModel product = productList.get(position);

        //loading the image
     /*   Glide.with(mCtx)
                .load(product.getImage())
                .into(holder.imageView);*/

        holder.eventId.setText(String.valueOf(product.getId()));
        holder.textViewTitle.setText(product.getTitle());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, eventId;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);


            eventId = itemView.findViewById(R.id.eventId);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);

            textViewTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ScheduleActivity.class);
                    intent.putExtra("id", eventId.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}
