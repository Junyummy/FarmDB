package ce.mnu.siteuser;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import org.springframework.http.*;
import org.springframework.core.io.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.*;
import com.google.gson.*;

@Controller
@RequestMapping(path="/siteuser")
public class SiteUserController {

    @Autowired
    private SiteUserRepository userRepository;
    
    

    
    @GetMapping
    public String redirectToStart() {
        return "redirect:/siteuser/start";
    }
    
    @GetMapping(path="/start")
    public String start(Model model, HttpSession session) {
       String email = (String) session.getAttribute("email");
        SiteUser user = userRepository.findByEmail(email);

        if (user != null) {
            model.addAttribute("name", user.getName());
        } else {
            model.addAttribute("name", null);
        }
        
        return "start";
    }
    
    
    @Autowired
    private ActivityRepository activityRepository;
    @GetMapping(path="/activities")
    public String getAllactivities(@RequestParam(name="pno", defaultValue="0")
    			String pno, Model model, HttpSession session,
    			RedirectAttributes rd) {
 	   int pageNo = 1; // 페이지 번호 
 	   if(pno != null) {
 		   pageNo = Integer.valueOf(pno);
 	   }
 	   int pageSize = 5; // 페이지 크기 
 	   Pageable paging = PageRequest.of(pageNo, pageSize,
 			   Sort.Direction.DESC, "id"); // 게시물을 ID에 대해 내림차순 
 	   Page<ActivityHeader> data = activityRepository.findActivityHeaders(paging);
 	   model.addAttribute("activities", data);
 	   session.setAttribute("pageNum", pageNo);
 	   return "activities";
    }
    
    
    @GetMapping("/actread")
    public String readActivity(@RequestParam(name = "num") Long num, Model model,
                              @RequestParam(name = "pno", defaultValue = "1") int pno,
                              HttpSession session) {
    	
        /*String email = (String) session.getAttribute("email");
        SiteUser user = userRepository.findByEmail(email);
        model.addAttribute("user", user);*/
    	int pageNo = 1;
    	session.setAttribute("pageNum", pageNo);
        
        Long no = Long.valueOf(num);
        session.setAttribute("no", no);
        Activity activity = activityRepository.findById(no).orElse(null);
        if (activity == null) {
            return "redirect:/siteuser/activities";
        }

        int pageNum = (Integer) session.getAttribute("pageNum");
        model.addAttribute("activity", activity);
        
        //boolean articleUpdateToken = (user != null && user.getName().equals(article.getAuthor()));
        //model.addAttribute("articleUpdateToken", articleUpdateToken);
        
        model.addAttribute("pageNum", pageNum);
        return "activity";
    }
    
    @Autowired
    private LocalspecialityRepository localspecialityRepository;
    @GetMapping(path="/localspecialities")
    public String getAlllocalspecialities(@RequestParam(name="pno", defaultValue="0")
    			String pno, Model model, HttpSession session,
    			RedirectAttributes rd) {
 	   int pageNo = 1; // 페이지 번호 
 	   if(pno != null) {
 		   pageNo = Integer.valueOf(pno);
 	   }
 	   int pageSize = 5; // 페이지 크기 
 	   Pageable paging = PageRequest.of(pageNo, pageSize,
			   Sort.Direction.DESC, "id"); // 게시물을 ID에 대해 내림차순 
	   Page<LocalspecialityHeader> data =
			   localspecialityRepository.findLocalspecialitiesHeaders(paging);
 	   model.addAttribute("localspecialities", data);
 	   session.setAttribute("pageNum", pageNo);
 	   return "localspecialities";
    }
    
