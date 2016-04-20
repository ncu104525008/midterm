package com.example.s104522061.midterm_c;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NameActivity extends AppCompatActivity {
    private Button btnSave;
    private EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSave = (Button) findViewById(R.id.saveBtn);
        editName = (EditText) findViewById(R.id.editName);

        btnSave.setOnClickListener(new saveName());
        showName();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_quit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private class saveName implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            SharedPreferences settings = getSharedPreferences("settings", 0);
            settings.edit().putString("userName", editName.getText().toString()).apply();

            Intent intent = new Intent(NameActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void showName() {
        SharedPreferences settings = getSharedPreferences("settings", 0);
        String userName = settings.getString("userName", "");
        editName.setText(userName);
    }
}
