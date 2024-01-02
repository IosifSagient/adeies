package com.adeies.adeies.enterprise.utils;

import com.adeies.adeies.enterprise.entities.EmployeeCard;
import org.springframework.data.jpa.domain.Specification;

import static org.springframework.data.jpa.domain.Specification.where;

public class EmployeeSpecifications {

    public static Specification<EmployeeCard> hasDepartment(Long department) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("department").get("id"), department);
    }

//    public static Specification<User> hasRole(Long role, Long dept) {
//        return where(
//                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("role"), role));
//    }

    public static Specification<EmployeeCard> hasArea(String area) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("area")), "%" + area.toLowerCase() + "%");
    }

    public static Specification<EmployeeCard> hasPosition(String position) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("position")), "%" + position.toLowerCase() + "%");
    }

    public static Specification<EmployeeCard> hasAreaAndDepartment(String area, Long department) {
        return where(hasArea(area).and(hasDepartment(department)));
    }
}
