package com.online_store.product_service.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.online_store.product_service.client.user.UserClient;


@Component
public class UtilsService {
    private final UserClient userClient;

    public UtilsService(UserClient userClient) {
        this.userClient = userClient;
    }

    public Long getCurrentUserId() {
        return userClient.getCurrentUser().data().id();
    }

    public void checkImageFileType(MultipartFile file) {
        if (!file.getContentType().startsWith("image/")) {
            throw new Error("Invalid file type!");
        }
    }
}
