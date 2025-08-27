package com.online_store.user_service.api.address.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.online_store.user_service.api.address.dto.AddressRequest;
import com.online_store.user_service.api.address.dto.AddressResponse;
import com.online_store.user_service.api.address.model.Address;
import com.online_store.user_service.api.address.repository.AddressRepository;
import com.online_store.user_service.common.utils.UtilsService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AddressService {
    private final AddressRepository repository;
    private final UtilsService utilsService;

    public AddressService(AddressRepository repository,
            UtilsService utilsService) {
        this.repository = repository;
        this.utilsService = utilsService;
    }

    public String createAddress(AddressRequest dto) {
        Long userId = this.utilsService.getCurrentUserId();

        Address address = createAddressMapper(dto, userId);
        repository.save(address);

        return "Address created successfully.";
    }

    public List<AddressResponse> listAddresses() {
        Long userId = utilsService.getCurrentUserId();

        return repository.findByUser(userId)
                .stream()
                .sorted(Comparator.comparing(Address::getCreatedAt).reversed())
                .map(this::getAddressMapper)
                .collect(Collectors.toList());
    }

    public AddressResponse getAddress(Long id) {
        Long userId = utilsService.getCurrentUserId();
        Address address = findByUserAndId(userId, id);
        return getAddressMapper(address);
    }

    public String deleteAddress(Long addressId) {
        Long accountId = utilsService.getCurrentUserId();
        Address addressToDelete = findByUserAndId(accountId, addressId);
        repository.delete(addressToDelete);
        return "Address deleted successfully.";
    }

    private Address findByUserAndId(Long userId, Long addressId) {
        return repository.findByUserAndId(userId, addressId)
                .orElseThrow(() -> {
                    return new EntityNotFoundException("Address with ID " + addressId + " not found.");
                });
    }

    private Address createAddressMapper(AddressRequest dto, Long userId) {
        return new Address(
                dto.city(),
                dto.district(),
                dto.neighborhood(),
                dto.street(),
                dto.buildingNumber(),
                dto.doorNumber(),
                dto.phone(),
                userId);
    }

    private AddressResponse getAddressMapper(Address dto) {
        return new AddressResponse(
                dto.getId(),
                dto.getCity(),
                dto.getDistrict(),
                dto.getNeighborhood(),
                dto.getStreet(),
                dto.getBuildingNumber(),
                dto.getDoorNumber(),
                dto.getPhone());
    }

}
