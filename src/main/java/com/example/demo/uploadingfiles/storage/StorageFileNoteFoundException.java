package com.example.demo.uploadingfiles.storage;

public class StorageFileNoteFoundException extends StorageException {
    public StorageFileNoteFoundException(String message) {
        super(message);
    }

    public StorageFileNoteFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
