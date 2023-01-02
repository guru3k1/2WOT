package com.cgasystems.BCP.model;

import java.util.Set;

public class Employee extends User{

    private Double salary;

    private Set<Tax> taxes;

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Set<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(Set<Tax> taxes) {
        this.taxes = taxes;
    }
}
