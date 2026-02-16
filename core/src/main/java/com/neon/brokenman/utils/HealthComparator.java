package com.neon.brokenman.utils;

import java.time.LocalDate;

public class HealthComparator implements Comparable {

    private Integer health;

    public HealthComparator(int health) {
        this.health = health;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(this.health, (Integer) o);
    }

    public Integer getHealth() {
        return health;
    }

    public LocalDate getDateOfHealthUpdate() {
        LocalDate nowDate = LocalDate.now();
        nowDate =  nowDate.plusDays(1);
        return nowDate;
    }
}
