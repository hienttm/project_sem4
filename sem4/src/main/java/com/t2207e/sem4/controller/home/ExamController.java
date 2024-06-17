package com.t2207e.sem4.controller.home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2207e.sem4.dto.OrderDetailByUserDTO;
import com.t2207e.sem4.dto.TestSubmissionDTO;
import com.t2207e.sem4.dto.UserAnswerDTO;
import com.t2207e.sem4.entity.Exam;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.entity.UserAnswer;
import com.t2207e.sem4.service.*;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.FileEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping("course")
public class ExamController {
    private final ExamService examService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserAnswerService userAnswerService;
    private final VimeoApiService vimeoApiService;

    private final UserService userService;

    private final RestTemplate restTemplate;

//    @Value("${vimeo.access.token}")
//    private String vimeoAccessToken;

    private final String uploadUrl = "https://api.vimeo.com/me/videos";
    private String uploadLink; // URL được trả về từ phản hồi POST

    public ExamController(ExamService examService, QuestionService questionService, AnswerService answerService, UserAnswerService userAnswerService, VimeoApiService vimeoApiService, UserService userService, RestTemplate restTemplate) {
        this.examService = examService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.userAnswerService = userAnswerService;
        this.vimeoApiService = vimeoApiService;
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("exam/{id}")
    public String exam(@PathVariable Integer id, Model model){
//        Kiểm tra người dùng đã từng làm bài kiểm tra hay chưa
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<User> userOptional = userService.getUserByUsername(username);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                Integer countUserAnswerByUserAndExamId = userAnswerService.countUserAnswerByUserAndExam_ExamId(user, id);
                if(countUserAnswerByUserAndExamId > 0){
                    return "redirect:/";
                }
            }

            Optional<Exam> examOptional = examService.getExamById(id);
            if(examOptional.isPresent()){
                Exam exam = examOptional.get();
                model.addAttribute("exam", exam);
            }
            return "home/exams/exam";
        }
        else {
            return "redirect:/login";
        }
    }

    @PostMapping("exam/submit")
    public String submitTest(@ModelAttribute TestSubmissionDTO submission) {

        for (UserAnswerDTO userAnswerDTO : submission.getUserAnswerDTOs()) {
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setExam(examService.getExamById(submission.getExamId()).get());
            if(userAnswerDTO.getAnswerId()!=null){
                userAnswer.setAnswer(answerService.getAnswerById(userAnswerDTO.getAnswerId()).get());
            }
            else {
                userAnswer.setAnswer(null);
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                Optional<User> userOptional = userService.getUserByUsername(username);
                if(userOptional.isPresent()){
                    User user = userOptional.get();
                    userAnswer.setUser(user);
                }
            }

            userAnswerService.add(userAnswer);
        }
        return "redirect:/course/detail/" + submission.getCourseId();
    }

//    @GetMapping("exam")
//    public String exam(Model model) throws JsonProcessingException {
//        String urlVideo = vimeoApiService.getVideo("951847436", vimeoAccessToken);
//        System.out.println(urlVideo);
//        model.addAttribute("uriVideo", urlVideo);
//        return "home/exams/index";
//    }
//
//    @PostMapping("test")
//    public String testFile(@RequestParam MultipartFile file) throws IOException {
//
//        // Read the content of the file
//        byte[] fileBytes = file.getBytes();
//
//        // Encode the file content to Base64
//        String encodedBase64 = Base64.getEncoder().encodeToString(fileBytes);
//
//        // Print the Base64 encoded string
//        System.out.println(encodedBase64);
//
//        return "redirect:/course/exam";
//    }
//
//
//
//    @PostMapping("test2")
//    public String uploadVideo(@RequestParam("file") MultipartFile file, Model model) {
//        try {
//            // 1. Gửi yêu cầu POST để tạo video mới
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", "bearer " + vimeoAccessToken);
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//            String uploadUrl = "https://api.vimeo.com/me/videos";
//            String requestBody = "{\"upload\": {\"approach\": \"tus\", \"size\": \"" + file.getSize() + "\"}, \"name\": \"Your Video Title\", \"description\": \"Your Video Description\"}";
////            , "domain": "Your Domain"
//
//            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//
//            ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, String.class);
//
//            System.out.println(response);
//
//            // 2. Lấy upload_link từ phản hồi POST
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode root = mapper.readTree(response.getBody());
//            // Upload the video file
//            String uploadLink = root.path("upload").path("upload_link").asText();
//
//            System.out.println(uploadLink);
//
//            // 3. Gửi yêu cầu PATCH để tải lên file video
//            headers.clear();
//            headers.set("Tus-Resumable", "1.0.0");
//            headers.set("Upload-Offset", "0");
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//
////            HttpEntity<byte[]> uploadEntity = new HttpEntity<>(file.getBytes(), headers);
////            ResponseEntity<Void> patchResponse = restTemplate.exchange(uploadLink, HttpMethod.PATCH, uploadEntity, Void.class);
//
//            HttpClient httpClient = HttpClients.createDefault();
//
//            // Create PATCH request
//            HttpPatch patchRequest = new HttpPatch(uploadLink);
//            patchRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/offset+octet-stream");
//            patchRequest.setHeader("Tus-Resumable", "1.0.0");
//            patchRequest.setHeader("Upload-Offset", "0");
//
//
//            File tempFile = File.createTempFile("temp", ".tmp");
//            file.transferTo(tempFile);
//            // Prepare file data
//            FileEntity fileEntity = new FileEntity(tempFile, ContentType.APPLICATION_OCTET_STREAM);
//            patchRequest.setEntity(fileEntity);
//
//            // Execute PATCH request
//            HttpResponse patchResponse = httpClient.execute(patchRequest);
//            System.out.println("patchHHHHHHHHHHHHHHHHHHHHHHHH");
//            System.out.println(patchResponse);
//
//            // 4. Gửi yêu cầu HEAD để xác minh quá trình tải lên
//            ResponseEntity<Void> headResponse = restTemplate.exchange(uploadLink, HttpMethod.HEAD, new HttpEntity<>(null, headers), Void.class);
//            System.out.println("HeadDDDDDDDDDDDDDDDDDDDDDDDD");
//            System.out.println(headResponse);
//            // Xử lý phản hồi HEAD để xác định trạng thái tải lên và cập nhật giao diện người dùng
//            // ...
//
//            model.addAttribute("message", "Video uploaded successfully!");
//        } catch (IOException e) {
//            e.printStackTrace();
//            model.addAttribute("message", "Failed to upload video: " + e.getMessage());
//        }
//
//        return "redirect:/course/exam";
//    }

}
