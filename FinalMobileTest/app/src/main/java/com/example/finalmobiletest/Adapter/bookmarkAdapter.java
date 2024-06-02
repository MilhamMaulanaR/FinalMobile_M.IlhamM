package com.example.finalmobiletest.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalmobiletest.Model.Bookmark;
import com.example.finalmobiletest.Database.DatabaseHelper;
import com.example.finalmobiletest.R;

import java.util.List;

public class bookmarkAdapter extends RecyclerView.Adapter<bookmarkAdapter.bookmarkViewHolder> {
    private List<Bookmark> bookmarkList;
    private Context context;

    public bookmarkAdapter(Context context, List<Bookmark> bookmarkList) {
        this.context = context;
        this.bookmarkList = bookmarkList;
    }

    @NonNull
    @Override
    public bookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item_bookmark, parent, false);
        return new bookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bookmarkViewHolder holder, int position) {
        Bookmark bookmark = bookmarkList.get(position);
        holder.title.setText(bookmark.getTitle());
        holder.author.setText(bookmark.getAuthor());
        holder.category.setText(bookmark.getCategory());
        // Anda bisa menambahkan elemen lainnya sesuai kebutuhan
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public class bookmarkViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, category;
        ImageButton bookmark_ib_delete;

        public bookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text1);
            author = itemView.findViewById(R.id.author1);
            category = itemView.findViewById(R.id.category1);
            bookmark_ib_delete = itemView.findViewById(R.id.bookmark_user_item);

            bookmark_ib_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Bookmark bookmark = bookmarkList.get(position);
                        DatabaseHelper db = new DatabaseHelper(itemView.getContext());
                        SharedPreferences sharedPreferences = itemView.getContext().getSharedPreferences("user", MODE_PRIVATE);
                        String username = sharedPreferences.getString("username", "");
                        int idUser = db.getUserId(username);
                        db.deletebookmark(bookmark.getId(), idUser);
                        bookmarkList.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });
        }
    }
}
