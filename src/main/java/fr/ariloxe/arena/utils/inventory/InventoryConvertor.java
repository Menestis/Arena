package fr.ariloxe.arena.utils.inventory;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Ariloxe
 */
public class InventoryConvertor {

    public static String inventoryToBase64(ItemStack[] itemStack) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(itemStack.length);
            for (int i = 0; i < itemStack.length; i++) {
                dataOutput.writeObject(itemStack[i]);
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (Exception e) {
            throw new IllegalStateException("Could not convert inventory to base64.", e);
        }
    }

    public static ItemStack[] inventoryFromBase64(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] itemStacks = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < itemStacks.length; i++)
                itemStacks[i] = (ItemStack)dataInput.readObject();

            dataInput.close();
            return itemStacks;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to decode the class type.", e);
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert Inventory to Base64.", e);
        }
    }

}
