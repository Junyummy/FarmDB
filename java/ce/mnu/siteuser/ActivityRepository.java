package ce.mnu.siteuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
	@Query(value = "SELECT photourl, id, name, money, person FROM activity", nativeQuery=true)
	Page<ActivityHeader> findActivityHeaders(Pageable pageable);
	Activity findByid(Long id);
}
