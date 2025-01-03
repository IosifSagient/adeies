package com.adeies.adeies.enterprise.dto;

import lombok.Data;

import java.time.LocalDate;


public record DateRange(LocalDate startDate, LocalDate endDate) {}
