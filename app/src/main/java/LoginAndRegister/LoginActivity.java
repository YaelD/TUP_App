package LoginAndRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TupApp.R;

import NavigationDrawer.NavigationDrawerActivity;
import JavaClasses.ServerConnection;

public class LoginActivity extends AppCompatActivity {

    private final String URL = "http://10.0.0.5/login.php";
    private static final String TAG = "LoginActivity";
    private EditText txtEmail, txtPassword;
    private TextView txtWarnEmail, txtWarnPassword;
    private Button btnLoginActivity;
    private String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

    }

    private void initLogin(){
        Log.d(TAG, "initLogin: started");

        if(validateData()){
            getLogInInfoFromDB();
            //Intent intent= new Intent(LoginActivity.this, MainScreenActivity.class);
            //startActivity(intent);
        }
    }

    private boolean validateData() {
        Log.d(TAG, "validateData: started");
        boolean validData = true;
        if(txtEmail.getText().toString().equals("")) {
            txtWarnEmail.setVisibility(View.VISIBLE);
            txtWarnEmail.setText("Enter your Email");
            validData = false;
        }
        if(txtPassword.getText().toString().equals("")){
            txtWarnPassword.setVisibility(View.VISIBLE);
            txtWarnPassword.setText("Enter your password");
            validData = false;
        }
        return validData;
    }

    private void initViews(){
        Log.d(TAG, "initViews: started");
        txtEmail= findViewById(R.id.txtEmail);
        txtPassword= findViewById(R.id.txtPassword);
        txtWarnEmail= findViewById(R.id.txtWarnEmail);
        txtWarnPassword = findViewById(R.id.txtWarnPassword);
        btnLoginActivity = findViewById(R.id.btnLoginActivity);
    }

    public void login(View view) {
        txtWarnEmail.setVisibility(View.GONE);
        txtWarnPassword.setVisibility(View.GONE);
        initLogin();

    }

    public void getLogInInfoFromDB()
    {


        email = txtEmail.getText().toString().trim();
        password = txtPassword.getText().toString().trim();
        ServerConnection.getInstance(this).logIn(email, password);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if(ServerConnection.getInstance(getApplicationContext()).getException()== null)
                {
                    Log.e("HERE:", "EXCEPTION = NULL");
                    ServerConnection.getInstance(getApplicationContext()).setException(null);
                    Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Log.e("HERE:", "EXCEPTION != NULL");
                    Toast.makeText(getApplicationContext(),
                            ServerConnection.getInstance(getApplicationContext()).getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        new Handler().postDelayed(r, 2000);

    }
}