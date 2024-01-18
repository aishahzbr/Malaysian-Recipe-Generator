package ftmk.bitp3453.labtest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminPage extends AppCompatActivity {
    ConnectionClass connectionClass;
    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<Pizza> orderModalArrayList;
    private DBHandler dbHandler;
    private OrderRVAdapter orderRVAdapter;
    private RecyclerView orderRV;
    Button logout;
    Button menu;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        // initializing our all variables.
        orderModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(AdminPage.this);
        logout = (Button) findViewById(R.id.logout);
        menu = (Button) findViewById(R.id.btnDisplayMenu);

        // getting our course array
        // list from db handler class.
        orderModalArrayList = dbHandler.readOrders();

        // on below line passing our array lost to our adapter class.
        orderRVAdapter = new OrderRVAdapter(orderModalArrayList, AdminPage.this);
        orderRV = findViewById(R.id.idRVCourses);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AdminPage.this, RecyclerView.VERTICAL, false);
        orderRV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        orderRV.setAdapter(orderRVAdapter);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity via a intent.
                Intent i = new Intent(AdminPage.this, MenuDisplay.class);
                startActivity(i);
            }
        });

    }

    public void LogOut(){
        Intent intent = new Intent(AdminPage.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "This happen when app is about to stop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "This happen when app is about to start", Toast.LENGTH_SHORT).show();
    }

}