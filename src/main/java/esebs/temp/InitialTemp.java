package esebs.temp;

import io.smallrye.common.constraint.NotNull;

public class InitialTemp {
    @NotNull
    public Integer temperature;
    @NotNull
    public String unit;

    public InitialTemp() {}
    public InitialTemp(int temperature, String unit) {
        this.temperature = temperature;
        this.unit = unit;
    }

}