    @GetMapping("/lsread")
    public String readlocalspeciality(@RequestParam(name = "num") Long num, Model model,
                              @RequestParam(name = "pno", defaultValue = "1") int pno,
                              HttpSession session) {
    	
        /*String email = (String) session.getAttribute("email");
        SiteUser user = userRepository.findByEmail(email);
        model.addAttribute("user", user);*/
    	
    	int pageNo = 1;
    	session.setAttribute("pageNum", pageNo);
        Long no = Long.valueOf(num);
        session.setAttribute("no", no);
        Localspeciality localspeciality = localspecialityRepository.findById(no).orElse(null);
        if (localspeciality == null) {
            return "redirect:/siteuser/localspecialities";
        }

        int pageNum = (Integer) session.getAttribute("pageNum");
        model.addAttribute("localspeciality", localspeciality);
        
        //boolean articleUpdateToken = (user != null && user.getName().equals(article.getAuthor()));
        //model.addAttribute("articleUpdateToken", articleUpdateToken);
        
        model.addAttribute("pageNum", pageNum);
        return "localspeciality";
    }
    
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    @Autowired
    private ReservationRepository reservationRepository;
    @GetMapping(path = "/reservation/{id}")
    public String reservationForm(@PathVariable("id") Long activityId, Model model, HttpSession session, RedirectAttributes ra) {
        session.setAttribute("id", activityId);

        String email = (String) session.getAttribute("email"); // Verify login
        if (email == null) {
            ra.addFlashAttribute("reason", "login required");
            return "redirect:/error"; // If not logged in, go to the error page
        }

        SiteUser user = userRepository.findByEmail(email);
        if (user == null) {
            // Handle case where user is not found
            return "redirect:/error"; // Redirect to error page or handle appropriately
        }

        model.addAttribute("user", user);
        session.setAttribute("name", user.getName());

        // Fetch activity details based on activityId
        Activity activity = activityRepository.findByid(activityId);
        if (activity == null) {
            // Handle case where activity is not found
            return "redirect:/error"; // Redirect to error page or handle appropriately
        }

        model.addAttribute("activity", activity);

        // Prepopulate the reservation object with necessary data
        Reservation reservation = new Reservation();
        reservation.setCustid(user.getUserNo());
        reservation.setActid(activity.getId());
        reservation.setWrittendate(LocalDate.now());
        reservation.setactivitydate(LocalDate.now());

        model.addAttribute("reservation", reservation);

        return "reservation"; // Render the reservation form with prepopulated data
    }
   
