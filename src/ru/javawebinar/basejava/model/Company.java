package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class Company {

    private final Link homePage;
    private final List<PositionsInCompany> positionsInCompanyList;

    public Company(String name, String url, List<PositionsInCompany> positionsInCompanies) {
        this.homePage = new Link(name, url);
        this.positionsInCompanyList = positionsInCompanies;
    }

    @Override
    public String toString() {
        return "\n" + "Company{" +
                "homePage=" + homePage +
                ", positionsInCompanyList=" + positionsInCompanyList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(homePage, company.homePage) &&
                Objects.equals(positionsInCompanyList, company.positionsInCompanyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positionsInCompanyList);
    }
}
