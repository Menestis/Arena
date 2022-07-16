package fr.ariloxe.arena.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import fr.ariloxe.arena.ArenaAPI;
import org.bukkit.Bukkit;

import java.util.concurrent.ThreadFactory;

/**
 * @author Ariloxe
 */
public class Tasks {

    public static ThreadFactory newThreadFactory(String name) {
        return new ThreadFactoryBuilder().setNameFormat(name).build();
    }

    public static void run(Callable callable) {
        Bukkit.getServer().getScheduler().runTask(ArenaAPI.getApi(), callable::call);
    }

    public static void runAsync(Callable callable) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(ArenaAPI.getApi(), callable::call);
    }

    public static void runLater(Callable callable, long delay) {
        Bukkit.getServer().getScheduler().runTaskLater(ArenaAPI.getApi(), callable::call, delay);
    }

    public static void runAsyncLater(Callable callable, long delay) {
        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(ArenaAPI.getApi(), callable::call, delay);
    }

    public static void runTimer(Callable callable, long delay, long interval) {
        Bukkit.getServer().getScheduler().runTaskTimer(ArenaAPI.getApi(), callable::call, delay, interval);
    }

    public static void runAsyncTimer(Callable callable, long delay, long interval) {
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(ArenaAPI.getApi(), callable::call, delay, interval);
    }

    public interface Callable {
        void call();
    }
}
