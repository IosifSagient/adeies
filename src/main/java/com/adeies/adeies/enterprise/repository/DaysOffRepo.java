package com.adeies.adeies.enterprise.repository;

import com.adeies.adeies.enterprise.entities.DaysOff;
import com.adeies.adeies.enterprise.pojos.DaysOffAvailablePK;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaysOffRepo extends JpaRepository<DaysOff, DaysOffAvailablePK> {
}
