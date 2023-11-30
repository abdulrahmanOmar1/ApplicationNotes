package com.example.assignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class ToDoTask extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ArrayAdapter<String> adapter;
    private Task[] taskarray;
    private ListView listView;
    String str;
    private List<String> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_to_do_task);
        setupSharedPrefs();
        ToDoFunction();
    }

    public void ToDoFunction(){
        listView = (ListView) findViewById (R.id.listToDo);
        arraylist = new ArrayList<> ();

        Gson gson = new Gson ();
         str = prefs.getString ("data", "");

        if ((str.equals("")) || (str.equals("[]"))) {
            Toast.makeText (getApplicationContext (), "There is no task yet!", Toast.LENGTH_SHORT).show ();
        } else{
            taskarray = gson.fromJson (str, Task[].class);
            for (int i = 0; i < taskarray.length; i++) {
                arraylist.add ("Name: " + taskarray[i].getName () + "\nDescripion :" + taskarray[i].getDescription () + "\n");
            }
            adapter = new ArrayAdapter <>(this, android.R.layout.simple_list_item_multiple_choice, arraylist);
            listView.setAdapter (adapter);
        }
    }
    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }
    public void clickback(View view) {

        int j=0 , i=0 , c1=0 ,c2=0 , x=0;
        if(str.equals("") || str.equals("[]")) {

        }else{
            for (i = 0; i < taskarray.length; i++) {
                if (!listView.isItemChecked (i)) {
                    j++;
                }
            }
            Task[] uncompletedTask = new Task[j];
            int size1 = taskarray.length - j;
            Task[] completeTask = new Task[size1];


            for (x = 0; x < taskarray.length; x++) {
                if (!listView.isItemChecked (x)) {
                    uncompletedTask[c1] = taskarray[x];
                    c1++;
                } else {
                    completeTask[c2] = taskarray[x];
                    c2++;
                }
            }

            Gson gson = new Gson ();
            String uncompletedGsonStr = gson.toJson (uncompletedTask);
            String completeGsonStr = gson.toJson (completeTask);

            editor.putString ("data", uncompletedGsonStr);
            editor.putString ("completed", completeGsonStr);
            editor.commit ();
        }
        Intent in=new Intent(this,MainActivity.class);
        startActivity(in);
    }


}



