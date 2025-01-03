package com.adeies.adeies.enterprise.dto;

import com.adeies.adeies.enterprise.enums.Status;
import lombok.Data;
import org.hibernate.type.ListType;

import java.util.List;

@Data
public class SearchFilters {
    private List<Long> employeeIdFilter;
    private List<Status> statusFilter;
    private DateRange dateRangeFilter;
}
