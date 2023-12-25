package com.adeies.adeies.enterprise.utils;

import com.adeies.adeies.enterprise.model.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecifications {
    public static Specification<Employee> hasDepartment(String department){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("department")),"%"+ department.toLowerCase()+"%");
    }
    public static Specification<Employee> hasArea(String area){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("area")),"%"+ area.toLowerCase()+"%");
    }
    public static Specification<Employee> hasPosition(String position){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("position")),"%" + position.toLowerCase() + "%");
    }

    public static Specification<Employee> hasAreaAndDepartment(String area, String department){
        return Specification.where(hasArea(area).and(hasDepartment(department)));
    }
}
