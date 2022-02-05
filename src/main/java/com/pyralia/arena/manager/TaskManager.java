package com.pyralia.arena.manager;

import com.pyralia.arena.listeners.task.ChangeMapTask;
import com.pyralia.arena.listeners.task.RegularTask;
import com.pyralia.arena.listeners.task.ResetTask;
import com.pyralia.arena.listeners.task.TabTask;

/**
 * @author Ariloxe
 */
public class TaskManager {

    public TaskManager(){
        new RegularTask();
        new ResetTask();
        new TabTask();
        new ChangeMapTask();
    }

}
