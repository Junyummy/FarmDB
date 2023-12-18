package ce.mnu.siteuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Repository
public interface LocalspecialityRepository extends JpaRepository<Localspeciality, Long> {
	@Query(value = "SELECT id, name, category, price , unit, photourl FROM localspeciality", nativeQuery=true)
	Page<LocalspecialityHeader> findLocalspecialitiesHeaders(Pageable pageable);
	Localspeciality findByid(Long id);
	//
	//
}
