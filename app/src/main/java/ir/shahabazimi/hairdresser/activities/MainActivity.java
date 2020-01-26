package ir.shahabazimi.hairdresser.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ir.shahabazimi.hairdresser.R;
import ir.shahabazimi.hairdresser.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container,new MainFragment()).commit();
    }
}
