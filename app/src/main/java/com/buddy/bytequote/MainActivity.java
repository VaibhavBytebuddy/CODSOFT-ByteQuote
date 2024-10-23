package com.buddy.bytequote;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView quoteTextView;
    private TextView authorTextView;
    private ImageView quoteImageView;
    private Button favoriteButton;
    private Button shareButton;

    //new logic
    private DatabaseHelper dbHelper;

    private Button openFavActivity;

    private List<Integer> backgroundImages;
    private Random random;

    private Button refreshButton;
    //

    private DatabaseHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Initialize UI components
        quoteTextView = findViewById(R.id.quoteTextView);
        authorTextView = findViewById(R.id.autorTextView);
        quoteImageView = findViewById(R.id.quoteImageView);
        favoriteButton = findViewById(R.id.favoriteButton);
        shareButton = findViewById(R.id.shareButton);

        db = new DatabaseHelper(this);


        // Load a new quote (e.g., from the database or API)
        //new logic


        dbHelper = new DatabaseHelper(this);

//
//         //if you want to add more data in your databse (add more quotes in database the call this method
//        // Example data to insert into the database


//
//        String exampleQuote ,exampleAuthor;
//
//
//
//        exampleQuote = "You have within you right now, everything you need to deal with whatever the world can throw at you.";
//        exampleAuthor = "Brian Tracy";
//
//        // Insert the quote into the database
//        insertQouteIntoDatabase(exampleQuote, exampleAuthor);
//






        //new logic  for bg

        backgroundImages = Arrays.asList(
                R.drawable.image3, R.drawable.image4,
                R.drawable.image5, R.drawable.image6,
                R.drawable.image7, R.drawable.image8
                // Add more image resources here
        );
        random = new Random();


        //


        displayRandomQuote();


        openFavActivity = findViewById(R.id.openFavActivity);

        //setonclick listener

        openFavActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent intent = new Intent(MainActivity.this, FavoriteQuotesActivity.class);

                startActivity(intent);
            }
        });


        //favorite quote


        //refresh quotes
// Handle refresh button click

        refreshButton = findViewById(R.id.refreshButton);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Refresh quote and change background
                displayRandomQuote();
            }
        });



        //new logic end


        //fetch and display a random quote
//        displayRandomQuote();

        //mark quote as favorite
        //new logic


        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markQuoteAsFavorite();

            }
        });


        //


        //share quote logic
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current quote and author
                String quoteToShare = "\"" + quoteTextView.getText().toString() + "\" - " + authorTextView.getText().toString();

                // Create an intent to share the quote
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain"); // Set the type to plain text
                shareIntent.putExtra(Intent.EXTRA_TEXT, quoteToShare); // Add the quote and author as extra text

                // Start the share dialog
                startActivity(Intent.createChooser(shareIntent, "Share quote via"));


            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void displayRandomQuote() {
        Cursor cursor = db.getRandomQuote();
        if (cursor.moveToFirst()) {
            //get values fromdb
            @SuppressLint("Range") String quote = cursor.getString(cursor.getColumnIndex("quote"));
            @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex("author"));

            //set values to mainactivity
            quoteTextView.setText(quote);
            authorTextView.setText(author);


            // Change background image randomly
            int randomImageIndex = random.nextInt(backgroundImages.size());
            findViewById(R.id.main).setBackgroundResource(backgroundImages.get(randomImageIndex));

        }
        cursor.close();

    }


    //temp method for save  some data in database
    //new logic

    private void insertQouteIntoDatabase(String quote, String author) {

        dbHelper.insertQuote(quote, author);

        Toast.makeText(this, "Quote inseted sucessfully", Toast.LENGTH_SHORT).show();

    }


    private void markQuoteAsFavorite() {

        Cursor cursor = db.getRandomQuote();
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int quoteId = cursor.getInt(cursor.getColumnIndex("id"));
            db.markAsFavorite(quoteId);
            Toast.makeText(this, "Marked as favorite", Toast.LENGTH_SHORT).show();

        }
        cursor.close();

    }


    //new logic end




////        exampleQuote = "You have within you right now, everything you need to deal with whatever the world can throw at you.";
////        exampleAuthor = "Brian Tracy";
////
////        exampleQuote = "Believe you can and you're halfway there.";
////        exampleAuthor = "Theodore Roosevelt";
////
////        exampleQuote = "The only impossible journey is the one you never begin.";
////        exampleAuthor = "Tony Robbins";
////
////        exampleQuote = "Your time is limited, so don’t waste it living someone else’s life.";
////        exampleAuthor = "Steve Jobs";
////
////        exampleQuote = "Not how long, but how well you have lived is the main thing.";
////        exampleAuthor = "Seneca";
////
////        exampleQuote = "The best revenge is massive success.";
////        exampleAuthor = "Frank Sinatra";
////
////        exampleQuote = "Life is either a daring adventure or nothing at all.";
////        exampleAuthor = "Helen Keller";
////
////        exampleQuote = "To live is the rarest thing in the world. Most people exist, that is all.";
////        exampleAuthor = "Oscar Wilde";
////
////        exampleQuote = "Success is not the key to happiness. Happiness is the key to success.";
////        exampleAuthor = "Albert Schweitzer";
////
////        exampleQuote = "Do not dwell in the past, do not dream of the future, concentrate the mind on the present moment.";
////        exampleAuthor = "Buddha";
////
////        exampleQuote = "What lies behind us and what lies before us are tiny matters compared to what lies within us.";
////        exampleAuthor = "Ralph Waldo Emerson";
////
////        exampleQuote = "In the end, we will remember not the words of our enemies, but the silence of our friends.";
////        exampleAuthor = "Martin Luther King Jr.";
////
////        exampleQuote = "It does not matter how slowly you go as long as you do not stop.";
////        exampleAuthor = "Confucius";
////
////        exampleQuote = "The future belongs to those who believe in the beauty of their dreams.";
////        exampleAuthor = "Eleanor Roosevelt";
////
////        exampleQuote = "I am not a product of my circumstances. I am a product of my decisions.";
////        exampleAuthor = "Stephen R. Covey";
////


}
