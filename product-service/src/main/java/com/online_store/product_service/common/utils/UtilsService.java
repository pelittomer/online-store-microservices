package com.online_store.product_service.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.online_store.product_service.client.logistic.CompanyDetailsResponse;
import com.online_store.product_service.client.logistic.CompanyResponse;
import com.online_store.product_service.client.logistic.LogisticClient;
import com.online_store.product_service.client.logistic.ShipperResponse;
import com.online_store.product_service.client.user.UserClient;
import com.online_store.product_service.common.response.ApiResponse;

@Component
public class UtilsService {
    private final UserClient userClient;
    private final LogisticClient logisticClient;

    public UtilsService(UserClient userClient,
            LogisticClient logisticClient) {
        this.userClient = userClient;
        this.logisticClient = logisticClient;
    }

    public Long getCurrentUserId() {
        return userClient.getCurrentUser().data().id();
    }

    public void checkImageFileType(MultipartFile file) {
        if (!file.getContentType().startsWith("image/")) {
            throw new Error("Invalid file type!");
        }
    }

    public Long getShipperById(Long id) {
        ApiResponse<ShipperResponse> shipper = logisticClient.getShipperById(id);
        return shipper.data().id();
    }

    public ShipperResponse getShipper(Long id) {
        ApiResponse<ShipperResponse> shipper = logisticClient.getShipperById(id);
        return shipper.data();
    }

    public CompanyDetailsResponse getMyCompany() {
        return logisticClient.getMyCompany().data();
    }

    public CompanyResponse getCompanyById(Long id) {
        return logisticClient.getCompanyById(id).data();
    }
}
