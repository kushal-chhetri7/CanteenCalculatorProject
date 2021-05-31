package gcit.edu.personalcanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VarifyNum extends AppCompatActivity {
    String verificationCodeBySystem;
    Button varify_btn;
    EditText phoneNoEnteredByTheUser;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varify_num);

        varify_btn=findViewById(R.id.verify_btn);
        phoneNoEnteredByTheUser=findViewById(R.id.verification_code_by_user);
        progressbar=findViewById(R.id.progressBar2);
        progressbar.setVisibility(View.INVISIBLE);


        String phoneNo=getIntent().getStringExtra("phoneNo");

        sendVerificationCodeToUser(phoneNo);

        varify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=phoneNoEnteredByTheUser.getText().toString();

                if(code.isEmpty() || code.length()<6){
                    phoneNoEnteredByTheUser.setError("Wrong OTP..");
                    phoneNoEnteredByTheUser.requestFocus();
                    return;
                }else{
                    progressbar.setVisibility(View.VISIBLE);
                    verifyCode(code);
                }
            }
        });


    }

    private void sendVerificationCodeToUser(String phoneNo) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber("+975" + phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(VarifyNum.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull  String s, @NonNull  PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationCodeBySystem=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull  PhoneAuthCredential phoneAuthCredential) {


             String code =phoneAuthCredential.getSmsCode();
             if(code!=null){
                 progressbar.setVisibility(View.VISIBLE);
                 verifyCode(code);
             }
        }

        @Override
        public void onVerificationFailed(@NonNull  FirebaseException e) {
            Toast.makeText(VarifyNum.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String codeByUser){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationCodeBySystem,codeByUser);
        signInTheUserByCredentials(credential);
    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(VarifyNum.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else
                        {
                            Toast.makeText(VarifyNum.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void close(View view) {
        Intent intent=new Intent(this,Open.class);
        startActivity(intent);
    }
}