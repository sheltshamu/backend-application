package zw.co.afrosoft.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.domain.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
