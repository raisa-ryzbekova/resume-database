package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DateUtil.NOW;
import static ru.javawebinar.basejava.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    private Link homePage;
    private List<PositionInCompany> positionInCompanyList = new ArrayList<>();

    public Company() {
    }

    public Company(String name, String url, PositionInCompany... positionsInCompany) {
        this(new Link(name, url), Arrays.asList(positionsInCompany));
    }

    public Company(Link link, List<PositionInCompany> positionsInCompany) {
        this.homePage = link;
        this.positionInCompanyList = positionsInCompany;
    }

    public String getCompanyName() {
        return this.homePage.getName();
    }

    public String getUrl() {
        return this.homePage.getUrl();
    }

    public List<PositionInCompany> getListOfPositions() {
        return this.positionInCompanyList;
    }

    @Override
    public String toString() {
        return "Company(" + homePage + "," + positionInCompanyList + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(homePage, company.homePage) &&
                Objects.equals(positionInCompanyList, company.positionInCompanyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positionInCompanyList);
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PositionInCompany implements Serializable {
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String position;
        private String functions;

        public PositionInCompany() {
        }

        public PositionInCompany(int startYear, Month startMonth, String position, String functions) {
            this(of(startYear, startMonth), NOW, position, functions);
        }

        public PositionInCompany(int startYear, Month startMonth, int endYear, Month endMonth, String position, String functions) {
            this(of(startYear, startMonth), of(endYear, endMonth), position, functions);
        }

        public PositionInCompany(LocalDate startDate, LocalDate endDate, String position, String functions) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(position, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.position = position;
            this.functions = functions == null ? "" : functions;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getPosition() {
            return position;
        }

        public String getFunctions() {
            return functions;
        }


        @Override
        public String toString() {
            return "PositionInCompany(" + startDate + ',' + endDate + ',' + position + ',' + functions + ')';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PositionInCompany that = (PositionInCompany) o;
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
}
