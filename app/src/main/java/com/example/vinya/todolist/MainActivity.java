package com.example.vinya.todolist;

import android.app.Activity;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.view.Gravity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;


public class MainActivity extends AppCompatActivity {

    //Declaring variables to refer to the custom adapter,listview and the arraylist of objects
    CustomAdapter adapter;
    ListView lView;
    Context context = MainActivity.this;
    ArrayList<ListData> myList = new ArrayList<ListData>();
    String fileName = "todolist";   //File that will be created in the internal storage of the phone

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Code to load the custom layout with the 'To Do List' title
        LinearLayout custom_title = (LinearLayout) findViewById(R.id.custom_bar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_titlebar);

        //Creating an adapter anf loading the custom listview to that adapter
        adapter = new CustomAdapter(context, myList);
        lView = (ListView) findViewById(R.id.list_tasks);
        loadDataInList();
        lView.setAdapter(adapter);


    }

    //Function to read data from the text file and load it to the ListView
    private void loadDataInList() {

        Scanner scan = null;
        try {
            scan = new Scanner(openFileInput(fileName));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] details = line.split("\t");
                String titleLine = details[0];
                String descLine = details[1];
                ListData lData = new ListData();
                lData.setTitle(titleLine);
                lData.setDescription(descLine);
                myList.add(lData);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

//Function to listen to the Add button click listener
    public void addItem(View view) {
        EditText Title = (EditText) findViewById(R.id.newTitle);
        EditText Description = (EditText) findViewById(R.id.editText);
        String newTitle = Title.getText().toString();
        String newDescript = Description.getText().toString();
        String empt = new String();
        //condition to check if no title or description is given to a task and button is clicked
        if(newTitle.equals(empt) && newDescript.equals(empt)){
            Toast.makeText(context, "Nothing to do? Really?!", Toast.LENGTH_LONG).show();
            return;}
        ListData newData = new ListData();
        newData.setTitle(newTitle);
        newData.setDescription(newDescript);
        myList.add(newData);
        Title.setText("");
        Description.setText("");
        Toast.makeText(context, "Item added!", Toast.LENGTH_LONG).show();
        adapter.notifyDataSetChanged();
        //Write the newly added data into the text file
        try{

            OutputStreamWriter out=new OutputStreamWriter(openFileOutput(fileName,MODE_APPEND));
            BufferedWriter bw = new BufferedWriter(out);
            bw.write(newTitle + "\t" + newDescript);
            bw.newLine();
            bw.close();

        }catch (Exception e) {
            System.out.println("Error in adding item! " + e.getMessage());
        }
        adapter.notifyDataSetChanged();

    }


}

