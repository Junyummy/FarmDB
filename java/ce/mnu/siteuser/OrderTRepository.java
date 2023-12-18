package ce.mnu.siteuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


@Repository
public interface OrderTRepository extends JpaRepository<OrderT, Long> {

    @Query(value = "SELECT id, custid, lsid, orderdate, payment_method, quantity FROM localspeciality, orderT, siteuser WHERE localspeciality.custid = siteuser.user_no and localspeciality.id = orderT.lsid ", nativeQuery=true)
    Page<OrderTHeader> findOrderTHeaders(Pageable pageable);
    
    Page<OrderT> findByCustid(Long custId, Pageable pageable);
}


 