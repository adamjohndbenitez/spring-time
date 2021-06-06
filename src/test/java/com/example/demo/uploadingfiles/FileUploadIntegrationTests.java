package com.example.demo.uploadingfiles;

import com.example.demo.uploadingfiles.storage.StorageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileUploadIntegrationTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private StorageService storageService;

    @LocalServerPort
    private int port;

    private ClassPathResource classPathResource;
    private ResponseEntity<String> responseEntity;

    @BeforeEach
    void setUp() {
        classPathResource = new ClassPathResource("testupload.txt", this.getClass());
    }

    @Test
    void shouldUploadFile() throws Exception {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("file", this.classPathResource);

        responseEntity = this.testRestTemplate
                .postForEntity("/", multiValueMap, String.class);

        assertThat(responseEntity.getStatusCode())
                .isEqualByComparingTo(HttpStatus.FOUND);
        assertThat(responseEntity.getHeaders().getLocation().toString())
                .startsWith("http://localhost:" + this.port + "/");

        then(storageService).should().store(any(MultipartFile.class));
    }

    /* FIXME: Issue was when "testupload.txt" was not in the same directory package hierarchy
              (com.example.demo.uploadingfiles) with this class exception is thrown -
              org.springframework.web.client.ResourceAccessException:
                  I/O error on POST request for "http://localhost:64483/":
                  class path resource [com/example/demo/uploadingfiles/testupload.txt]
                  cannot be resolved to URL because it does not exist; nested exception
                  is java.io.FileNotFoundException: class path resource
                  [com/example/demo/uploadingfiles/testupload.txt] cannot be resolved
                  to URL because it does not exist */

    @Test
    void shouldDownloadFile() {
        given(this.storageService.loadAsResource("testupload.txt"))
                .willReturn(this.classPathResource);

        responseEntity = this.testRestTemplate
                .getForEntity("/files/{filename}",
                        String.class,
                        "testupload.txt");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION))
                .isEqualTo("attachment; filename=\"testupload.txt\"");
        assertThat(responseEntity.getBody()).isEqualTo("Spring Framework");
    }

    @AfterEach
    void tearDown() {
        if (this.classPathResource != null) classPathResource = null;
        if (this.responseEntity != null) responseEntity = null;
    }
}
