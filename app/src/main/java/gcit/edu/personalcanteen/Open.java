package gcit.edu.personalcanteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Open extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
    }

    public void canteen(View view) {
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
    }

    public void customer(View view) {
        Intent intent=new Intent(this,Registration.class);
        startActivity(intent);
    }
}