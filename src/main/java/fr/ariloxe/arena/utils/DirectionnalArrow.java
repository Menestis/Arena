package fr.ariloxe.arena.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author Ariloxe
 */
public class DirectionnalArrow {

    public static int distance(Location location1, Location location2) {
        if (location1.getWorld() == location2.getWorld()) {
            double distancez;
            double xp = location1.getBlockX();
            double zp = location1.getBlockZ();
            double xl = location2.getBlockX();
            double zl = location2.getBlockZ();

            double distancex = xp - xl;
            if (distancex < 0.0)
                distancex = -distancex;

            if ((distancez = zp - zl) < 0.0)
                distancez = -distancez;

            double distance = Math.sqrt(Math.pow(distancex, 2.0) + Math.pow(distancez, 2.0));
            return (int) distance;
        }
        return -1;
    }

    public static double angle(Player player, Location location) {
        Location playerLocation = player.getLocation();
        if (playerLocation.getWorld() == location.getWorld()) {
            if (playerLocation.getBlockX() != location.getBlockX() || playerLocation.getBlockZ() != location.getBlockZ()) {
                double xp = playerLocation.getBlockX();
                double zp = playerLocation.getBlockZ();
                double xl = location.getBlockX();
                double zl = location.getBlockZ();
                double distancex = xp - xl;
                double distancecx = distancex < 0.0 ? -distancex : distancex;
                double distancez = zp - zl;
                double distancecz = distancez < 0.0 ? -distancez : distancez;
                double angle = 180.0 * Math.atan(distancecz / distancecx) / 3.141592653589793;
                if (distancex < 0.0 || distancez < 0.0) {
                    if (distancex < 0.0 && distancez >= 0.0)
                        angle = 90.0 - angle + 90.0;
                    else if (distancex <= 0.0 && distancez < 0.0)
                        angle += 180.0;
                    else if (distancex > 0.0)
                        angle = 90.0 - angle + 270.0;
                }

                if ((angle += 270.0) >= 360.0)
                    angle -= 360.0;

                if ((angle -= (player.getEyeLocation().getYaw() + 180.0f)) <= 0.0)
                    angle += 360.0;

                if (angle <= 0.0)
                    angle += 360.0;

                return angle;
            }
            return -1.0;
        }
        return -2.0;
    }

    public static String fleche(double angle) {
        String c = "";
        if (angle == -2.0) {
            c = "۞";
        } else if (angle == -1.0) {
            c = "۞";
        } else if (angle < 22.5 && angle >= 0.0 || angle > 337.5) {
            c = "⬆";
        } else if (angle < 67.5 && angle > 22.5) {
            c = "⬈";
        } else if (angle < 112.5 && angle > 67.5) {
            c = "➡";
        } else if (angle < 157.5 && angle > 112.5) {
            c = "⬊";
        } else if (angle < 202.5 && angle > 157.5) {
            c = "⬇";
        } else if (angle < 247.5 && angle > 202.5) {
            c = "⬋";
        } else if (angle < 292.5 && angle > 247.5) {
            c = "⬅";
        } else if (angle < 337.5 && angle > 292.5) {
            c = "⬉";
        }
        return c;
    }

}
