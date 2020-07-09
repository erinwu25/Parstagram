package com.example.parstagram;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.parse.ParseFile;

import org.w3c.dom.Text;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    Context context;
    List<Post> posts;

    // pass in the context and list of posts
    public PostAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;


    }

    @NonNull
    @Override
    // for each row, inflate the layout
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    // bind views based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        // get data
        Post post = posts.get(position);
        // bind tweet with view holder
        holder.bind(post);

    }

    @Override
    // item count
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> postList) {
        posts.addAll(postList);
        notifyDataSetChanged();
    }

    // define a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvUsername;
        ImageView ivPostImage;
        TextView tvPostDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            tvPostDescription = itemView.findViewById(R.id.tvPostDescription);

            // itemView's onClickListener
            itemView.setOnClickListener(this);
        }

        public void bind(Post post) {
            tvUsername.setText(post.getUser().getUsername());
            tvPostDescription.setText(post.getDescription());

            ParseFile image = post.getImage();
            if (image != null) {
                ivPostImage.setVisibility(View.VISIBLE);
                String test = image.getUrl();

                Glide.with(context)
                        .load(test)
                        .into(ivPostImage);
            } else {
                ivPostImage.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View view) {

        }
    }
}
