    package com.example.shopapp.Adapter;

    import android.content.Context;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.shopapp.Model.categoriesModel;
    import com.example.shopapp.R;

    import java.util.List;


    public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

        private Context context;
        private List<categoriesModel> categoryList;

        public CategoriesAdapter(Context context, List<categoriesModel> categoryList) {
            this.context = context;
            this.categoryList = categoryList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.categories, parent, false);
            return new ViewHolder(view);
        }



        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            categoriesModel category = categoryList.get(position);
            Log.d("CategoriesAdapter", "Binding: " + category.getCategoryName()); // Debug log
            holder.categoryName.setText(category.getCategoryName());
            holder.categoryImage.setImageResource(category.getCategoryImage());
        }


        @Override
        public int getItemCount() {
            return categoryList.size();
        }


        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView categoryImage;
            TextView categoryName;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                categoryImage = itemView.findViewById(R.id.categoryImage);
                categoryName = itemView.findViewById(R.id.categoryName);
            }
        }
    }
