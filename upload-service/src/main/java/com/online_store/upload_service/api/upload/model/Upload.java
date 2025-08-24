package com.online_store.upload_service.api.upload.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "uploads")
public class Upload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "file_name")
    private String fileName;

    @Column(nullable = false, name = "file_type")
    private String fileType;

    @Lob
    @Column(name = "file_content", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] fileContent;

    public Upload() {
    }

    public Upload(String fileName, String fileType, byte[] fileContent) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileContent = fileContent;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }
}
