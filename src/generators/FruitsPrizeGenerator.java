package generators;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import components.Reel;
import exceptions.InvalidResultException;
import exceptions.InvalidValuesIndexException;
import helpers.Counter;
import interfaces.IPrizeGenerator;
import models.GameResult;
import models.ReelValue;

import java.util.ArrayList;
import java.util.List;

public class FruitsPrizeGenerator implements IPrizeGenerator {
    private List<Reel> reels;
    private List<ReelValue> reelsValues;
    private FruitsMultiplier multiplier;

    public FruitsPrizeGenerator() {
        reels = new ArrayList<>();
    }

    public void setReels(List<Reel> reels) {
        this.reels = reels;
    }

    public void setReelsValues(List<ReelValue> reelsValues) {
        this.reelsValues = reelsValues;
    }

    public void loadConfig(JsonArray multipliers){
        Gson gson = new Gson();
        List<FruitsMultiplier> temp = gson.fromJson(multipliers,new TypeToken<List<FruitsMultiplier>>(){}.getType());
        multiplier = temp.get(temp.indexOf(new FruitsMultiplier(reels.size())));

    }

    @Override
    public GameResult generatePrize(int bet, List<Integer> reelsResult) throws InvalidResultException, InvalidValuesIndexException {

        if(reelsResult.size() != reels.size())
            throw new InvalidResultException("Reels Count: " + reels.size() + " - Results Count: " + reelsResult.size());

        List<String> resultValues = new ArrayList<>();

        for (int i = 0; i < reelsResult.size(); i++) {

            List<ReelValue> values = reels.get(i).getValues();
            int valueIndex = reelsResult.get(i);

            if(valueIndex < 0 || valueIndex >= values.size())
                throw new InvalidValuesIndexException("Reel Values count: " + values.size() + " - Requested Index: " + valueIndex);

            resultValues.add(values.get(valueIndex).getCode());
        }

        Counter<String> counter = new Counter<>();
        for (String value: resultValues)
            counter.count(value);

        String mostCommon = counter.getMostCommon();
        int mostCommonCount = counter.get(mostCommon);
        int x = multiplier.getMultipliers()[mostCommonCount-1];
        int mostCommonPrize = reelsValues.get(reelsValues.indexOf(new ReelValue(mostCommon))).getPrize();

        int prize =  mostCommonPrize * mostCommonCount * x + (x * bet);

        GameResult gameResult = new GameResult();
        gameResult.setPrize(prize);
        gameResult.setValues(resultValues);
        gameResult.setBet(bet);

        return gameResult;
    }
}

