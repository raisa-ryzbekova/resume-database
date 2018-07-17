package ru.javawebinar.basejava.model;

import java.time.LocalDate;

public class Company {

    private final String companyName;
    private final LocalDate beginDate;
    private final LocalDate endDate;
    private final String position;
    private final String functions;

    public Company(String companyName, LocalDate beginDate, LocalDate endDate, String position, String functions) {
        this.companyName = companyName;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.position = position;
        this.functions = functions;
    }

    @Override
    public String toString() {
        String date = beginDate + " - " + endDate;
        String content = companyName + "\n" + date + " " + position + "\n" + functions;
        return content;
    }
}
