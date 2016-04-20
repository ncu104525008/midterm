package com.example.s104522061.midterm_c;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button showName;
    private Button classBtn[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showName();
        initClassBtn();
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
            Intent intent = new Intent(MainActivity.this, NameActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if(id == R.id.action_quit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showName() {
        showName = (Button) findViewById(R.id.name);
        SharedPreferences settings = getSharedPreferences("settings", 0);
        String userName = settings.getString("userName", "");
        showName.setText(userName);
    }

    private void initClassBtn() {
        int btnID[][] = {
                {R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16},
                {R.id.btn21, R.id.btn22, R.id.btn23, R.id.btn24, R.id.btn25, R.id.btn26},
                {R.id.btn31, R.id.btn32, R.id.btn33, R.id.btn34, R.id.btn35, R.id.btn36},
                {R.id.btn41, R.id.btn42, R.id.btn43, R.id.btn44, R.id.btn45, R.id.btn46},
                {R.id.btn51, R.id.btn52, R.id.btn53, R.id.btn54, R.id.btn55, R.id.btn56}};
        classBtn = new Button[5][6];
        for(int i=0;i<5;i++) {
            for(int j=0;j<6;j++) {
                classBtn[i][j] = (Button) findViewById(btnID[i][j]);
                classBtn[i][j].setOnClickListener(new addClass(i, j));
            }
        }
    }

    private class addClass implements Button.OnClickListener {
        private int day;
        private int section;

        public addClass(int day, int section) {
            this.day = day;
            this.section = section;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("day", this.day);
            bundle.putInt("section", this.section);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }
}
