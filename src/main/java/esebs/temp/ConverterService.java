package esebs.temp;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ConverterService {
    public Integer toFarenheit(int temp, String unit) {
        if (unit.equals("F")) {
            return temp;
        } else if (unit.equals("C")) {
            return (((temp * 9) / 5) + 32);
        } else {
            return -99999999;
        }
    }

    public Integer toCelsius(int temp, String unit) {
        if (unit.equals("C")) {
            return temp;
        } else if (unit.equals("F")) {
            return (((temp - 32) * 5) / 9);
        } else {
            return -99999999;
        }
    }
}
