package ce.mnu.siteuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT id, custid, actid, writtendate, activitydate, actperson FROM reservation, activity, siteuser WHERE reservation.custid = siteuser.user_no and reservation.actid = activity.id ", nativeQuery=true)
    Page<ReservationHeader> findOrderHeaders(Pageable pageable);
    
    Page<Reservation> findByCustid(Long custId, Pageable pageable);
}


 