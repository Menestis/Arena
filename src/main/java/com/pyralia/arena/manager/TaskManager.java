package com.pyralia.arena.manager;

import com.pyralia.arena.listeners.task.*;

/**
 * @author Ariloxe
 */
public class TaskManager {

    public TaskManager(){
        new RegularTask();
        new ResetTask();
        new TabTask();
        new ChangeMapTask();
        new LeaderboardTask();
    }

}
