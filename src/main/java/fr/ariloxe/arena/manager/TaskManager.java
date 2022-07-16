package fr.ariloxe.arena.manager;

import fr.ariloxe.arena.listeners.task.*;
import fr.ariloxe.arena.listeners.task.ChangeMapTask;
import fr.ariloxe.arena.listeners.task.RegularTask;
import fr.ariloxe.arena.listeners.task.ResetTask;

/**
 * @author Ariloxe
 */
public class TaskManager {

    public TaskManager(){
        new RegularTask();
        new ResetTask();
        //new TabTask();
        new ChangeMapTask();
    }

}
