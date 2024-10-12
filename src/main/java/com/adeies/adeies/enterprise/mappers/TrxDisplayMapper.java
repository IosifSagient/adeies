package com.adeies.adeies.enterprise.mappers;

import com.adeies.adeies.enterprise.dto.Transactions.TransactionsDto;
import com.adeies.adeies.enterprise.entities.Transactions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,componentModel = "spring")
public interface TrxDisplayMapper {
    TrxDisplayMapper INSTANCE = Mappers.getMapper(TrxDisplayMapper.class);

    @Mapping(target ="userId", source = "user.id")
    @Mapping(target ="approvedBy", source = "approvedBy.employeeCard.lastName")
    @Mapping(target = "definition" , source = "definition.description")
    TransactionsDto toDto (Transactions transactions);
}
