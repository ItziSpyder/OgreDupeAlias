package fun.ogre.ogredupealias.utils.raytracers;

import fun.ogre.ogredupealias.OgreDupeAlias;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.util.Consumer;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class BlockDisplayRaytracer {

    private static final OgreDupeAlias system = OgreDupeAlias.instance;

    public static void outline(Material display, Location location, long stayTime) {
        outline(display, location, 0.05, stayTime);
    }

    public static void outline(Material display, Location location, double thickness, long stayTime) {
        Location og = location.getBlock().getLocation();

        Location a1 = og.clone().add(0, 0, 0);
        Location a2 = og.clone().add(1, 0, 0);
        Location a3 = og.clone().add(1, 0, 1);
        Location a4 = og.clone().add(0, 0, 1);

        Location b1 = og.clone().add(0, 1, 0);
        Location b2 = og.clone().add(1, 1, 0);
        Location b3 = og.clone().add(1, 1, 1);
        Location b4 = og.clone().add(0, 1, 1);

        trace(display, a1, a2, thickness, stayTime);
        trace(display, a2, a3, thickness, stayTime);
        trace(display, a3, a4, thickness, stayTime);
        trace(display, a4, a1, thickness, stayTime);

        trace(display, b1, b2, thickness, stayTime);
        trace(display, b2, b3, thickness, stayTime);
        trace(display, b3, b4, thickness, stayTime);
        trace(display, b4, b1, thickness, stayTime);

        trace(display, a1, b1, thickness, stayTime);
        trace(display, a2, b2, thickness, stayTime);
        trace(display, a3, b3, thickness, stayTime);
        trace(display, a4, b4, thickness, stayTime);
    }

    public static void trace(Material display, Location start, Location end, long stayTime) {
        trace(display, start, end.toVector().subtract(start.toVector()), 0.05, end.distance(start), stayTime);
    }

    public static void trace(Material display, Location start, Location end, double thickness, long stayTime) {
        trace(display, start, end.toVector().subtract(start.toVector()), thickness, end.distance(start), stayTime);
    }

    public static void trace(Material display, Location start, Vector direction, double thickness, double distance, long stayTime) {
        World world = start.getWorld();

        BlockDisplay beam = world.spawn(start, BlockDisplay.class, entity -> {
            AxisAngle4f angle = new AxisAngle4f(0, 0, 0, 1);
            Vector3f transition = new Vector3f(-(float)(thickness / 2F));
            Vector3f scale = new Vector3f((float)thickness, (float)thickness, (float)distance);
            Transformation trans = new Transformation(transition, angle, scale, angle);
            Location vector = entity.getLocation();

            vector.setDirection(direction);
            entity.teleport(vector);
            entity.setBlock(display.createBlockData());
            entity.setBrightness(new Display.Brightness(15, 15));
            entity.setInterpolationDelay(0);
            entity.setTransformation(trans);

            Bukkit.getScheduler().runTaskLater(system, entity::remove, stayTime);
        });
    }

    public static void trace(Material display, Location start, Vector direction, double thickness, double distance, long stayTime, Consumer<BlockDisplay> onEntitySpawn) {
        World world = start.getWorld();

        BlockDisplay beam = world.spawn(start, BlockDisplay.class, entity -> {
            AxisAngle4f angle = new AxisAngle4f(0, 0, 0, 1);
            Vector3f transition = new Vector3f(-(float)(thickness / 2F));
            Vector3f scale = new Vector3f((float)thickness, (float)thickness, (float)distance);
            Transformation trans = new Transformation(transition, angle, scale, angle);
            Location vector = entity.getLocation();

            vector.setDirection(direction);
            entity.teleport(vector);
            entity.setBlock(display.createBlockData());
            entity.setBrightness(new Display.Brightness(15, 15));
            entity.setInterpolationDelay(0);
            entity.setTransformation(trans);

            Bukkit.getScheduler().runTaskLater(system, entity::remove, stayTime);
            Bukkit.getScheduler().runTaskLater(system, () -> onEntitySpawn.accept(entity), 5);
        });
    }
}
