package com.online_store.logistic_service.common.utils;


import org.springframework.stereotype.Component;

import com.online_store.logistic_service.client.user.UserClient;


@Component
public class UtilsService {
    private final UserClient userClient;

    public UtilsService(UserClient userClient) {
        this.userClient = userClient;
    }

    public Long getCurrentUserId() {
        return userClient.getCurrentUser().data().id();
    }

}
