package com.example.finalmobiletest.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.finalmobiletest.Model.Bookmark;
import com.example.finalmobiletest.Database.DatabaseHelper;
import com.example.finalmobiletest.Activity.Login;
import com.example.finalmobiletest.R;
import com.example.finalmobiletest.Adapter.bookmarkAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    ImageButton btn_profile_logout, btn_back_profile;
    CircleImageView avatarImageProfile;
    TextView profileName, profileUsername, profileDesc;
    RecyclerView recyclerView;
    List<Bookmark> bookmarks;
    bookmarkAdapter adapter;
    DatabaseHelper myDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_profile_logout = view.findViewById(R.id.btn_profile_logout);
        btn_back_profile = view.findViewById(R.id.btn_back_profile);
        avatarImageProfile = view.findViewById(R.id.avatarImageProfile);
        profileName = view.findViewById(R.id.Profile_name);
        profileUsername = view.findViewById(R.id.Profile_username);
        profileDesc = view.findViewById(R.id.Profile_desc);
        recyclerView = view.findViewById(R.id.bookmark_profile);

        myDB = new DatabaseHelper(getActivity());
        bookmarks = new ArrayList<>();

        adapter = new bookmarkAdapter(getActivity(), bookmarks);
        recyclerView.setAdapter(adapter);
        loadData();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        SharedPreferences preferencesName = getContext().getSharedPreferences("Name", MODE_PRIVATE);
        String name = preferencesName.getString("name", "");
        SharedPreferences preferencesDesc = getContext().getSharedPreferences("Desc", MODE_PRIVATE);
        String desc = preferencesDesc.getString("desc", "");

        profileName.setText(name);
        profileUsername.setText("@"+username);
        profileDesc.setText(desc);

        btn_profile_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("cekLoginFromLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("login", false);
                editor.apply();

                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btn_back_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finishAffinity();
            }
        });
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        int userId = myDB.getUserId(username);
        bookmarks.clear();
        bookmarks.addAll(myDB.getBookmarksByUserId(userId));
        adapter.notifyDataSetChanged();
    }


}
