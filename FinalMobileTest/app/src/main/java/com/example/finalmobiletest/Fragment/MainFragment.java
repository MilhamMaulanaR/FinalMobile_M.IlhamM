package com.example.finalmobiletest.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.finalmobiletest.ApiService;
import com.example.finalmobiletest.Activity.Quote;
import com.example.finalmobiletest.Adapter.QuoteAdapter;
import com.example.finalmobiletest.R;
import com.example.finalmobiletest.Model.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {
    private ApiService apiService;
    private RecyclerView recyclerView;
    private QuoteAdapter quoteAdapter;
    private List<Quote> quotesList;
    private Button btn_all, btn_love, btn_sad, btn_happiness, btn_peace, btn_birthday, btn_death, btn_education, btn_family, btn_funny, btn_time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize API service and quotes list
        apiService = RetrofitClient.getQuotesClient().create(ApiService.class);
        quotesList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialize buttons
        btn_love = view.findViewById(R.id.btn_love);
        btn_sad = view.findViewById(R.id.btn_sad);
        btn_happiness = view.findViewById(R.id.btn_happiness);
        btn_peace = view.findViewById(R.id.btn_peace);
        btn_birthday = view.findViewById(R.id.btn_birthday);
        btn_death = view.findViewById(R.id.btn_death);
        btn_education = view.findViewById(R.id.btn_education);
        btn_family = view.findViewById(R.id.btn_family);
        btn_funny = view.findViewById(R.id.btn_funny);
        btn_time = view.findViewById(R.id.btn_time);
        btn_all = view.findViewById(R.id.btn_all);

        // Set tags for buttons
        btn_love.setTag("love");
        btn_sad.setTag("sad");
        btn_happiness.setTag("happiness");
        btn_peace.setTag("peace");
        btn_birthday.setTag("birthday");
        btn_death.setTag("death");
        btn_education.setTag("education");
        btn_family.setTag("family");
        btn_funny.setTag("funny");
        btn_time.setTag("time");
        btn_all.setTag("all");

        // Initialize RecyclerView with StaggeredGridLayoutManager
        recyclerView = view.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        // Initialize adapter and set it to RecyclerView
        quoteAdapter = new QuoteAdapter(quotesList);
        recyclerView.setAdapter(quoteAdapter);

        // Fetch initial quotes
        fetchQuotes("all", 20);

        // Set onClickListeners for buttons
        setOnClickListener(btn_love);
        setOnClickListener(btn_sad);
        setOnClickListener(btn_happiness);
        setOnClickListener(btn_peace);
        setOnClickListener(btn_birthday);
        setOnClickListener(btn_death);
        setOnClickListener(btn_education);
        setOnClickListener(btn_family);
        setOnClickListener(btn_funny);
        setOnClickListener(btn_time);
        setOnClickListener(btn_all);

        return view;
    }

    // Method to set onClickListener for buttons
    private void setOnClickListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = button.getTag().toString();
                fetchQuotes(category, 10);
                ResetButtonColors();
                button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                button.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            }
        });
    }

    // Method to fetch quotes from API
    private void fetchQuotes(String category, int count) {
        Call<List<Quote>> call = apiService.getQuotes(category, count);
        call.enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    quotesList.clear();
                    quotesList.addAll(response.body());
                    quoteAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(0);
                } else {
                    Toast.makeText(getActivity(), "Failed to get quotes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {
                Toast.makeText(getActivity(), "Request failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to reset button colors
    public void ResetButtonColors() {
        btn_love.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        btn_sad.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        btn_happiness.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        btn_peace.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        btn_birthday.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        btn_death.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        btn_education.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        btn_family.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        btn_funny.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        btn_time.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        btn_all.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));

        btn_love.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        btn_sad.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        btn_happiness.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        btn_peace.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        btn_birthday.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        btn_death.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        btn_education.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        btn_family.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        btn_funny.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        btn_time.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        btn_all.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
    }
}
