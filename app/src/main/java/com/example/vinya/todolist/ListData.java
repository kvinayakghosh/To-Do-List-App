package com.example.vinya.todolist;

/**
 * Created by vinya on 10/5/2016.
 */
//Class to create new objects to store title and description
public class ListData {
    String title;
    String description;

    //return the title of each task
    public String getTitle(){
        return title;
    }

    //set the title for each task
    public void setTitle(String task_title) {
        this.title = task_title;
    }

    //return the description of each task
    public String getDescription(){
        return description;
    }

    //set the description of each task
    public void setDescription(String task_description) {
        this.description = task_description;
    }

}

