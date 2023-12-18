package ce.mnu.siteuser;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class OrderT {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(nullable = true) // 숫자 타입은 length 설정이 필요 없습니다.
    private Long custid;

    @Column(nullable = true) // 숫자 타입은 length 설정이 필요 없습니다.
    private Long lsid;

    @Column(nullable = true)
    private LocalDate orderdate = LocalDate.now();
     
    @Column(nullable = true)
    private String payment_method;
    
    @Column(nullable = true) // 숫자 타입은 length 설정이 필요 없습니다.
    private Long quantity;
    
    @Column(nullable = true) // totalprice
    private Long totalprice;
   
    public Long getTotalprice() {return totalprice;}
    public void setTotalprice(Long tp) {this.totalprice = tp;}

    // id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    // custid
    public Long getCustid() {
        return custid;
    }

    public void setCustid(Long custid) {
        this.custid = custid;
    }
    
    // lsid
    public Long getLsid() {
        return lsid;
    }

    public void setLsid(Long lsid) {
        this.lsid = lsid;
    }
    
    // orderdate
    public LocalDate getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(LocalDate orderdate) {
        this.orderdate = orderdate;
    }
    
    // payment_method
    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
    
    // quantity
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}

