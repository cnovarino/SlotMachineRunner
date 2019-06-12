import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import components.Reel;
import components.SlotMachine;
import exceptions.ConfigFileNotFoundException;
import generators.FruitsPrizeGenerator;
import models.ReelValue;
import slotmachine.ui.view.IReelAdapter;
import slotmachine.ui.view.IView;
import slotmachine.ui.view.SlotMachineViewFacade;
import views.FruitView;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Gson gson = new Gson();

            Settings settings = Settings.getInstance("fruits_reel_config.json");

            SlotMachine slotMachine = SlotMachine.getInstance();

            JsonArray reelValuesArray = settings.getSettings().getAsJsonArray("reelValues");
            List<ReelValue> reelValues = gson.fromJson(reelValuesArray,new TypeToken<ArrayList<ReelValue>>(){}.getType());

            JsonArray reelsArray = settings.getSettings().getAsJsonArray("reels");
            List<Reel> reels = gson.fromJson(reelsArray,new TypeToken<ArrayList<Reel>>(){}.getType());

            FruitsPrizeGenerator prizeGenerator = new FruitsPrizeGenerator();

            prizeGenerator.setReels(reels);
            prizeGenerator.setReelsValues(reelValues);
            prizeGenerator.loadConfig(settings.getSettings().get("multipliers").getAsJsonArray());

            slotMachine.loadConfig(reels,reelValues,prizeGenerator);

            SlotMachineViewFacade.setPlayHandler(slotMachine);
            SlotMachineViewFacade.setCreditHandler(slotMachine);
            SlotMachineViewFacade.setGameModeHandler(slotMachine);

            slotMachine.setiDisplayHandler(SlotMachineViewFacade.getDisplayHandler());
            slotMachine.setiPrizeHandler(SlotMachineViewFacade.getPrizeHandler());
            slotMachine.setReelHandler(SlotMachineViewFacade.getReelHandler());

            SlotMachineViewFacade.setReelAdapter(new IReelAdapter() {
                @Override
                public int getCount() {
                    return reels.size();
                }

                @Override
                public IView getView(int position) {
                    return new FruitView(reels.get(position).getCurrentValue());
                }
            });

            SlotMachineViewFacade.show();


        } catch (ConfigFileNotFoundException e) {
            e.printStackTrace();
        }



    }


}
