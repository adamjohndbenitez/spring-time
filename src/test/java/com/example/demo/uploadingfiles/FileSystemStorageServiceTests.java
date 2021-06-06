package com.example.demo.uploadingfiles;

import com.example.demo.uploadingfiles.storage.FileSystemStorageService;
import com.example.demo.uploadingfiles.storage.StorageException;
import com.example.demo.uploadingfiles.storage.StorageProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileSystemStorageServiceTests {
    private StorageProperties storageProperties = new StorageProperties();
    private FileSystemStorageService fileSystemStorageService;

    @BeforeEach
    void setUp() {
        storageProperties.setLocation("target/files/" + Math.abs(new Random().nextLong()));
        fileSystemStorageService = new FileSystemStorageService(this.storageProperties);
        fileSystemStorageService.init();
    }

    @Test
    void loadNonExistent() {
        assertThat(this.fileSystemStorageService.load("foo.txt")).doesNotExist();
    }

    @Test
    void saveAndLoad() {
        fileSystemStorageService.store(
                new MockMultipartFile(
                        "foo", "foo.txt",
                        MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes()));

        assertThat(fileSystemStorageService.load("foo.txt")).exists();
    }

    @Test
    void saveRelativePathNotPermitted() {
        assertThrows(StorageException.class, () -> this.fileSystemStorageService.store(
                new MockMultipartFile(
                        "foo", "../foo.txt",
                        MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes())));
    }

    @Test
    void saveAbsolutePathNotPermitted() {
        assertThrows(StorageException.class, () -> this.fileSystemStorageService.store(
                new MockMultipartFile(
                        "foo", "/etc/passwd",
                        MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes())));
    }

    @Test
    @EnabledOnOs({OS.LINUX}) // Ignored when running test results, only enable on linux os.
    void saveAbsolutePathInFilenamePermitted() {
        // Unix file systems (e.g. ext4) allows backslash '\' in file names.
        String filename = "\\etc\\passwd";
        fileSystemStorageService.store(
                new MockMultipartFile(
                        filename,
                        filename,
                        MediaType.TEXT_PLAIN_VALUE,
                        "Hello World".getBytes()));

        assertTrue(Files.exists(
                Paths.get(this.storageProperties.getLocation()).resolve(Paths.get(filename))));
    }

    @Test
    void savePermitted() {
        fileSystemStorageService.store(new MockMultipartFile("foo", "bar/../foot.xt", MediaType.TEXT_PLAIN_VALUE, "hello, World".getBytes()));
    }
}
