package ce.mnu.siteuser;

import jakarta.persistence.*;
import java.time.LocalDate;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Localspeciality {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @Column(length=50, unique=true, nullable=false)
    private String name;

    @Column(nullable=false, length=20)
    private String category;

    @Column(nullable=false, length=20)
    private Long price;

    @Column(nullable=true, length=50)
    private Long inventory;

    @Column(nullable=true, length=20)
    private Long weight;

    @Column(nullable=false, length=20)
    private String unit;

    @Column(nullable=true, name="DoM")
    private LocalDate DoM = LocalDate.now();
    
    @Column(nullable=true, name="ED")
    private LocalDate ED = LocalDate.now();
    
   // @Transient
    //private MultipartFile photoFile;
    
    @Column(nullable=true, length=100, name="photourl")
    private String photourl;
    
    //@Column(nullable=true, length=100, name="ls_photo")
    //private String photo;
    
    
    
    
    
    
    //특산물 ID
    public Long getId() {return id;}
    public void setId(Long lid) {id = lid;}
    
   //특산물명
    public String getName() {return name;}
    public void setName(String ln) {name = ln;}

    //유형
    public String getCategory() {return category;}
    public void setCategory(String lc) {category = lc;}
    //가격
    public Long getPrice() {return price;}
    public void setPrice(Long lp) {price = lp;}
    //재고
    public Long getInventory() {return inventory;}
    public void setInventory(Long li) {inventory = li;}
    //중량
    public Long getWeight() {return weight;}
    public void setWeight(Long lw) {weight = lw;}
    
    //단위  
    public String getUnit() {return unit;}
    public void setUnit(String lu) {unit = lu;}
    
    //제조일자
    public LocalDate getDoM() { return DoM;}
    public void setDoM(LocalDate doM) { DoM = doM; }
    
    //유통기한
    public LocalDate getED() { return ED; }
    public void setED(LocalDate eD) { ED = eD; }
    
    //photo
    //public MultipartFile getPhotoFile() { return photoFile;}
    
    //public String getPhoto() { return ls_photo; }
    //public void setPhoto(String photo) { this.ls_photo = photo; }
    
    //photoUrl
    public String getPhotourl() {return photourl;}
    public void setPhotourl(String p) { photourl = p; }
    
}
