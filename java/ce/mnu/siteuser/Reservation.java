package ce.mnu.siteuser;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
    @Column(length=20, nullable=true)
    private Long custid;

    @Column(nullable=true, length=50)
    private Long actid;

    @Column(nullable=true, name="writtendate")
    private LocalDate writtendate = LocalDate.now();
     
    @Column(nullable=true, name="activitydate")
    private LocalDate activitydate = LocalDate.now();
    
    @Column(nullable=true)
    private Long actperson;
    
    //id
    public Long getId() { return id; }
    public void setId(Long i) { id = i; }
    
    //custid
    public Long getCustid() { return custid; }
    public void setCustid(Long ci) { custid = ci; }

    //actid
    public Long getActid() { return actid; }
    public void setActid(Long ai) { actid = ai; }
    
    //writtendate
    public LocalDate getWrittendate() { return writtendate; }
    public void setWrittendate(LocalDate wd) { writtendate = wd; }
    
    //activitydate
    public LocalDate getActivitydate() { return activitydate; }
    public void setactivitydate(LocalDate ad) { activitydate = ad; }
   
    //actperson
    public Long getActperson() { return actperson; }
    public void setactperson(Long ap) { actperson = ap; }
    
}

