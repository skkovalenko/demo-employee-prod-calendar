package org.desktop.parse;

import org.desktop.model.TypeDay;
import org.springframework.stereotype.Component;

public class DayForProdCalendar {
    private int number;
    private TypeDay typeDay;


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public TypeDay getTypeDay() {
        return typeDay;
    }

    public void setTypeDay(TypeDay typeDay) {
        this.typeDay = typeDay;
    }
}
