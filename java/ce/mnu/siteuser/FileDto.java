package ce.mnu.siteuser;
import lombok.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter @Setter
public class FileDto {
    private String fileName;
    private String contentType;
}
   /* private LocalDateTime uploadTime;


    public String getFileNameWithTime() {
        String formattedTime = uploadTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            return fileName.substring(0, dotIndex) + "_" + formattedTime + fileName.substring(dotIndex);
        }
        return fileName + "_" + formattedTime;
    }*/
