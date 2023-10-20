package projeto.dio.controller.dto;

import java.math.BigDecimal;

import projeto.dio.domain.model.Account;

public record AccountDto(Long id, String registration, BigDecimal balance, BigDecimal amount) {

    public AccountDto(Account model) {
        this(model.getId(), model.getRegistration(), model.getBalance(), model.getAmount());
    }

    public Account toModel() {
        Account model = new Account();
        model.setId(this.id);
        model.setRegistration(this.registration);
        model.setBalance(this.balance);
        model.setAmount(this.amount);
        return model;
    }
}