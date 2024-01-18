package ftmk.bitp3453.labtest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.function.Function;


public class CustomerConfirmOrderPage extends AppCompatActivity {

    //ConnectionClass connectionClass;
    private DBHandler dbHandler;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_confirm_order_page);
        TextView displaydt = (TextView) findViewById(R.id.displaydatetimee);
        TextView displayordernum = (TextView) findViewById(R.id.displayorderid);

        dbHandler = new DBHandler(this);
        GetDateTime getDateTime = new GetDateTime(this);
        getDateTime.getDateTime(new GetDateTime.VolleyCallback() {
            @Override
            public void onGetDateTime(String date, String time) {
                displaydt.setText(date + "\n" + time);
            }
        });

        Cursor cursor = dbHandler.getLastRows("orderpizza");
        String data = "";

        /*if (cursor!=null && cursor.moveToFirst()) {
            do {
                data += cursor.getString(0) + "\n";
            } while (cursor.moveToNext());
            displayordernum.setText("Your Order No: " + "\n" + data);
        }*/


        if (cursor!=null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                data += cursor.getString(0) + "\n";
                cursor.moveToNext();
                displayordernum.setText("Your Order No: " + "\n" + data);
            }
        }

        /*if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                data += cursor.getString(0) + "\n";
                cursor.moveToNext();
                displayordernum.setText("Your Order No: " + "\n" + data);
            }
        }*/
    }

    public void fnBackMenu(){
        Intent intent = new Intent(CustomerConfirmOrderPage.this, MainActivity.class);
        startActivity(intent);
    }
}