package com.solipsism.shared.model;

import java.util.UUID;
import java.io.Serializable;

public class DataPackage implements Serializable {
    private String type;
    private UUID sender;
    private UUID receiver;
    private String content;
    private String fileName;
    private byte[] fileData;

    public DataPackage(String type, UUID sender, UUID receiver, String content) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public UUID getSender() {
        return sender;
    }

    public UUID getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
