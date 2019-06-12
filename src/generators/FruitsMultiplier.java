package generators;

import java.util.Arrays;
import java.util.Objects;

public class FruitsMultiplier {
    private int reels;
    private int[] multipliers;

    public FruitsMultiplier(int reels) {
        this.reels = reels;
        multipliers = new int[reels];
    }

    public int[] getMultipliers() {
        return multipliers;
    }

    @Override
    public String toString() {
        return "Multiplier{" +
                "reels=" + reels +
                ", multipliers=" + Arrays.toString(multipliers) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FruitsMultiplier that = (FruitsMultiplier) o;
        return reels == that.reels;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reels);
    }
}
