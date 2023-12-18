package ce.mnu.siteuser;

import jakarta.persistence.*;
import java.util.List;


@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    public Long id;
    
    @Column(length=50, unique=true, nullable=false)
    public String name;

    @Column(length=50, unique=true, nullable=false)
    public String address;

    @Column(nullable=false, length=20)
    public Long phone;

    @Column(nullable=false, length=20)
    public Long money;

    @Column(nullable=true, length=50)
    public Long person;

    @Column(nullable=true, length=300)
    public String note;
    
    @Column(nullable=true, length=20)
    public String photourl;
    
    public Long getId() {return id;}
    public void setId(Long i) {id = i;}
    
    public String getName() {return name;}
    public void setName(String an) {name = an;}
    
    public String getAddress() {return address;}
    public void setAddress(String aa) {address = aa;}
    
    public Long getPhone() {return phone;}
    public void setPhone(Long p) {phone = p;}
    
    public Long getMoney() {return money;}
    public void setMoney(Long am) {money = am;}
    
    public Long getPerson() {return person;}
    public void setPerson(Long ap) {person = ap;}
    
    public String getNote() {return note;}
    public void setNote(String an) {note = an;}
    
    public String getPhotourl() {return photourl;}
    public void setPhotourl(String an) {photourl = an;}
}