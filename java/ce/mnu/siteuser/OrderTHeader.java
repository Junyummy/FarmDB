package ce.mnu.siteuser;


import java.time.LocalDate;
import java.util.List;

public interface OrderTHeader {
    Long getId();
    Long getCustid();
    Long getLsid();
    LocalDate getOrderdate();
    String getPayment_method();
    Long getQuantity();
  
}


