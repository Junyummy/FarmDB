package ce.mnu.siteuser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
	SiteUser findByEmail(String email);
}