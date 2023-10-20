package projeto.dio.controller.dto;

import java.util.List;

import projeto.dio.domain.model.User;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record UserDto(
        Long id,
        String name,
        long registration,
        AccountDto account,
        List<TransactionDto> transaction) {

    public UserDto(User model) {
        this(
                model.getId(),
                model.getName(),
                model.getRegistration(),
                ofNullable(model.getAccount()).map(AccountDto::new).orElse(null),
                ofNullable(model.getTransaction()).orElse(emptyList()).stream().map(TransactionDto::new).collect(toList())
        );
    }

    public User toModel() {
        User model = new User();
        model.setId(this.id);
        model.setName(this.name);
        model.setRegistration(this.registration);
        model.setAccount(ofNullable(this.account).map(AccountDto::toModel).orElse(null));
        model.setTransaction(ofNullable(this.transaction).orElse(emptyList()).stream().map(TransactionDto::toModel).collect(toList()));
        return model;
    }

}