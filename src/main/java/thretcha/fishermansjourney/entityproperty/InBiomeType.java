package thretcha.fishermansjourney.entityproperty;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.properties.EntityProperty;
import net.minecraftforge.common.BiomeDictionary;
import org.apache.logging.log4j.Level;
import thretcha.fishermansjourney.FishermansJourneyMod;
import thretcha.fishermansjourney.util.BiomeAndTypeHandler;

import java.util.Random;

public class InBiomeType implements EntityProperty {

    //the biome Type the entity should be in for the property to return true
    private final BiomeDictionary.Type biomeType;

    public InBiomeType(String typeString) {
        if (BiomeAndTypeHandler.isValidBiomeType(typeString)) {
            biomeType = BiomeAndTypeHandler.getBiomeType(typeString);
        } else {
            FishermansJourneyMod.logger.log(Level.ERROR, typeString + " mentioned in a Loot_Table is not a valid Biome Type");
            biomeType = null;
        }
    }

    //Currently not working because Loot context is empty
    //Waiting on a forge PR that adds all the entity information the every LootContext
    @Override
    public boolean testProperty(Random random, Entity entityIn) {
        if (BiomeAndTypeHandler.isBiomeOfType(entityIn.getEntityWorld().getBiome(entityIn.getPosition()), biomeType)) {
            return true;
        }
        return false;
    }

    public static class Serializer extends EntityProperty.Serializer<InBiomeType> {
        public Serializer() {
            //super(new ResourceLocation(FishermansJourneyMod.MODID + ":in_biome_type"), InBiomeType.class);
            super(new ResourceLocation(FishermansJourneyMod.MODID ,"in_biome_type"), InBiomeType.class);
        }

        public JsonElement serialize(InBiomeType property, JsonSerializationContext serializationContext) {
            return new JsonPrimitive(property.biomeType.toString());
        }

        public InBiomeType deserialize(JsonElement element, JsonDeserializationContext deserializationContext) {
            return new InBiomeType(JsonUtils.getString(element, "in_biome_type"));
        }
    }
}
