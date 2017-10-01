package thretcha.fishermansjourney.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thretcha.fishermansjourney.FishermansJourneyMod;
import thretcha.fishermansjourney.entity.EntityBaseFishingHook;
import thretcha.fishermansjourney.item.ItemBaseFishingRod;
import thretcha.fishermansjourney.util.BiomeAndTypeHandler;

import java.nio.file.Path;
import java.nio.file.Paths;
@Mod.EventBusSubscriber
public abstract class CommonProxy {
public static Path configDirectory;
    public void preInit(FMLPreInitializationEvent event)
    {
        configDirectory= Paths.get(event.getModConfigurationDirectory().getPath().toString()+"\\"+ FishermansJourneyMod.MODID);

        System.out.println(configDirectory);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBaseFishingRod());
    }


    public void init(FMLInitializationEvent event)
    {

    }

    public void postInit(FMLPostInitializationEvent event)
    {
        BiomeAndTypeHandler.initBiomeAndTypeHandler();
    }
}
