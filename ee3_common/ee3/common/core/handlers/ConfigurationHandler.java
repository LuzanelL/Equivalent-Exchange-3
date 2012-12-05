package ee3.common.core.handlers;

import java.io.File;
import java.util.logging.Level;
import cpw.mods.fml.common.FMLLog;
import ee3.common.EquivalentExchange3;
import ee3.common.block.ModBlocks;
import ee3.common.item.ModItems;
import ee3.common.lib.BlockIds;
import ee3.common.lib.ConfigurationSettings;
import ee3.common.lib.ItemIds;
import ee3.common.lib.Reference;
import ee3.common.lib.Strings;
import net.minecraftforge.common.Configuration;
import static net.minecraftforge.common.Configuration.*;

/**
 * ConfigurationManager
 * 
 * Loads in configuration data from disk
 * 
 * @author pahimar
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ConfigurationHandler {

    private static final String CATEGORY_KEYBIND = "keybindings";
    private static final String CATEGORY_GRAPHICS = "graphics";
    private static final String CATEGORY_TRANSMUTATION = "transmutation";
    private static final String CATEGORY_BLOCK_PROPERTIES = Configuration.CATEGORY_BLOCK + Configuration.CATEGORY_SPLITTER + "properties";
    private static final String CATEGORY_RED_WATER_PROPERTIES = CATEGORY_BLOCK_PROPERTIES + Configuration.CATEGORY_SPLITTER + "red_water";
    private static final String CATEGORY_DURABILITY = Configuration.CATEGORY_ITEM + Configuration.CATEGORY_SPLITTER + "durability";

    public static void init(File configFile) {
        Configuration configuration = new Configuration(configFile);

        try {
            configuration.load();

            /* General configs */
            ConfigurationSettings.ENABLE_VERSION_CHECK = configuration
            		.get(CATEGORY_GENERAL, ConfigurationSettings.ENABLE_VERSION_CHECK_CONFIGNAME, ConfigurationSettings.ENABLE_VERSION_CHECK_DEFAULT)
            		.getBoolean(ConfigurationSettings.ENABLE_VERSION_CHECK_DEFAULT);
            ConfigurationSettings.ENABLE_SOUNDS = configuration
            		.get(CATEGORY_GENERAL, ConfigurationSettings.ENABLE_SOUNDS_CONFIGNAME, ConfigurationSettings.ENABLE_SOUNDS_DEFAULT)
            		.getBoolean(ConfigurationSettings.ENABLE_SOUNDS_DEFAULT);

            /* Graphic configs */
            ConfigurationSettings.ENABLE_PARTICLE_FX = configuration
                    .get(CATEGORY_GRAPHICS, ConfigurationSettings.ENABLE_PARTICLE_FX_CONFIGNAME, ConfigurationSettings.ENABLE_PARTICLE_FX_DEFAULT)
                    .getBoolean(ConfigurationSettings.ENABLE_PARTICLE_FX_DEFAULT);
            ConfigurationSettings.ENABLE_OVERLAY_WORLD_TRANSMUTATION = configuration
                    .get(CATEGORY_GRAPHICS, ConfigurationSettings.ENABLE_OVERLAY_WORLD_TRANSMUTATION_CONFIGNAME, ConfigurationSettings.ENABLE_OVERLAY_WORLD_TRANSMUTATION_DEFAULT)
                    .getBoolean(ConfigurationSettings.ENABLE_OVERLAY_WORLD_TRANSMUTATION_DEFAULT);
            
            /* Block configs */
            BlockIds.CALCINATOR =  configuration
            		.getBlock(Strings.CALCINATOR_NAME, BlockIds.CALCINATOR_DEFAULT)
            		.getInt(BlockIds.CALCINATOR_DEFAULT);
            BlockIds.RED_WATER_STILL = configuration
            		.getBlock(Strings.RED_WATER_STILL_NAME, BlockIds.RED_WATER_STILL_DEFAULT)
            		.getInt(BlockIds.RED_WATER_STILL_DEFAULT);
            
            /* Block property configs */
            configuration.addCustomCategoryComment(CATEGORY_BLOCK_PROPERTIES, "Custom block properties");
            
            /* Red Water configs */
            configuration.addCustomCategoryComment(CATEGORY_RED_WATER_PROPERTIES, "Configuration settings for various properties of Red Water");
            ConfigurationSettings.RED_WATER_DURATION_BASE = configuration
                    .get(CATEGORY_RED_WATER_PROPERTIES, ConfigurationSettings.RED_WATER_DURATION_BASE_CONFIGNAME, ConfigurationSettings.RED_WATER_DURATION_BASE_DEFAULT)
                    .getInt(ConfigurationSettings.RED_WATER_DURATION_BASE_DEFAULT);
            ConfigurationSettings.RED_WATER_DURATION_MODIFIER = configuration
                    .get(CATEGORY_RED_WATER_PROPERTIES, ConfigurationSettings.RED_WATER_DURATION_MODIFIER_CONFIGNAME, ConfigurationSettings.RED_WATER_DURATION_MODIFIER_DEFAULT)
                    .getInt(ConfigurationSettings.RED_WATER_DURATION_MODIFIER_DEFAULT);
            ConfigurationSettings.RED_WATER_RANGE_BASE = configuration
                    .get(CATEGORY_RED_WATER_PROPERTIES, ConfigurationSettings.RED_WATER_RANGE_BASE_CONFIGNAME, ConfigurationSettings.RED_WATER_RANGE_BASE_DEFAULT)
                    .getInt(ConfigurationSettings.RED_WATER_RANGE_BASE_DEFAULT);
            ConfigurationSettings.RED_WATER_RANGE_MODIFIER = configuration
                    .get(CATEGORY_RED_WATER_PROPERTIES, ConfigurationSettings.RED_WATER_RANGE_MODIFIER_CONFIGNAME, ConfigurationSettings.RED_WATER_RANGE_MODIFIER_DEFAULT)
                    .getInt(ConfigurationSettings.RED_WATER_RANGE_MODIFIER_DEFAULT);
            

            /* Item configs */
            ItemIds.MINIUM_SHARD = configuration
            		.getItem(Strings.MINIUM_SHARD_NAME, ItemIds.MINIUM_SHARD_DEFAULT)
            		.getInt(ItemIds.MINIUM_SHARD_DEFAULT);
            ItemIds.INERT_STONE = configuration
            		.getItem(Strings.INERT_STONE_NAME, ItemIds.INERT_STONE_DEFAULT)
            		.getInt(ItemIds.INERT_STONE_DEFAULT); 
            ItemIds.MINIUM_STONE = configuration
            		.getItem(Strings.MINIUM_STONE_NAME, ItemIds.MINIUM_STONE_DEFAULT)
            		.getInt(ItemIds.MINIUM_STONE_DEFAULT);            
            ItemIds.PHILOSOPHER_STONE = configuration
            		.getItem(Strings.PHILOSOPHER_STONE_NAME, ItemIds.PHILOSOPHER_STONE_DEFAULT)
            		.getInt(ItemIds.PHILOSOPHER_STONE_DEFAULT);
            ItemIds.ALCHEMY_DUST = configuration
            		.getItem(Strings.ALCHEMY_DUST_NAME, ItemIds.ALCHEMY_DUST_DEFAULT)
            		.getInt(ItemIds.ALCHEMY_DUST_DEFAULT);
            ItemIds.ALCHEMY_BAG = configuration
                    .getItem(Strings.ALCHEMY_BAG_NAME, ItemIds.ALCHEMY_BAG_DEFAULT)
                    .getInt(ItemIds.ALCHEMY_BAG_DEFAULT);
            
            /* Item durability configs */
            ConfigurationSettings.MINIUM_STONE_MAX_DURABILITY = configuration
                    .get(CATEGORY_DURABILITY, ConfigurationSettings.MINIUM_STONE_MAX_DURABILITY_CONFIGNAME, ConfigurationSettings.MINIUM_STONE_MAX_DURABILITY_DEFAULT)
                    .getInt(ConfigurationSettings.MINIUM_STONE_MAX_DURABILITY_DEFAULT);
            ConfigurationSettings.PHILOSOPHERS_STONE_MAX_DURABILITY = configuration
                    .get(CATEGORY_DURABILITY, ConfigurationSettings.PHILOSOPHERS_STONE_MAX_DURABILITY_CONFIGNAME, ConfigurationSettings.PHILOSOPHERS_STONE_MAX_DURABILITY_DEFAULT)
                    .getInt(ConfigurationSettings.PHILOSOPHERS_STONE_MAX_DURABILITY_DEFAULT);

            /* KeyBinding configs */
            configuration.addCustomCategoryComment(CATEGORY_KEYBIND, "Keybindings for Equivalent Exchange 3. See http://www.minecraftwiki.net/wiki/Key_codes for mapping of key codes to keyboard keys");
            EquivalentExchange3.proxy.setKeyBinding(ConfigurationSettings.KEYBINDING_EXTRA, configuration
            		.get(CATEGORY_KEYBIND, ConfigurationSettings.KEYBINDING_EXTRA, ConfigurationSettings.KEYBINDING_EXTRA_DEFAULT)
            		.getInt(ConfigurationSettings.KEYBINDING_EXTRA_DEFAULT));
            EquivalentExchange3.proxy.setKeyBinding(ConfigurationSettings.KEYBINDING_CHARGE, configuration
            		.get(CATEGORY_KEYBIND, ConfigurationSettings.KEYBINDING_CHARGE, ConfigurationSettings.KEYBINDING_CHARGE_DEFAULT)
            		.getInt(ConfigurationSettings.KEYBINDING_CHARGE_DEFAULT));
            EquivalentExchange3.proxy.setKeyBinding(ConfigurationSettings.KEYBINDING_TOGGLE, configuration
            		.get(CATEGORY_KEYBIND, ConfigurationSettings.KEYBINDING_TOGGLE, ConfigurationSettings.KEYBINDING_TOGGLE_DEFAULT)
            		.getInt(ConfigurationSettings.KEYBINDING_TOGGLE_DEFAULT));
            EquivalentExchange3.proxy.setKeyBinding(ConfigurationSettings.KEYBINDING_RELEASE, configuration
            		.get(CATEGORY_KEYBIND, ConfigurationSettings.KEYBINDING_RELEASE, ConfigurationSettings.KEYBINDING_RELEASE_DEFAULT)
            		.getInt(ConfigurationSettings.KEYBINDING_RELEASE_DEFAULT));
            
            /* Transmutation configs */
            ConfigurationSettings.TRANSMUTE_COST_ITEM = configuration
                    .get(CATEGORY_TRANSMUTATION, ConfigurationSettings.TRANSMUTE_COST_ITEM_CONFIGNAME, ConfigurationSettings.TRANSMUTE_COST_ITEM_DEFAULT)
                    .getInt(ConfigurationSettings.TRANSMUTE_COST_ITEM_DEFAULT);
            ConfigurationSettings.TRANSMUTE_COST_BLOCK = configuration
                    .get(CATEGORY_TRANSMUTATION, ConfigurationSettings.TRANSMUTE_COST_BLOCK_CONFIGNAME, ConfigurationSettings.TRANSMUTE_COST_BLOCK_DEFAULT)
                    .getInt(ConfigurationSettings.TRANSMUTE_COST_BLOCK_DEFAULT);
            ConfigurationSettings.TRANSMUTE_COST_MOB = configuration
                    .get(CATEGORY_TRANSMUTATION, ConfigurationSettings.TRANSMUTE_COST_MOB_CONFIGNAME, ConfigurationSettings.TRANSMUTE_COST_MOB_DEFAULT)
                    .getInt(ConfigurationSettings.TRANSMUTE_COST_MOB_DEFAULT);
        }
        catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " has had a problem loading its configuration");
        }
        finally {
            configuration.save();
        }
    }
}
