package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Company {

    private final Link homePage;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String position;
    private final String functions;

    public Company(String name, String url, LocalDate startDate, LocalDate endDate, String position, String functions) {
        Objects.requireNonNull(startDate, "Start date mustn't be null");
        Objects.requireNonNull(endDate, "End date mustn't be null");
        this.homePage = new Link(name, url);
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.functions = functions;
    }

    @Override
    public String toString() {
        return "Company{" +
                "homePage=" + homePage +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", position='" + position + '\'' +
                ", functions='" + functions + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(homePage, company.homePage) &&
                Objects.equals(startDate, company.startDate) &&
                Objects.equals(endDate, company.endDate) &&
                Objects.equals(position, company.position) &&
                Objects.equals(functions, company.functions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, startDate, endDate, position, functions);
    }
}
