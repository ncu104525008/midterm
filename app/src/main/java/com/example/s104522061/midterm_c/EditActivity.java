package com.example.s104522061.midterm_c;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    private TextView dayView;
    private TextView sectionView;
    private EditText classText;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
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

    private void initView() {
        dayView = (TextView) findViewById(R.id.day);
        sectionView = (TextView) findViewById(R.id.section);
        classText = (EditText) findViewById(R.id.editName);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        Bundle bundle = getIntent().getExtras();
        int day = bundle.getInt("day");
        int section = bundle.getInt("section");
        int dayId[] = {R.string.monday, R.string.tuesday, R.string.wednesday, R.string.thursday, R.string.friday};
        int sectionId[] = {R.string.section1, R.string.section2, R.string.section3, R.string.section4, R.string.section5, R.string.section6};

        dayView.setText(getString(dayId[day]));
        sectionView.setText(getString(sectionId[section]));
    }
}
