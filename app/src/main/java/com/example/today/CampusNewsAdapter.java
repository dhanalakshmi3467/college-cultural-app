package com.example.today;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CampusNewsAdapter extends RecyclerView.Adapter<CampusNewsAdapter.ProductViewHolder> {

    LinearLayout ll_search;
    private Context mCtx;
    private List<NewsList> productList;

    //declare interface
    private OnItemClicked onClick;


    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public CampusNewsAdapter(Context context, List<NewsList> productList) {
        this.mCtx = context;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.news_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        NewsList product = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(product.getImage())
                .into(holder.imageView);

        holder.txtId.setText(String.valueOf(product.getId()));
        holder.textTitle.setText(product.getTitle());
        holder.textNews.setText(product.getNews_by());
        holder.textInfo.setText(product.getInfo());
        holder.textDate.setText(product.getDate());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textNews, textInfo, textDate, txtId;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            txtId = itemView.findViewById(R.id.eventId);
            textTitle = itemView.findViewById(R.id.textViewTitle);
            textNews = itemView.findViewById(R.id.textViewNews);
            textInfo = itemView.findViewById(R.id.textViewInfo);
            textDate = itemView.findViewById(R.id.textViewDate);


            imageView = itemView.findViewById(R.id.imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, NewsDetails.class);
                   intent.putExtra("id",txtId.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}

