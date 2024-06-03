package com.t2207e.sem4.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class VimeoApiService {
    private final String VIMEO_API_BASE_URL = "https://api.vimeo.com";

    private final RestTemplate restTemplate;

    public VimeoApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getVideo(String videoId, String accessToken) throws JsonProcessingException {
        String url = VIMEO_API_BASE_URL + "/videos/" + videoId;
        // Thêm header Authorization chứa access token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        // Gửi yêu cầu GET đến Vimeo API
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        String urlVideo = root.path("player_embed_url").asText();
        // Trả về nội dung của phản hồi
        return urlVideo;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public class UploadTicket {
        private String uri;
        private String completeUri;
        private String uploadLinkSecure;
    }

    public class UploadRequest {
        private Upload upload;

        public UploadRequest() {
            this.upload = new Upload();
        }

        public Upload getUpload() {
            return upload;
        }

        public void setUpload(Upload upload) {
            this.upload = upload;
        }

        public static class Upload {
            private String approach = "tus";
            private int size;

            public String getApproach() {
                return approach;
            }

            public void setApproach(String approach) {
                this.approach = approach;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }
        }
    }

}
