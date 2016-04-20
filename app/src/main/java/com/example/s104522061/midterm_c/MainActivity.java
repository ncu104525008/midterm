package com.example.s104522061.midterm_c;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button showName;
    private Button classBtn[][];

    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = MyDBHelper.getDatabase(MainActivity.this);

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
                classBtn[i][j].setOnLongClickListener(new deleteClass(i, j));
                classBtn[i][j].setText(getClassName(i, j));
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

    private class deleteClass implements Button.OnLongClickListener {
        private int day;
        private int section;

        public deleteClass(int day, int section) {
            this.day = day;
            this.section = section;
        }

        @Override
        public boolean onLongClick(final View v) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(getString(R.string.deleteClass))
                    .setMessage(getString(R.string.suretodeleteClass))
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ContentValues cv = new ContentValues();
                                    cv.put(MyDBHelper.NAME_COLUMN, "");
                                    String where = "day=" + day + " AND section=" + section;

                                    db.update(MyDBHelper.TABLE_NAME, cv, where, null);
                                    showChangeMessage();
                                    Button btn = (Button) findViewById(v.getId());
                                    btn.setText("");
                                }
                            })
                            .setNeutralButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();

            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showChangeMessage() {
        final int notifyID = 1; // 通知的識別號碼
        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); // 取得系統的通知服務
        final Notification notification = new Notification.Builder(getApplicationContext()).setSmallIcon(android.R.drawable.ic_dialog_alert).setContentTitle("我的課表").setContentText("課表更新完成").build(); // 建立通知
        notificationManager.notify(notifyID, notification); // 發送通知
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
}
