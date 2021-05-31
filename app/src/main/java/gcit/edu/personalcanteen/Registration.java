package gcit.edu.personalcanteen;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    private EditText reg_name,reg_email,reg_password,reg_confirmpassword,reg_phonenumber;
    private FirebaseAuth auth;
    ImageView backBtn, background;
    Button reg_Btn,login;
    TextView titletext,welcome;

    FirebaseDatabase rootNode;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth=FirebaseAuth.getInstance();
        reg_name=findViewById(R.id.name);
        reg_email=findViewById(R.id.email);
        reg_password=findViewById(R.id.password);
        reg_confirmpassword=findViewById(R.id.confirmpassword);
        reg_phonenumber=findViewById(R.id.phonenumber);

        backBtn=findViewById(R.id.backbotton);
        background=findViewById(R.id.background);
        titletext=findViewById(R.id.signup_title_text);
        welcome=findViewById(R.id.welcome);
        reg_Btn=findViewById(R.id.reg_Btn);
        login=findViewById(R.id.signup_login_button);


    }


    public void back2(View view) {
        Intent obj = new Intent(this, Open.class);
        startActivity(obj);
    }

    public void signupp(View view) {
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("users");

//                if(!validateUsername() || !validatepassword() || !validateEmail() || !validateUserPhone() || !validateconpass()){
//                    return;
//                }

        String username=reg_name.getText().toString();
        String email=reg_email.getText().toString();
        String password=reg_password.getText().toString();
        String conpassword=reg_confirmpassword.getText().toString();
        String phoneNo=reg_phonenumber.getText().toString();

        UserHelperClass helperClass=new UserHelperClass(username,email,password,conpassword,phoneNo);
        reference.child(phoneNo).setValue(helperClass);


        if(TextUtils.isEmpty(username)){
            reg_name.setError("Name cannot be empty" );
            reg_name.requestFocus();
            return;

        }
        if(TextUtils.isEmpty(email)){
            reg_email.setError("Email Cannot be empty" );
           reg_email.requestFocus();
            return;

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            reg_email.setError("Please Provide Valid Email" );
            reg_email.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            reg_password.setError("Password cannot be empty" );
            reg_password.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(conpassword)){
            reg_confirmpassword.setError("Confirm password cannot be empty" );
            reg_confirmpassword.requestFocus();
            return;

        }
        if(!conpassword.equals(password)){
            Toast.makeText(this, "Password Didn't Match", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(phoneNo)){
            reg_phonenumber.setError("Confirm password cannot be empty" );
            reg_phonenumber.requestFocus();
            return;

        }

        Intent intent=new Intent(getApplicationContext(),VarifyNum.class);
        intent.putExtra("phoneNo",phoneNo);
        startActivity(intent);

    }


//    public void signup(View view) {
//        Intent obj = new Intent(this, Login.class);
//        startActivity(obj);
//
//    }

//    public void registerUser(View view) {
//        String userName=name.getText().toString();
//        String userEmail=email.getText().toString();
//        String userPassword=password.getText().toString();
//        String userConfirmPassword=confirmpassword.getText().toString();
//        String userPhone=phonenumber.getText().toString();
//    }

//    public void signup(View view) {
//        String userName=name.getText().toString();
//        String userEmail=email.getText().toString();
//        String userPassword=password.getText().toString();
//        String userConfirmPassword=confirmpassword.getText().toString();
//
//
//        auth.createUserWithEmailAndPassword(userEmail,userPassword)
//                .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(Registration.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(Registration.this,Login.class));
//                        }else{
//                            Toast.makeText(Registration.this, "Failed, Try Again"+ task.getException(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
}