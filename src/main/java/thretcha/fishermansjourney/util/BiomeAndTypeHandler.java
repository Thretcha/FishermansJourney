package thretcha.fishermansjourney.util;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import org.apache.logging.log4j.Level;
import thretcha.fishermansjourney.FishermansJourneyMod;
import thretcha.fishermansjourney.proxy.CommonProxy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

//needs to be refactored
public class BiomeAndTypeHandler {

    public static Set<BiomeDictionary.Type> biomeTypeSet = new HashSet<>();
    public static Set<Biome> biomeSet = new HashSet<>();

    public static Multimap<Biome,BiomeDictionary.Type> typesWithBiomes = HashMultimap.create();
    public static Multimap<BiomeDictionary.Type,Biome> biomesWithType = HashMultimap.create();

    private static Path biomeAndTypeFile=Paths.get(CommonProxy.configDirectory.toString()+"\\BiomeAndTypeList.txt");
    private static Path biomeAndTypeJson=Paths.get(CommonProxy.configDirectory.toString()+"\\BiomesWithTypes.json");
    private static Gson gson =  new GsonBuilder().setPrettyPrinting().create();

    public static void initBiomeAndTypeHandler() {
        try {
            Files.createDirectories(CommonProxy.configDirectory);
        } catch (IOException e) {
            FishermansJourneyMod.logger.log(Level.ERROR,e.getStackTrace());
        }
        try {
            Files.createFile(biomeAndTypeFile);
            Files.createFile(biomeAndTypeJson);
        } catch (IOException e) {
            FishermansJourneyMod.logger.log(Level.ERROR,e.getStackTrace());
        }

        for (Biome b : Biome.REGISTRY)
        {
            biomeSet.add(b);
            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(b);
            for(BiomeDictionary.Type t :types)
            {
               biomeTypeSet.add(t);
               typesWithBiomes.put(b,t);
                biomesWithType.put(t,b);
            }

        }
        if(FishermansJourneyMod.DEV_MODE)
        {
            writeToBiomeAndTypeFile();
            writeToBiomeAndTypeJsonFile();
        }
    }

    private static void writeToBiomeAndTypeFile(){
        StringBuilder stringBuilder =new StringBuilder(1500);

        stringBuilder.append("###################################\n");
        stringBuilder.append("Biomes found during minecraft start\n");
        stringBuilder.append("###################################\n");
        for(Biome b:biomeSet)
        {
            stringBuilder.append(b.getBiomeName()+"\n");
        }
        stringBuilder.append("########################################\n");
        stringBuilder.append("Biome Types found during minecraft start\n");
        stringBuilder.append("########################################\n");
        for(BiomeDictionary.Type t:biomeTypeSet)
        {
            stringBuilder.append(t.getName()+"\n");
        }

        //try-with-resource auto closes file once it's no longer needed
        try(BufferedWriter writer = Files.newBufferedWriter(biomeAndTypeFile, StandardCharsets.UTF_8))
        {
            writer.write(stringBuilder.toString(),0,stringBuilder.toString().length());
        }
        catch (IOException e)
        {
            FishermansJourneyMod.logger.log(Level.ERROR,e.getStackTrace());
        }
    }

    private static void writeToBiomeAndTypeJsonFile(){
        //try-with-resource auto closes file once it's no longer needed
        try(JsonWriter writer =gson.newJsonWriter(Files.newBufferedWriter(biomeAndTypeJson, StandardCharsets.UTF_8))) {
            writer.beginObject();

            writer.name("Biomes");
            writer.beginArray();
            //loop until all biomes are listed
            //for each biome list all their types
            //for each type of this biome list them
            for(Biome b :biomeSet)
            {
                writer.beginObject();
                writer.name(b.getBiomeName());
                writer.beginArray();
                for(BiomeDictionary.Type t :typesWithBiomes.get(b))
                {
                    writer.value(t.getName());
                }
                writer.endArray();
                writer.endObject();
            }
            writer.endArray();

            writer.name("Types");
            writer.beginArray();
            //loop until all types are listed
            //for each type list all the biomes having it
            //for each biome having this type list it
            for(BiomeDictionary.Type t:biomeTypeSet)
            {
                writer.beginObject();
                writer.name(t.getName());
                writer.beginArray();
                for(Biome b:biomesWithType.get(t))
                {
                    writer.value(b.getBiomeName());
                }
                writer.endArray();
                writer.endObject();
            }
            writer.endArray();

            writer.endObject();
            writer.flush();
        } catch (IOException e) {
            FishermansJourneyMod.logger.log(Level.ERROR, e.getStackTrace());
        }
    }
}