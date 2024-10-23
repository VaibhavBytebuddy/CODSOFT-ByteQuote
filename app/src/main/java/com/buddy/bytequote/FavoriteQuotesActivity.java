package com.buddy.bytequote;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class FavoriteQuotesActivity extends AppCompatActivity {

    private ListView favoriteQuotesListView;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorite_quotes);

        favoriteQuotesListView=findViewById(R.id.favoriteQuotesListView);
        db=new DatabaseHelper(this);

        //Load favorite quote from database
        loadFavoriteQuotes();




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadFavoriteQuotes() {
        Cursor cursor=db.getAllFavorites();
        List<String > favoriteQuotes=new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do {
                @SuppressLint("Range") String quote = cursor.getString(cursor.getColumnIndex("quote"));
                favoriteQuotes.add(quote);
            } while (cursor.moveToNext());
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,favoriteQuotes);
        favoriteQuotesListView.setAdapter(adapter);

    }


}