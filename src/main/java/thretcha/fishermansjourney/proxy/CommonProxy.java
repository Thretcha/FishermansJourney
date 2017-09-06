package thretcha.fishermansjourney.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import thretcha.fishermansjourney.FishermansJourneyMod;
import thretcha.fishermansjourney.util.BiomeAndTypeHandler;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class CommonProxy {
public static Path configDirectory;
    public void preInit(FMLPreInitializationEvent event)
    {
        configDirectory= Paths.get(event.getModConfigurationDirectory().getPath().toString()+"\\"+ FishermansJourneyMod.MODID);

        System.out.println(configDirectory);
    }

    public void init(FMLInitializationEvent event)
    {

    }

    public void postInit(FMLPostInitializationEvent event)
    {
        BiomeAndTypeHandler.initBiomeAndTypeHandler();
    }
}
