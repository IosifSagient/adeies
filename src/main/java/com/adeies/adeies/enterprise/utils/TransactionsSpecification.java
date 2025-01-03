package com.adeies.adeies.enterprise.utils;

import com.adeies.adeies.enterprise.dto.DateRange;
import com.adeies.adeies.enterprise.dto.Transactions.EmployeeTrxFilters;
import com.adeies.adeies.enterprise.entities.Transactions;
import com.adeies.adeies.enterprise.enums.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class TransactionsSpecification {
    public static Specification<Transactions> hasUser(List<Long> userIds) {
        return (root, query, cb) ->
                userIds == null ? null : root.get("user").get("id").in(userIds);
    }

    public static Specification<Transactions> hasStatus(List<Status> statuses) {
        return (root, query, cb) ->
                (statuses == null || statuses.isEmpty())
                        ? null
                        : root.get("status").in(statuses);
    }

    public static Specification<Transactions> hasStartDateAndEndDate(DateRange dateRange) {
        return (root, query, criteriaBuilder) -> {

            Predicate endDateCondition  = criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), dateRange.endDate());
            Predicate startDateCondition = criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), dateRange.startDate());

            // Combine the conditions to check for overlap
            return criteriaBuilder.and(startDateCondition, endDateCondition);
        };
    }

    public static Specification<Transactions> buildSpecificationDependingOnCriteria(EmployeeTrxFilters employeeTrxFilters) {
        Specification<Transactions> spec = Specification.where(null);

        if (employeeTrxFilters.status() != null) {
            spec = spec.and(hasStatus(List.of(employeeTrxFilters.status())));
        }

        if (employeeTrxFilters.employeeId() != null) {
            spec = spec.and(hasUser(List.of(employeeTrxFilters.employeeId())));
        }

        if (employeeTrxFilters.startDate() != null && employeeTrxFilters.endDate() != null) {
            spec = spec.and(hasStartDateAndEndDate(
                    new DateRange(LocalDate.parse(employeeTrxFilters.startDate()), LocalDate.parse(employeeTrxFilters.endDate()))
            ));
        }

        return spec;
    }
}
