package LoginAndRegister;

import android.os.Bundle;

import BaseActivity.BaseActivity;

public class UserDetailsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_screen);

        setContainer(new UserDetailsFragment());
        initViews();
    }

//        setContentView(R.layout.activity_user_details);
//
//        initUserDetailsViews();
//
//        traveler = ServerUtility.getInstance(this).getTraveler();
//        editTxtFirstName.setHint(traveler.getFirstName());
//        editTxtLastName.setHint(traveler.getLastName());
//        editTxtEmail.setHint(traveler.getEmailAddress());
//        editTxtPassword.setHint(traveler.getPassword());
//        editTxtConfirmPassword.setHint(traveler.getPassword());
//
//        btnCancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserDetailsActivity.this, BaseActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //show snackBar
//                Toast.makeText(UserDetailsActivity.this, "Changes saved", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    private void initUserDetailsViews() {
//        btnSaveChanges = findViewById(R.id.btnSaveChanges);
//        btnCancle = findViewById(R.id.btnCancle);
//        editTxtFirstName = findViewById(R.id.editTxtFirstName);
//        editTxtLastName = findViewById(R.id.editTxtLastName);
//        editTxtEmail = findViewById(R.id.editTxtEmail);
//        editTxtPassword = findViewById(R.id.editTxtPassword);
//        editTxtConfirmPassword = findViewById(R.id.editTxtConfirmPassword);
//    }
}