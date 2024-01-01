package com.adeies.adeies.enterprise.utils;

import com.adeies.adeies.enterprise.entities.EmployeeCard;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecifications {
    public static Specification<EmployeeCard> hasDepartment(String department) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("department")),
                "%" + department.toLowerCase() + "%");
    }

    public static Specification<EmployeeCard> hasArea(String area) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("area")), "%" + area.toLowerCase() + "%");
    }

    public static Specification<EmployeeCard> hasPosition(String position) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("position")), "%" + position.toLowerCase() + "%");
    }

    public static Specification<EmployeeCard> hasAreaAndDepartment(String area, String department) {
        return Specification.where(hasArea(area).and(hasDepartment(department)));
    }
}
