package gcit.edu.personalcanteen;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private EditText phone,password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();

        phone=findViewById(R.id.phonenum);
        password=findViewById(R.id.passwordL);
    }



    public void register(View view) {
        Intent obj = new Intent(this, Registration.class);
        startActivity(obj);
    }

    public void signin(View view) {
        isUser();

//        auth.signInWithEmailAndPassword(userEmail,userPassword)
//                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(Login.this,MainActivity.class));
//                        }else{
//                            Toast.makeText(Login.this, "Error ", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
    }

    private void isUser() {

        String userEmail=phone.getText().toString();
        String userPassword=password.getText().toString();

        if(TextUtils.isEmpty(userEmail)){
            phone.setError("Email Cannot be empty" );
            phone.requestFocus();
            return;

        }
        if(TextUtils.isEmpty(userPassword)){
            password.setError("Password cannot be empty" );
            password.requestFocus();
            return;
        }
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query checkUser= reference.orderByChild("phoneNo").equalTo(userEmail);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.exists()){
                    phone.setError(null);
                    String passwordFromDB=snapshot.child(userEmail).child("password").getValue(String.class);

                    if(passwordFromDB.equals(userPassword)){
                        String conpassFromDB=snapshot.child(userEmail).child("conpassword").getValue(String.class);
                        String emailFromDB=snapshot.child(userEmail).child("email").getValue(String.class);
                        String phoneNoFromDB=snapshot.child(userEmail).child("phoneNo").getValue(String.class);
                        String usernameFromDB=snapshot.child(userEmail).child("username").getValue(String.class);


                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("username",usernameFromDB);
                        intent.putExtra("email",emailFromDB);
                        intent.putExtra("password",passwordFromDB);
                        intent.putExtra("conpassword",conpassFromDB);
                        intent.putExtra("phoneNo",phoneNoFromDB);

                        startActivity(intent);

                    }
                    else{
                        password.setError("Wrong password");
                    }
                }
                else
                {
                    phone.setError(" No such user");
                    phone.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }

    public void backk(View view) {
        Intent obj = new Intent(this, Open.class);
        startActivity(obj);
    }
}