package ce.mnu.siteuser;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class SiteUser {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long userNo;

    @Column(length=50, unique=true, nullable=false)
    private String email;

    @Column(nullable=false, length=20)
    private String name;

    @Column(nullable=false, length=20, name="password")
    private String passwd;

    @Column(nullable=true, length=50)
    private String address;

    @Column(nullable=true, length=20)
    private String phoneNumber;
    
    
    
    
    public Long getUserNo() {
        return userNo;
    }
    public void setUserNo(Long n) {
        userNo = n;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String e) {
        email = e;
    }
    public String getName() {
        return name;
    }
    public void setName(String n) {
        name = n;
    }
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String p) {
        passwd = p;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String a) {
        address = a;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String pn) {
        phoneNumber = pn;
    }
    
    @Column(length=13, nullable=true)
    private String dateOfBirth; // LocalDate 대신 String으로 선언
    
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public LocalDate getParsedDateOfBirth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateOfBirth, formatter);
    }

    
    
}
