package thretcha.fishermansjourney;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import thretcha.fishermansjourney.proxy.CommonProxy;

@Mod(modid=FishermansJourneyMod.MODID,version=FishermansJourneyMod.VERSION,name=FishermansJourneyMod.NAME, useMetadata = true)

public class FishermansJourneyMod {
    public static final String MODID = "fishermansjourney";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "Fisherman's Journey";
    public static final Boolean DEV_MODE = true;

    @SidedProxy(clientSide="thretcha.fishermansjourney.proxy.ClientProxy", serverSide="thretcha.fishermansjourney.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static FishermansJourneyMod instance;


    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //System.out.println(UUID.randomUUID());
        logger = event.getModLog();
        this.proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        this.proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        this.proxy.postInit(event);
    }
}