    @PostMapping(path="/reservation/save") // 글을 DB에 저장
    public String savereservation(@ModelAttribute Reservation reservation, Model model, HttpSession session, RedirectAttributes ra) throws IOException{
        // ...
    	if (reservation.getCustid() == null) {
    		Long id = (Long) session.getAttribute("id");
    		reservation.setCustid(id);
    	}
    	reservationRepository.save(reservation);    	
        return "reservationsaved";
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Autowired
    private OrderTRepository orderRepository;
    
    @GetMapping(path = "/order/{id}")
    public String orderForm(@PathVariable("id") Long localspecialityId, Model model, HttpSession session, RedirectAttributes ra) {
        session.setAttribute("id", localspecialityId);
        

        String email = (String) session.getAttribute("email"); // Verify login
        if (email == null) {
            ra.addFlashAttribute("reason", "login required");
            System.out.println("User is not logged in");
            return "redirect:/error"; // If not logged in, go to the error page
        }

        SiteUser user = userRepository.findByEmail(email);
        if (user == null) {
            // Handle case where user is not found
        	System.out.println("User is null");
            return "redirect:/error"; // Redirect to error page or handle appropriately
        }

        model.addAttribute("user", user);
        session.setAttribute("name", user.getName());

 
        Localspeciality localspeciality = localspecialityRepository.findByid(localspecialityId);
        if (localspeciality == null) {
        	System.out.println("Localspeciality is null");
            // Handle case where activity is not found
            return "redirect:/error"; // Redirect to error page or handle appropriately
        }
        

        model.addAttribute("localspeciality", localspeciality);
        model.addAttribute("price",localspeciality.getPrice());

        // Prepopulate the reservation object with necessary data
        OrderT orderT = new OrderT();
        orderT.setCustid(user.getUserNo());
        orderT.setLsid(localspeciality.getId());
        orderT.setOrderdate(LocalDate.now());

        model.addAttribute("order", orderT);

        return "order"; // Render the reservation form with prepopulated data
    }
   
    
    @PostMapping(path="/order/save") // 글을 DB에 저장
    public String saveorder(@ModelAttribute OrderT order, Model model, HttpSession session, RedirectAttributes ra) throws IOException{
        // ...
    	if (order.getCustid() == null) {
    		Long id = (Long) session.getAttribute("id");
    		order.setCustid(id);
    	}
    	orderRepository.save(order);    	
        return "ordersaved";
    }
    
    
    
    
    @GetMapping(path="/myorders")
    public String getmyorder(@RequestParam(name="pno", defaultValue="0")
    			String pno, Model model, HttpSession session, 
    			RedirectAttributes rd) {
    	String email = (String) session.getAttribute("email"); // Verify login
        if (email == null) {
            rd.addFlashAttribute("reason", "login required");
            System.out.println("User is not logged in");
            return "redirect:/error"; // If not logged in, go to the error page
        }
        
        SiteUser user = userRepository.findByEmail(email);
        if (user == null) {
            // Handle case where user is not found
        	System.out.println("User is null");
            return "redirect:/error"; // Redirect to error page or handle appropriately
        }
        
        
        
        int pageNo = 0; // 페이지 번호
        if(pno != null) {
  		   pageNo = Integer.valueOf(pno);
  	   }
        int pageSize = 5; // 페이지 크기 
  	   
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, "id"); // 페이지네이션 설정
        
        
        Long custId = user.getUserNo();
        Page<OrderT> userOrders = orderRepository.findByCustid(custId, paging); // 사용자의 주문 목록 가져오기
        
        ////
        List<String> localspecialityNames = new ArrayList<>();

        for (OrderT order : userOrders.getContent()) {
            Localspeciality localspeciality = localspecialityRepository.findByid(order.getLsid());
            if (localspeciality != null) {
                localspecialityNames.add(localspeciality.getName());
            } else {
                localspecialityNames.add("N/A"); // 만약에 찾을 수 없을 경우 기본값으로 설정
            }
        model.addAttribute("localspecialityNames", localspecialityNames);
        }
        
        ////
        
        
        
        
        
        
        model.addAttribute("myorders", userOrders); 

        session.setAttribute("name", user.getName());
 	   return "myorders";
    }
    
    
    @GetMapping(path="/myreservations")
    public String getmyreservation(@RequestParam(name="pno", defaultValue="0")
    			String pno, Model model, HttpSession session, 
    			RedirectAttributes rd) {
    	String email = (String) session.getAttribute("email"); // Verify login
        if (email == null) {
            rd.addFlashAttribute("reason", "login required");
            System.out.println("User is not logged in");
            return "redirect:/error"; // If not logged in, go to the error page
        }
        
        SiteUser user = userRepository.findByEmail(email);
        if (user == null) {
            // Handle case where user is not found
        	System.out.println("User is null");
            return "redirect:/error"; // Redirect to error page or handle appropriately
        }
        
        
        
        int pageNo = 0; // 페이지 번호
        if(pno != null) {
  		   pageNo = Integer.valueOf(pno);
  	   }
        int pageSize = 5; // 페이지 크기 
  	   
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, "id"); // 페이지네이션 설정
        
        
        Long custId = user.getUserNo();
        Page<Reservation> userReservations = reservationRepository.findByCustid(custId, paging); // 사용자의 주문 목록 가져오기
        
        ////
        List<String> activityNames = new ArrayList<>();

        for (Reservation reservation : userReservations.getContent()) {
            Activity activity = activityRepository.findByid(reservation.getActid());
            if (activity != null) {
            	activityNames.add(activity.getName());
            } else {
                activityNames.add("N/A"); // 만약에 찾을 수 없을 경우 기본값으로 설정
            }
        model.addAttribute("activityNames", activityNames);
        }
        
        ////
        
        
        
        
        
        
        model.addAttribute("myreservations", userReservations); 

