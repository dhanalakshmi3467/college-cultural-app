package com.example.today.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.today.models.FeaturedHelperClass;
import com.example.today.R;

import java.util.List;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.ProductViewHolder> {

    LinearLayout ll_search;
    private Context mCtx;
    private List<FeaturedHelperClass> productList;

    //declare interface
    private OnItemClicked onClick;


    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public FeaturedAdapter(Context context, List<FeaturedHelperClass> productList) {
        this.mCtx = context;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_design, parent, false);
        ProductViewHolder featuredViewHolder = new ProductViewHolder(view);
        return featuredViewHolder;

    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        FeaturedHelperClass product = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(product.getImage())
                .into(holder.imageView);

        holder.title.setText(product.getTitle());
        holder.desc.setText(product.getDescription());


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView  title, desc;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.featured_image);
            title = itemView.findViewById(R.id.featured_title);
            desc = itemView.findViewById(R.id.featured_desc);



        }
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}

