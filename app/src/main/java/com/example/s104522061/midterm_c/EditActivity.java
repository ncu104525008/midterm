package com.example.s104522061.midterm_c;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    private TextView dayView;
    private TextView sectionView;
    private EditText classText;
    private Button saveBtn;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = MyDBHelper.getDatabase(EditActivity.this);

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
        classText = (EditText) findViewById(R.id.className);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        Bundle bundle = getIntent().getExtras();
        int day = bundle.getInt("day");
        int section = bundle.getInt("section");
        int dayId[] = {R.string.monday, R.string.tuesday, R.string.wednesday, R.string.thursday, R.string.friday};
        int sectionId[] = {R.string.section1, R.string.section2, R.string.section3, R.string.section4, R.string.section5, R.string.section6};

        dayView.setText(getString(dayId[day]));
        sectionView.setText(getString(sectionId[section]));
        classText.setText(getClassName(day, section));

        saveBtn.setOnClickListener(new updateClass(day, section));
    }

    private String getClassName(int day, int section) {
        String className = "";
        String query[] = {"name"};
        String where = "day=" + day + " AND section=" + section;

        Cursor cursor = db.query(MyDBHelper.TABLE_NAME, query, where, null, null, null, null, null);

        while(cursor.moveToNext()) {
            className = cursor.getString(0);
        }

        return className;
    }

    private class updateClass implements Button.OnClickListener {
        private int day;
        private int section;

        public updateClass(int day, int section) {
            this.day = day;
            this.section = section;
        }

        @Override
        public void onClick(View view) {
            ContentValues cv = new ContentValues();
            cv.put(MyDBHelper.NAME_COLUMN, classText.getText().toString());
            String where = "day=" + day + " AND section=" + section;

            db.update(MyDBHelper.TABLE_NAME, cv, where, null);

            showChangeMessage();
            Intent intent = new Intent(EditActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showChangeMessage() {
        final int notifyID = 1;
        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final Notification notification = new Notification.Builder(getApplicationContext())
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.updatedone))
                .build();
        notificationManager.notify(notifyID, notification);
    }
}
