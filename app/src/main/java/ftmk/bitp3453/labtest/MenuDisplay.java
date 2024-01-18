package ftmk.bitp3453.labtest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class MenuDisplay extends AppCompatActivity {

    private DBHandler dbHandler;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_display);

        TextView displaymenupizza = (TextView) findViewById(R.id.displaypizzamenu);
        dbHandler = new DBHandler(this);

        Cursor cursor = dbHandler.getMenu("pizza");
        String data = "";

        if (cursor!=null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                data += "\n\nPizza ID: " + cursor.getString(0) + "\n" + "Pizza Name: " + cursor.getString(1) + "\nPrice: " + cursor.getString(2) + "\n\n";
                cursor.moveToNext();
                displaymenupizza.setText("-MENU-" + "\n" + data);
            }
        }
    }
}