package server.mapper;

import server.dto.AddressDTO;
import server.dto.PersonalDetailDTO;
import server.dto.SupplierDTO;
import server.models.Address;
import server.models.PersonalDetail;
import server.models.Supplier;
import server.services.AddressService;
import server.services.PersonalDetailService;

public class SupplierMapper {

    private static final AddressService addressService = AddressService.getInstance();
    private static final PersonalDetailService personalDetailService = PersonalDetailService.getInstance();

    public static Supplier fromDTO(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setId(supplierDTO.getId());
        supplier.setName(supplierDTO.getName());

        if (supplierDTO.getPersonalDetails() != null) {
            // Проверка и сохранение PersonalDetail
            PersonalDetail personalDetail = new PersonalDetail();
            personalDetail.setFirstName(supplierDTO.getPersonalDetails().getFirstName());
            personalDetail.setLastName(supplierDTO.getPersonalDetails().getLastName());
            personalDetail.setPhoneNumber(supplierDTO.getPersonalDetails().getPhoneNumber());
            personalDetail.setEmail(supplierDTO.getPersonalDetails().getEmail());

            // Проверка на существование адреса
            Address address = supplierDTO.getPersonalDetails().getAddress() != null ?
                    addressService.findByAddress(supplierDTO.getPersonalDetails().getAddress()) :
                    null;

            if (address == null && supplierDTO.getPersonalDetails().getAddress() != null) {
                address = new Address();
                address.setCity(supplierDTO.getPersonalDetails().getAddress().getCity());
                address.setAddress(supplierDTO.getPersonalDetails().getAddress().getAddress());
                addressService.save(address);
            }

            personalDetail.setAddress(address);

            personalDetailService.save(personalDetail);

            supplier.setPersonalDetails(personalDetail);
        }

        return supplier;
    }

    public static SupplierDTO toDTO(Supplier supplier) {
        if (supplier == null) return null;

        PersonalDetailDTO personalDetailDTO = null;
        if (supplier.getPersonalDetails() != null) {
            PersonalDetail personalDetail = supplier.getPersonalDetails();

            AddressDTO addressDTO = null;
            if (personalDetail.getAddress() != null) {
                Address address = personalDetail.getAddress();
                addressDTO = AddressDTO.builder()
                        .city(address.getCity())
                        .address(address.getAddress())
                        .build();
            }

            personalDetailDTO = PersonalDetailDTO.builder()
                    .firstName(personalDetail.getFirstName())
                    .lastName(personalDetail.getLastName())
                    .phoneNumber(personalDetail.getPhoneNumber())
                    .email(personalDetail.getEmail())
                    .address(addressDTO)
                    .build();
        }

        return SupplierDTO.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .personalDetails(personalDetailDTO)
                .build();
    }
}
