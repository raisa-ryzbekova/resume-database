package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class PositionsInCompany {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String position;
    private final String functions;

    public PositionsInCompany(LocalDate startDate, LocalDate endDate, String position, String functions) {
        Objects.requireNonNull(startDate, "start date mustn't be null");
        Objects.requireNonNull(endDate, "end date mustn't be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.functions = functions;
    }

    @Override
    public String toString() {
        return "\n" + "PositionsInCompany{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", position='" + position + '\'' +
                ", functions='" + functions + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionsInCompany that = (PositionsInCompany) o;
        return Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(position, that.position) &&
                Objects.equals(functions, that.functions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, position, functions);
    }
}
