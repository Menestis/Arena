package fr.ariloxe.arena.utils.skull;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class SkullUtils
{
  public static ItemStack getSkullByURL(String url) {
    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
    if (url.isEmpty()) return head;
    
    SkullMeta headMeta = (SkullMeta)head.getItemMeta();
    
    GameProfile profile = new GameProfile(UUID.randomUUID(), "pyralia_head");
    byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[] { url }).getBytes());
    profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
    Field profileField = null;
    try {
      profileField = headMeta.getClass().getDeclaredField("profile");
      profileField.setAccessible(true);
      profileField.set(headMeta, profile);
    } catch (NoSuchFieldException|IllegalArgumentException|IllegalAccessException e1) {
      e1.printStackTrace();
    } 
    head.setItemMeta((ItemMeta)headMeta);
    return head;
  }
  
  public static ItemStack getSkullByURL(String url, String displayName) {
    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
    if (url.isEmpty()) return head;
    
    SkullMeta headMeta = (SkullMeta)head.getItemMeta();
    headMeta.setDisplayName(displayName);

    
    GameProfile profile = new GameProfile(UUID.randomUUID(), "pyralia_head");
    byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[] { url }).getBytes());
    profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
    Field profileField = null;
    
    try {
      profileField = headMeta.getClass().getDeclaredField("profile");
      profileField.setAccessible(true);
      profileField.set(headMeta, profile);
    } catch (NoSuchFieldException|IllegalArgumentException|IllegalAccessException e1) {
      e1.printStackTrace();
    } 
    
    head.setItemMeta((ItemMeta)headMeta);
    return head;
  }
  
  public static ItemStack getSkullByURL(String url, String displayName, String... lores) {
    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
    if (url.isEmpty()) return head;
    
    List<String> lore = new ArrayList<>();
    for (String line : lores) lore.add(line);
    
    SkullMeta headMeta = (SkullMeta)head.getItemMeta();
    headMeta.setDisplayName(displayName);
    
    headMeta.setLore(lore);
    
    GameProfile profile = new GameProfile(UUID.randomUUID(), "pyralia_head");
    byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[] { url }).getBytes());
    profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
    Field profileField = null;
    try {
      profileField = headMeta.getClass().getDeclaredField("profile");
      profileField.setAccessible(true);
      profileField.set(headMeta, profile);
    } catch (NoSuchFieldException|IllegalArgumentException|IllegalAccessException e1) {
      e1.printStackTrace();
    } 
    head.setItemMeta((ItemMeta)headMeta);
    return head;
  }
  
  public static ItemStack getSkullByURL(String url, String displayName, String lores) {
    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
    if (url.isEmpty()) return head;
    
    List<String> lore = new ArrayList<>();
    lore.add(lores);
    
    SkullMeta headMeta = (SkullMeta)head.getItemMeta();
    headMeta.setDisplayName(displayName);
    
    headMeta.setLore(lore);
    
    GameProfile profile = new GameProfile(UUID.randomUUID(), "pyralia_head");
    byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[] { url }).getBytes());
    profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
    Field profileField = null;
    try {
      profileField = headMeta.getClass().getDeclaredField("profile");
      profileField.setAccessible(true);
      profileField.set(headMeta, profile);
    } catch (NoSuchFieldException|IllegalArgumentException|IllegalAccessException e1) {
      e1.printStackTrace();
    } 
    head.setItemMeta((ItemMeta)headMeta);
    return head;
  }
}
