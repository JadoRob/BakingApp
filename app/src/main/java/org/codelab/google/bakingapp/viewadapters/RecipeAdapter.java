package org.codelab.google.bakingapp.viewadapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import org.codelab.google.bakingapp.R;
import org.codelab.google.bakingapp.data.Recipe;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private static final String TAG = RecipeAdapter.class.getSimpleName();
    private List<Recipe> mRecipeList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RecipeAdapter(List<Recipe> recipeList) {
        mRecipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(v);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe currentRecipe = mRecipeList.get(position);
        //checks if the recipe does not have an image and uses a default picture.
        int image = defaultImage(currentRecipe);
        Picasso.get().load(image)
                .error(R.drawable.default_image)
                .placeholder(R.drawable.image_holder)
                .into(holder.mImageView);
        StringBuilder servingSize = new StringBuilder("Serving Size: ");
        String servings = Integer.toString(currentRecipe.getServings());
        holder.mRecipeView.setText(currentRecipe.getName());
        holder.mTextview2.setText(servingSize.append(servings));
    }

    private int defaultImage(Recipe recipe) {
        //check if no image is provided
        String food = recipe.getName();
        int image = 0;
        if (recipe.getImage().equals("")) {

            switch (food) {
                case "Nutella Pie":
                    image = R.drawable.pie;
                    break;
                case "Brownies":
                    image = R.drawable.brownie;
                    break;
                case "Yellow Cake":
                    image = R.drawable.yellow_cake;
                    break;
                case "Cheesecake":
                    image = R.drawable.cheese_cake;
                    break;
            }
            return image;
        }
        return R.drawable.default_image;
    }

    public void setRecipes(List<Recipe> recipeList) {
        mRecipeList = recipeList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mRecipeView;
        public TextView mTextview2;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mRecipeView = itemView.findViewById(R.id.textView);
            mTextview2 = itemView.findViewById(R.id.textView2);

            //handles clicks on the list and passes the position via interface
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}


