package generators;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import components.Reel;
import exceptions.InvalidResultException;
import exceptions.InvalidValuesIndexException;
import models.GameResult;
import models.ReelValue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FruitsPrizeGeneratorTest {

    @Test
    void loadConfig() {
        Gson gson = new Gson();
        JsonArray config = new JsonArray();
        JsonObject mult = new JsonObject();

        List<ReelValue> values = new ArrayList<>();
        values.add(new ReelValue("a"));
        values.add(new ReelValue("b"));
        values.add(new ReelValue("c"));

        List<Reel> reels = new ArrayList();
        reels.add(new Reel(values));
        reels.add(new Reel(values));
        reels.add(new Reel(values));

        List<Integer> multipliers1 = new ArrayList<>();
        multipliers1.add(0);
        multipliers1.add(10);
        multipliers1.add(20);
        int reels1 = 3;
        mult.addProperty("reels",reels1);
        mult.add("multipliers", gson.toJsonTree(multipliers1).getAsJsonArray());
        config.add(mult);

        FruitsPrizeGenerator prizeGenerator = new FruitsPrizeGenerator();
        prizeGenerator.setReels(reels);
        prizeGenerator.setReelsValues(values);
        prizeGenerator.loadConfig(config);

        assertEquals(3,prizeGenerator.getMultiplier().getReels());

    }

    @Test
    void generatePrize() {
        Gson gson = new Gson();
        JsonArray config = new JsonArray();
        JsonObject mult = new JsonObject();

        List<ReelValue> values = new ArrayList<>();
        ReelValue v1 = new ReelValue("a");
        v1.setPrize(10);
        ReelValue v2 = new ReelValue("b");
        v2.setPrize(20);
        ReelValue v3 = new ReelValue("c");
        v3.setPrize(30);
        values.add(v1);
        values.add(v2);
        values.add(v3);

        List<Reel> reels = new ArrayList();
        reels.add(new Reel(values));
        reels.add(new Reel(values));
        reels.add(new Reel(values));

        List<Integer> multipliers1 = new ArrayList<>();
        multipliers1.add(0);
        multipliers1.add(10);
        multipliers1.add(20);
        int reels1 = 3;
        mult.addProperty("reels",reels1);
        mult.add("multipliers", gson.toJsonTree(multipliers1).getAsJsonArray());
        config.add(mult);

        FruitsPrizeGenerator prizeGenerator = new FruitsPrizeGenerator();
        prizeGenerator.setReels(reels);

        prizeGenerator.setReelsValues(values);
        prizeGenerator.loadConfig(config);

        List<String> expectedList = new ArrayList<>();
        expectedList.add("b");
        expectedList.add("a");
        expectedList.add("b");
        GameResult expected = new GameResult();
        expected.setBet(100);
        expected.setPrize(20*2*10+10*100);
        expected.setValues(expectedList);

        List<Integer> reelResults = new ArrayList<>();
        reelResults.add(1);
        reelResults.add(0);
        reelResults.add(1);

        try {
            assertEquals(expected, prizeGenerator.generatePrize(100, reelResults));
        } catch (InvalidResultException e) {
            e.printStackTrace();
        } catch (InvalidValuesIndexException e) {
            e.printStackTrace();
        }

    }
}