package com.example.vinya.todolist;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vinya on 10/4/2016.
 */


//Create a custom adapter that extends a BaseAdapter
public class CustomAdapter extends BaseAdapter {

    //Create a variable to represent the arraylist and the inflator
    ArrayList<ListData> myList = new ArrayList<ListData>();
    LayoutInflater inflator;
    Context context;

    //Custom adapter constructor
    public CustomAdapter(Context context, ArrayList<ListData> myList){
        this.myList = myList;
        this.context = context;
        inflator = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public ListData getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflator.inflate(R.layout.create_listview,null);
        }
        //Load the widgets into the view
        TextView title = (TextView) convertView.findViewById(R.id.task_title);
        title.setText(myList.get(position).getTitle());
        TextView description = (TextView) convertView.findViewById(R.id.task_description);
        description.setText(myList.get(position).getDescription());
        final CheckBox checked = (CheckBox) convertView.findViewById(R.id.checkBox);
        //On longclicklistener to delete the task item being clicked for a long time
        convertView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                myList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Item Deleted!", Toast.LENGTH_LONG).show();
                updateFile(myList);
                return false;
            }
            });

        //onClickListener for the checkbox being clicked
        checked.setOnClickListener(
                new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Integer index = (Integer) convertView.getTag();
                        myList.remove(position);
                       checked.setChecked(false);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Item Deleted!", Toast.LENGTH_LONG).show();
                        updateFile(myList);
                    }
                }
        );
        return convertView;
    }



//Method to update the file based on checkbox on Long Click on items
public void updateFile(ArrayList<ListData> myList) {

    this.myList = myList;
    int listSize = myList.size();
    String title;
    String desc;
    String fileName = "todolist";
    try {
        OutputStreamWriter out2 = new OutputStreamWriter(context.openFileOutput(fileName, context.MODE_PRIVATE));
        BufferedWriter bw2 = new BufferedWriter(out2);
        bw2.write("");
        bw2.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
//write all the updated ArrayList elements into the file
    for (int i = 0; i < listSize; i++) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput(fileName, context.MODE_APPEND));
            BufferedWriter bw = new BufferedWriter(out);
            title = myList.get(i).getTitle();
            desc = myList.get(i).getDescription();
            bw.write(title + "\t" + desc);
            bw.newLine();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

}

