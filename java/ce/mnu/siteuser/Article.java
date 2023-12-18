package ce.mnu.siteuser;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long num;
    
    @Column(length=20, nullable=true)
    private String author;

    @Column(nullable=true, length=50)
    private String title;

    @Column(nullable=true)
    private String body;

    @Column(nullable=true, name="written_date")
    private LocalDate writtenDate = LocalDate.now();
        
    @Transient
    private MultipartFile photoFile;
    
    @Column(nullable=true)
    private String photoUrl;
    
    @Column(nullable=true)
    private String photo;
    
    
    @Column(nullable=true)
    private Long price;
    
    @Column(nullable=true)
    private Long views;
    
    @ElementCollection
    private List<String> categories; // 다중 카테고리 속성 추가
    
    @ElementCollection
    private List<String> processes; // 다중 카테고리 속성 추가
    
    @ElementCollection
    private List<String> locations; // 다중 카테고리 속성 추가
    
    //Num
    public Long getNum() { return num; }
    public void setNum(Long n) { num = n; }
    
    //Author
    public String getAuthor() { return author; }
    public void setAuthor(String e) { author = e; }
    
    //Title
    public String getTitle() { return title; }
    public void setTitle(String e) { title = e; }

    //Body
    public String getBody() {return body;}
    public void setBody(String body) {this.body = body;}
    
    //Date
    public LocalDate getWrittenDate() { return writtenDate; }
    public void setWrittenDate(LocalDate writtendate) { writtenDate = writtendate; }
    
    //photo
    public MultipartFile getPhotoFile() { return photoFile;}
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
    
    //photoUrl
    public String getPhotoUrl() {return photoUrl;}
    public void setPhotoUrl(String p) { photoUrl = p; }
    
    //price
    public Long getPrice() { return price; }
    public void setPrice(Long pr) { price = pr; }
    
    //views
    public Long getViews() { return views; }
    public void setViews(Long views) { this.views = views; }
    
    //categories
    public List<String> getCategories() {return categories;}
    public void setCategories(List<String> categories) {this.categories = categories;}
 
    //process
    public List<String> getProcesses() {return processes;}
    public void setProcesses(List<String> processes) {this.processes = processes;}
    
    //locations
    public List<String> getLocations() {return locations;}
    public void setLocations(List<String> locations) {this.locations = locations;}

}

