package ce.mnu.siteuser;


import java.time.LocalDate;
import java.util.List;

public interface ReservationHeader {
    Long getId();
    Long getCustid();
    Long getActid();
    LocalDate getWrittendate();
    LocalDate getActivitydate();
    Long getActperson();
  
}


