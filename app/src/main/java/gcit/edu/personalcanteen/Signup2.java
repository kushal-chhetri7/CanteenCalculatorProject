package gcit.edu.personalcanteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Signup2 extends AppCompatActivity {
    ImageView backBtn, background;
    Button next,login;
    TextView titletext,welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        backBtn=findViewById(R.id.backbotton);
        background=findViewById(R.id.background);
        titletext=findViewById(R.id.signup_title_text);
        welcome=findViewById(R.id.welcome);
        next=findViewById(R.id.signup_next_button);
        login=findViewById(R.id.signup_login_button);
    }

    public void callNextSignupScreen(View view) {


    }

    public void signup(View view) {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }
}