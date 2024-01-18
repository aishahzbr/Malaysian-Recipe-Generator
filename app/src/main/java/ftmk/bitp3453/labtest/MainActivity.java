package ftmk.bitp3453.labtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText qtywpz;
    EditText qtytpz;
    private DBHandler dbHandler;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //navigationviewcode
        drawerLayout = findViewById(R.id.layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.nav_menu);
        dbHandler = new DBHandler(this);
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intentnav;
                switch (item.getItemId()) {
                    case R.id.nav_customer:
                        intentnav = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentnav);
                        return true;
                    case R.id.nav_admin:
                        intentnav = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intentnav);
                        return true;
                    default:
                        return false;
                }
            }
        });

        //Intent intent = getIntent();
        //String username = intent.getStringExtra("namekey");
        Toast.makeText(this, "Welcome !!", Toast.LENGTH_SHORT).show();

        qtywpz = (EditText) findViewById(R.id.editWoodpz);
        qtytpz = (EditText) findViewById(R.id.editTurkishpz);

        new ImageFromInternet((ImageView) findViewById(R.id.woodpic)).execute("https://lh3.googleusercontent.com/p/AF1QipPH8YIRrh-GRTaCH4z4Vz5cFjJsxLt845rqWy4-=w1080-h608-p-no-v0");
        new ImageFromInternet((ImageView) findViewById(R.id.turkishpic)).execute("https://img-global.cpcdn.com/recipes/f220b4b19b7a5831/1360x964cq70/turkish-pizza-pide-foto-resep-utama.webp");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fncalculate (View v){
        int woodpizza = Integer.parseInt(qtywpz.getText().toString());
        int turkishpizza = Integer.parseInt(qtytpz.getText().toString());

        int totalItem = woodpizza + turkishpizza;

        TextView displayqty = (TextView)findViewById(R.id.edttotalquantity);
        TextView displaypr = (TextView) findViewById(R.id.edttotalprice);
        displayqty.setText("Total Item: " + totalItem);
        double totalPrice = (woodpizza * 18) + (turkishpizza * 22);
        displaypr.setText("Total Price: RM " + totalPrice);
    }
    public void ConfirmOrder(View view){
        int woodpizza = Integer.parseInt(qtywpz.getText().toString());
        int turkishpizza = Integer.parseInt(qtytpz.getText().toString());

        String woodpizzaqty = qtywpz.getText().toString();
        String turkishpizzaqty = qtytpz.getText().toString();

        if(turkishpizza == 0) {
            dbHandler.insertOrder("001", woodpizza);
            Toast.makeText(MainActivity.this, "Order has been confirmed!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, CustomerConfirmOrderPage.class);
            startActivity(intent);
        }

        else if (woodpizza == 0){
            dbHandler.insertOrder("002", turkishpizza);
            Toast.makeText(MainActivity.this, "Order has been confirmed!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, CustomerConfirmOrderPage.class);
            startActivity(intent);
        }

        else if (!woodpizzaqty.isEmpty() && !turkishpizzaqty.isEmpty()){
            dbHandler.insertOrder("001", woodpizza);
            dbHandler.insertOrder("002", turkishpizza);
            Toast.makeText(MainActivity.this, "Order has been confirmed!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, CustomerConfirmOrderPage.class);
            startActivity(intent);
        }

        else
        {
            Toast.makeText(MainActivity.this, "No order?", Toast.LENGTH_SHORT).show();
        }

        qtywpz.setText("");
        qtytpz.setText("");
    }

    private class ImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public ImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {

                InputStream in = new java.net.URL(imageURL).openStream();
                bimage= BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }


}