        session.setAttribute("name", user.getName());
 	   return "myreservations";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @GetMapping(path="/signup")
    public String signupForm(Model model) {
        model.addAttribute("siteuser", new SiteUser());
        return "signup_input";
    }

    @PostMapping(path="/signup")
    public String signup(@ModelAttribute SiteUser user, Model model, RedirectAttributes ra) {
        SiteUser existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            ra.addFlashAttribute("reason", "email already exists");
            return "redirect:/siteuser/signup_failed";
        }
        
        

        LocalDate dob = user.getParsedDateOfBirth();
        String dobAsString = dob.format(DateTimeFormatter.ofPattern("yyMMdd"));
        user.setDateOfBirth(dobAsString);
        
        userRepository.save(user);
        model.addAttribute("name", user.getName());
        return "signup_done";
    }

    @GetMapping(path="/signup_failed")
    public String signupFailed(Model model, @ModelAttribute("reason") String reason) {
        model.addAttribute("reason", reason);
        return "signup_error";
    }
    @GetMapping(path="/find")
    public String findUserForm(Model model) {
        return "find";
    }

    @PostMapping(path="/find")
    public String findUser(@RequestParam(name="email") String email,
                           HttpSession session,
                           RedirectAttributes ra) {
        SiteUser user = userRepository.findByEmail(email);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/siteuser/find_done";
        } else {
            ra.addFlashAttribute("reason", "wrong email");
            return "redirect:/siteuser/error";
        }
    }

    @GetMapping(path="/find_done")
    public String findDone(Model model, HttpSession session) {
        SiteUser user = (SiteUser) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            session.removeAttribute("user");
            return "find_done";
        } else {
            return "redirect:/siteuser/error";
        }
    }



    @PostMapping(path="/login")
    public String loginUser(@RequestParam(name="email") String email,
                            @RequestParam(name="passwd") String passwd,
                            HttpSession session,
                            RedirectAttributes rd) {
        SiteUser user = userRepository.findByEmail(email);
        if(user != null) {
            if(passwd.equals(user.getPasswd())) {
                session.setAttribute("email", email);
                return "login_done";
            }
        }
        rd.addFlashAttribute("reason", "wrong password");
        return "redirect:/login_failed";
    }

    @GetMapping(path="/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping(path="/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "start";
    }

    
    @GetMapping(path="/update")
    public String updateForm(HttpSession session, Model model) {
    	 String email = (String) session.getAttribute("email");
    	    SiteUser user = userRepository.findByEmail(email);

    	    // Assuming user.getDateOfBirth() returns a String representing the date in 'yyyy-MM-dd HH:mm:ss' format
    	    String dateOfBirthString = user.getDateOfBirth();
    	    
    	    // Parse the string into LocalDateTime using the provided format
    	    LocalDateTime dateTime = LocalDateTime.parse(dateOfBirthString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    	    // Extract date part
    	    LocalDate dateOfBirth = dateTime.toLocalDate();

    	    // Format the LocalDate to YYMMDD format
    	    String formattedDate = dateOfBirth.format(DateTimeFormatter.ofPattern("yyMMdd"));

        
        
        model.addAttribute("formattedDate", formattedDate);
        model.addAttribute("user", user);
        return "update_user";
    }

    @PostMapping(path="/update")
    public String update(@ModelAttribute SiteUser user, HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");
        SiteUser currentUser = userRepository.findByEmail(email);
        
        LocalDate dob = user.getParsedDateOfBirth();
        if (dob != null) {
            String dobAsString = dob.format(DateTimeFormatter.ofPattern("yyMMdd"));
            currentUser.setDateOfBirth(dobAsString);
        }
        
        currentUser.setPasswd(user.getPasswd());
        currentUser.setAddress(user.getAddress());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(currentUser);
        model.addAttribute("name", currentUser.getName());
        return "update_done";
    }
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @GetMapping(path="/bbs/write") // 글 쓰기 페이지로 이동
    public String bbsForm(Model model, HttpSession session, RedirectAttributes ra) {
    	String email = (String) session.getAttribute("email"); // 로그인 확인
        if(email == null) {
            ra.addFlashAttribute("reason", "login required");
            return "redirect:/error"; // 로그인 안 했으면, 에러 페이지로 이동
        }
        SiteUser user = userRepository.findByEmail(email);
        model.addAttribute("user", user);

        // 세션에 "name" 속성을 설정합니다.
        session.setAttribute("name", user.getName());

        
        //model.addAttribute("article", new Article());
        Article article = new Article();
        String name = (String) session.getAttribute("name");
        model.addAttribute("article", new Article());
        article.setAuthor(name);
        model.addAttribute("article", article);
        return "new_article";
    }
    
    String resourcesPath = getClass().getResource("/").getPath();
    public String getCurrentTimeFormatted() {
	    LocalDateTime currentTime = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
	    return currentTime.format(formatter);
	}
    
    
    @PostMapping(path="/bbs/add") // 글을 DB에 저장
    public String addArticle(@ModelAttribute Article article, Model model, HttpSession session, RedirectAttributes ra
            , @RequestParam(value = "photoFile", required = false) MultipartFile file) throws IOException {
        // ...
    	if (article.getAuthor() == null) {
    		String name = (String) session.getAttribute("name");
    		article.setAuthor(name);
    	}
    	
        if (!file.isEmpty()) {
            try {
                // 파일 경로 계산을 위해 작업 디렉토리 기준으로 상대 경로 설정
                String relativePath = "/src/main/resources/static/img/";
                String absolutePath = System.getProperty("user.dir") + relativePath;

                String fullName = file.getOriginalFilename();
                String extension = fullName.substring(fullName.lastIndexOf("."));
                String newName = fullName.replace(extension, "");
                newName = newName.replace(' ', '_');

                String currentTime = getCurrentTimeFormatted();
                String finalName = newName + "_" + currentTime + extension;

                File upfile = new File(absolutePath + finalName);
                file.transferTo(upfile);

                String newFilePath = relativePath + finalName;
                newFilePath = "C:/Users/chhoo/eclipse-workspace-web/siteuser_wp/src/main/resources/static/img/" + finalName;
                File newFile = new File(newFilePath);
                upfile.renameTo(newFile);

                
                FileDto dto = new FileDto(finalName, file.getContentType());
                // 파일 정보를 게시글에 저장
                article.setPhoto(finalName);
                article.setPhotoUrl(newFilePath);
                article.setViews((long) 0);
                model.addAttribute("file", dto);
                articleRepository.save(article);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        	article.setViews((long) 0);
        	articleRepository.save(article);
            model.addAttribute("file", null);
        }
        return "saved";
    }


    
    @GetMapping(path="/bbs")
    public String getAllArticles(@RequestParam(name="pno", defaultValue="0")
    			String pno, Model model, HttpSession session,
    			RedirectAttributes rd) {
 	   String email = (String) session.getAttribute("email");
 	   if(email == null) {
 		   rd.addFlashAttribute("reason", "login required");
 		   return "redirect:/error";
 	   }
 	   Integer pageNo = 0; // 페이지 번호 
 	   if(pno != null) {
 		   pageNo = Integer.valueOf(pno);
 	   }
 	   Integer pageSize = 2; // 페이지 크기 
 	   Pageable paging = PageRequest.of(pageNo, pageSize,
 			   Sort.Direction.DESC, "num"); // 게시물을 num에 대해 내림차순 
 	   Page<ArticleHeader> data =
 			   articleRepository.findArticleHeaders(paging);
 	   model.addAttribute("articles", data);
 	   session.setAttribute("pageNum", pageNo);
 	   return "articles";
    }

    
    @GetMapping("/read")
    public String readArticle(@RequestParam(name = "num") Long num, Model model,
                              @RequestParam(name = "pno", defaultValue = "0") int pno,
                              HttpSession session) {
    	
        String email = (String) session.getAttribute("email");
        SiteUser user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        
        
        Long no = Long.valueOf(num);
        session.setAttribute("no", no);
        Article article = articleRepository.findById(no).orElse(null);
        if (article == null) {
            return "redirect:/siteuser/bbs";
        }

        int pageNum = (Integer) session.getAttribute("pageNum");
        model.addAttribute("article", article);
        article.setViews(article.getViews() + 1);  // 조회수 증가
        articleRepository.save(article); // 변경사항 저장
        
        boolean articleUpdateToken = (user != null && user.getName().equals(article.getAuthor()));
        model.addAttribute("articleUpdateToken", articleUpdateToken);
        
        model.addAttribute("pageNum", pageNum);
        return "article";
    }


    @GetMapping(path="/bbs/update") // 글 수정 페이지로 이동
    public String updateArticle(Model model, HttpSession session) {
    	
    	String email = (String) session.getAttribute("email");
        
        
    	long no = (long)session.getAttribute("no");
        Article article = articleRepository.findById(no).orElse(null);
        if (article == null) {
            return "redirect:/siteuser/bbs";
        }

        int pageNum = (Integer) session.getAttribute("pageNum");
        model.addAttribute("article", article);
        model.addAttribute("pageNum", pageNum);
        
        return "article_update";
    }

    
    @PostMapping(path="/siteuser/update")
    public String updateArticle(@ModelAttribute Article article, Model model, HttpSession session, RedirectAttributes ra,
            @RequestParam(value = "photoFile", required = false) MultipartFile file) throws IOException {

        Long no = (Long) session.getAttribute("no");
        Article updateArticle = articleRepository.findById(no).orElse(null);
        model.addAttribute("article", updateArticle);

        if (updateArticle != null) {
            if (article.getNum() != null) {
                updateArticle.setNum(article.getNum());
            }

            if (article.getAuthor() != null) {
                updateArticle.setAuthor(article.getAuthor());
            } else {
                // 중간 체크 코드 추가
                System.out.println("article.getAuthor() is null");
                System.out.println("article.getAuthor() is null");
                System.out.println("article.getAuthor() is null");
                System.out.println("article.getAuthor() is null");
                System.out.println("article.getAuthor() is null");
                System.out.println("article.getAuthor() is null");
            }

            if (article.getTitle() != null) {
                updateArticle.setTitle(article.getTitle());
            }

            if (article.getBody() != null) {
                updateArticle.setBody(article.getBody());
            }

            if (article.getWrittenDate() != null) {
                updateArticle.setWrittenDate(article.getWrittenDate());
            }

            if (article.getViews() != null) {
                updateArticle.setViews(article.getViews());
            }

            if (article.getPrice() != null) {
                updateArticle.setPrice(article.getPrice());
            }

            if (article.getCategories() != null) {
                updateArticle.setCategories(article.getCategories());
            }

            if (article.getProcesses() != null) {
                updateArticle.setProcesses(article.getProcesses());
            }

            if (article.getLocations() != null) {
                updateArticle.setLocations(article.getLocations());
            }

            if (!file.isEmpty()) {
                try {
                    // 파일 경로 계산을 위해 작업 디렉토리 기준으로 상대 경로 설정
                    String relativePath = "/src/main/resources/static/img/";
                    String absolutePath = System.getProperty("user.dir") + relativePath;

                    String fullName = file.getOriginalFilename();
                    String extension = fullName.substring(fullName.lastIndexOf("."));
                    String newName = fullName.replace(extension, "");
                    newName = newName.replace(' ', '_');

                    String currentTime = getCurrentTimeFormatted();
                    String finalName = newName + "_" + currentTime + extension;

                    File upfile = new File(absolutePath + finalName);
                    file.transferTo(upfile);

                    String newFilePath = relativePath + finalName;
                    newFilePath = "C:/Users/chhoo/eclipse-workspace-web/siteuser_wp/src/main/resources/static/img/" + finalName;
                    File newFile = new File(newFilePath);
                    upfile.renameTo(newFile);

                    FileDto dto = new FileDto(finalName, file.getContentType());
                    // 파일 정보를 게시글에 저장
                    updateArticle.setPhoto(finalName);
                    updateArticle.setPhotoUrl(newFilePath);
                    model.addAttribute("file", dto);

                    articleRepository.save(updateArticle);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // 파일이 업로드되지 않은 경우 처리
                model.addAttribute("file", null);
            }

            // 저장된 게시글 페이지로 리다이렉트
            return "redirect:/siteuser/bbs";
        }

        // 게시글이 없을 경우 처리
        model.addAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
        return "error-page"; // 에러 페이지로 이동
    }
    
    @GetMapping(path="/village_intro")
    public String village(Model model) {
        return "village_intro"; // 마을 소개 페이지
    }
    
    @GetMapping(path="/tourist")
    public String getAlltourist(@RequestParam(name="pno", defaultValue="0")
             String pno, Model model, HttpSession session,
             RedirectAttributes rd) {
      
       return "tourist";
    }


/*    
 // 파일 업로드
    @PostMapping(path = "/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            String fileName = originalFilename.replace(' ', '_');

            // 파일 이름에 업로드 시간 추가
            String formattedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex != -1) {
                fileName = fileName.substring(0, dotIndex) + "_" + formattedTime + fileName.substring(dotIndex);
            } else {
                fileName = fileName + "_" + formattedTime;
            }

           FileDto dto = new FileDto(fileName, file.getContentType(), LocalDateTime.now());
            String filePath = base + "/" + fileName;
            File upFile = new File(filePath);
            file.transferTo(upFile);
            model.addAttribute("file", dto);
        }
        return "result";
    }
    
    @GetMapping(path="/upload")
    public String visitUpload( ) {
    	return "uploadForm";
    }
*/
    @Value("${spring.servlet.multipart.location}")
    String base; // 파일 저장 폴더 

    // 파일 다운로드
    @GetMapping(path="/download")
    public ResponseEntity<Resource> download(@ModelAttribute FileDto dto)
    		throws IOException {
    	Path path = Paths.get(base + "/" + dto.getFileName( ));
    	String contentType = Files.probeContentType(path);
    	HttpHeaders headers = new HttpHeaders( );
    	headers.setContentDisposition(ContentDisposition.builder("attachment")
    			.filename(dto.getFileName(), StandardCharsets.UTF_8).build( ));
    	headers.add(HttpHeaders.CONTENT_TYPE, contentType);
    	Resource rsc = new InputStreamResource(Files.newInputStream(path));
    	return new ResponseEntity<>(rsc, headers, HttpStatus.OK);
    }

    @GetMapping(path = "/sample")
    public String sample(@RequestParam(defaultValue = "0") String pid, Model model) {
        model.addAttribute("pid", pid);
        return "sample";
    }

    @ResponseBody
    @GetMapping(path = "/json-data")
    public String jsonData(@RequestParam(defaultValue = "0") String pid) {
        JsonObject jo = new JsonObject();

        if (pid.equals("0")) {
            String[] data = {
                "사용자 등록", "/siteuser/signup",
                "사용자 로그인", "/siteuser/login",
                "게시글", "/siteuser/bbs",
                "파일 업로드", "/siteuser/upload",
                "사용자 로그아웃", "/siteuser/logout"
            };

            jo.addProperty("header", "SiteUser 첫 페이지");

            JsonArray ja = new JsonArray();
            for (int k = 0; k < 5; k++) {
                JsonObject jObj = new JsonObject();
                jObj.addProperty(data[2 * k], data[2 * k + 1]);
                ja.add(jObj);
            }

            jo.add("menus", ja);
        } else if (pid.equals("1")) {
            jo.addProperty("header", "사용자 등록");
        } else if (pid.equals("2")) {
            jo.addProperty("header", "사용자 로그인");
        }

        System.out.println(jo.toString());
        return jo.toString();
    }


 }