package com.example.assignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class completedtasks extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ArrayAdapter<String> adapter;
    private ListView CompletedTasklist;
    private Task[] taskarray;
    private List<String> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_completedtasks);
        setupSharedPrefs();
        completedFunction();
    }
    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }
    private void completedFunction(){

    CompletedTasklist =findViewById(R.id.CompletedTasklist);
    List<String> arr = new ArrayList<> ();

    Gson gson = new Gson ();
    String str = prefs.getString ("completed", "");
    taskarray = gson.fromJson (str, Task[].class);

        if (str.equals ("[]") || str.equals ("")) {
            Toast.makeText (getApplicationContext (), "There is no tasks completed yet!", Toast.LENGTH_SHORT).show ();
        } else {
            taskarray = gson.fromJson (str, Task[].class);
            for (int i = 0; i < taskarray.length; i++) {
                arr.add ("Name: " + taskarray[i].getName () +"\nDescripion :" + taskarray[i].getDescription () + "\n");
               }
            adapter = new ArrayAdapter (this, android.R.layout.simple_list_item_1, arr);
            CompletedTasklist.setAdapter (adapter);
             }
}
    public void clickbackCompleted(View view) {
            Intent intent = new Intent (this,MainActivity.class);
            startActivity (intent);
    }

    public void clickADDCompleted(View view) {
        Intent intent = new Intent (this,AddTask.class);
        startActivity (intent);
    }

}
