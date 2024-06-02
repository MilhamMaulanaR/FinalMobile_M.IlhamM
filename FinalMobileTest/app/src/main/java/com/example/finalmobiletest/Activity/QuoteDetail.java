package com.example.finalmobiletest.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.finalmobiletest.Database.DatabaseHelper;
import com.example.finalmobiletest.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuoteDetail extends AppCompatActivity {

    private ImageButton btnBackDetail, bookmarkQuoteDetail;
    private TextView quoteDetailName, quoteDetailUsername, kategoriQuoteDetail, quoteDetail, authorQuoteDetail;
    private CircleImageView dqAvatar;
    private Button btncopyquotedetail, btnDownloadQuoteDetail;
    private ImageView image;
    private View quoteContainer;
    private RelativeLayout download;
    private ProgressBar progressBar;
    private DatabaseHelper myDB;
    private HandlerThread handlerThread;
    private Handler backgroundHandler;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quote_detail);

        // Mengatur padding untuk view utama agar tidak tertutup oleh sistem bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi view dari layout
        btnBackDetail = findViewById(R.id.btn_back_detail);
        quoteDetailName = findViewById(R.id.quote_detail_name);
        quoteDetailUsername = findViewById(R.id.quote_detail_username);
        dqAvatar = findViewById(R.id.dq_avatar);
        kategoriQuoteDetail = findViewById(R.id.kategori_quote_detail);
        bookmarkQuoteDetail = findViewById(R.id.bookmark_quote_detail);
        quoteDetail = findViewById(R.id.quote_detail);
        authorQuoteDetail = findViewById(R.id.author_quote_detail);
        btncopyquotedetail = findViewById(R.id.btn_copy_quotedetail);
        btnDownloadQuoteDetail = findViewById(R.id.btn_download_quotedetail);
        image = findViewById(R.id.image);
        quoteContainer = findViewById(R.id.main);
        download = findViewById(R.id.download);
        progressBar = findViewById(R.id.progress_bar);

        // Inisialisasi DatabaseHelper
        myDB = new DatabaseHelper(this);

        // Inisialisasi HandlerThread dan Handlers
        handlerThread = new HandlerThread("SaveImageThread");
        handlerThread.start();
        backgroundHandler = new Handler(handlerThread.getLooper());
        mainHandler = new Handler(getMainLooper());

        // Mengambil data dari Intent
        Intent intent = getIntent();
        String author = intent.getStringExtra("author");
        String text = intent.getStringExtra("text");
        String category = intent.getStringExtra("category");

        // Mengambil data user dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        SharedPreferences preferencesName = getSharedPreferences("Name", MODE_PRIVATE);
        String name = preferencesName.getString("name", "");

        Log.d("QuoteDetail", "Attempting to load image into ImageView");

        // Membuat URL gambar dengan waktu saat ini untuk menghindari cache
        String url = "https://picsum.photos/200/300?grayscale&random=" + System.currentTimeMillis();

        // Mengatur listener untuk tombol salin kutipan
        btncopyquotedetail.setOnClickListener(v -> copyQuoteToClipboard());

        // Mengatur listener untuk tombol unduh kutipan
        btnDownloadQuoteDetail.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            backgroundHandler.post(() -> saveQuoteImage());
        });

        // Mengatur listener untuk tombol bookmark
        bookmarkQuoteDetail.setOnClickListener(v -> {
            int quoteId = intent.getIntExtra("id", 0);
            int idUser = myDB.getUserId(username);
            myDB.insertBookmarkQuote(quoteId, idUser, text, author, category);
            Toast.makeText(QuoteDetail.this, "Quote added to bookmarks", Toast.LENGTH_SHORT).show();
        });

        // Memuat gambar menggunakan Glide, dengan mengabaikan cache
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(image);

        // Mengatur teks untuk berbagai view
        authorQuoteDetail.setText(author);
        quoteDetail.setText(text);
        kategoriQuoteDetail.setText(category);
        quoteDetailName.setText(name);
        quoteDetailUsername.setText("@" + username); // Menambahkan "@" di depan username

        // Mengatur listener untuk tombol kembali
        btnBackDetail.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quitSafely();
    }

    // Fungsi untuk menyalin kutipan ke clipboard
    private void copyQuoteToClipboard() {
        String quote = quoteDetail.getText().toString();
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("quote", quote);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Quote copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    // Fungsi untuk menyimpan gambar kutipan
    private void saveQuoteImage() {
        Bitmap bitmap = getBitmapFromView(download);
        String fileName = "Quotable" + System.currentTimeMillis() + ".png";
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            mainHandler.post(() -> {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Image saved to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            });
        } catch (IOException e) {
            e.printStackTrace();
            mainHandler.post(() -> {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
            });
        }
    }

    // Fungsi untuk mendapatkan bitmap dari view
    private Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
