package ce.mnu.siteuser;

import jakarta.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable=false)
    private String name;

    // Getter and Setter methods
}
