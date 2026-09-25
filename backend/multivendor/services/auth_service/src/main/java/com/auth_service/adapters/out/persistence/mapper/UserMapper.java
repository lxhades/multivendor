package com.auth_service.adapters.out.persistence.mapper;


import com.auth_service.adapters.out.persistence.entity.AddressEntity;
import com.auth_service.adapters.out.persistence.entity.UserEntity;
import com.auth_service.domain.model.aggregate.User;
import com.auth_service.domain.model.entity.Address;
import com.auth_service.domain.model.vo.Email;
import com.auth_service.domain.model.vo.PasswordHash;
import com.auth_service.domain.model.vo.Phone;
import com.auth_service.domain.model.vo.UserId;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class UserMapper {

    // DOMAIN → ENTITY
    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(user.getUserId().value());
        entity.setEmail(user.getEmail().getValue());
        entity.setPhone(user.getPhone().value());
        entity.setPasswordHash(user.getPasswordHash().value());
        entity.setStatus(user.getStatus());
        entity.setAddresses(
                user.getAddresses().stream()
                        .map(this::toAddressEntity)
                        .toList()
        );
        entity.setRoles(new HashSet<>(user.getRoles()));
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());

        return entity;
    }


    // ENTITY → DOMAIN
    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.reconstitute(
                UserId.of(entity.getId()),
                Email.of(entity.getEmail()),
                new Phone(entity.getPhone()),
                new PasswordHash(entity.getPasswordHash()),
                entity.getStatus(),
                new HashSet<>(entity.getRoles()),
                entity.getAddresses().stream()
                        .map(this::toAddressDomain)
                        .toList(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
    private AddressEntity toAddressEntity(Address address) {
        AddressEntity entity = new AddressEntity();
        entity.setId(address.getAddressId());
        entity.setReceiverName(address.getReceiverName());
        entity.setPhone(address.getPhone().value());
        entity.setLocation(address.getLocation());
        entity.setDetail(address.getDetail());
        entity.setDefault(address.isDefault());
        return entity;
    }
    private Address toAddressDomain(AddressEntity entity) {
        return new Address(
                entity.getId(),
                entity.getReceiverName(),
                new Phone(entity.getPhone()),
                entity.getLocation(),
                entity.getDetail(),
                entity.isDefault()
        );
    }
}