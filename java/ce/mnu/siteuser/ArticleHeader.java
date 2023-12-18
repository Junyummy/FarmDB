package ce.mnu.siteuser;


import java.time.LocalDate;
import java.util.List;

public interface ArticleHeader {
    Long getNum();
    String getTitle();
    String getAuthor();
    LocalDate getWrittenDate();
    String getPhoto();
    Long getViews();
    List<String> getCategories();
    List<String> getProcesses();
    List<String> getLocations();
}


