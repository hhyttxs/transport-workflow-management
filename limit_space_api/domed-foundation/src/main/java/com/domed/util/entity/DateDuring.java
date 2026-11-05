package com.domed.util.entity;

import java.util.Date;

public class DateDuring {
    private Date begindDate;
    private Date endDate;
    private Date actulEndDate;

    public DateDuring(Date begindDate, Date endDate,Date actulEndDate) {
        this.begindDate = begindDate;
        this.endDate = endDate;
        this.actulEndDate = actulEndDate;
    }

    public Date getBegindDate() {
        return begindDate;
    }

    public void setBegindDate(Date begindDate) {
        this.begindDate = begindDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getActulEndDate() {
        return actulEndDate;
    }

    public void setActulEndDate(Date actulEndDate) {
        this.actulEndDate = actulEndDate;
    }

    @Override
    public String toString() {
        return "DateDuring{" +
                "begindDate=" + begindDate +
                ", endDate=" + endDate +
                ", actulEndDate=" + actulEndDate +
                '}';
    }
}
