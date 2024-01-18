package ftmk.bitp3453.labtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

public class LoginActivity extends AppCompatActivity {
    ConnectionClass connectionClass;

    EditText username;
    EditText password;
    Button login;
    String name;
    String pass;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    DBHandler DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //navigationviewcode
        drawerLayout = findViewById(R.id.layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.nav_menu);
        DB = new DBHandler(this);

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

        username = (EditText) findViewById(R.id.edtName);
        password = (EditText) findViewById(R.id.edtPassword);
        login = (Button) findViewById(R.id.btnLogin);
    }

    public void fnlogin(View vw){
        //change to string
        name = username.getText().toString();
        pass = password.getText().toString();

        login = (Button) findViewById(R.id.btnLogin);

        if (name.matches("") && pass.matches("")) {
            Toast.makeText(this, "Please fill in name and password", Toast.LENGTH_SHORT).show();
        }
        else if (name.matches("") || pass.matches("")) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
        else {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean checkAdmin = DB.checkAdmin(name, pass);
                    if(checkAdmin == true) {
                        Toast.makeText(LoginActivity.this, "Successfully Login!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, AdminPage.class);
                        startActivity(intent);
                    }

                    else
                    {
                        Toast.makeText(LoginActivity.this, "Wrong username or password!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}