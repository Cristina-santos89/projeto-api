package projeto.dio.controller.dto;

import projeto.dio.domain.model.Transaction;

public record TransactionDto(Long id, String icon, String description) {

    public TransactionDto(Transaction model) {
        this(model.getId(), model.getIcon(), model.getDescription());
    }

    public Transaction toModel() {
        final Transaction model = new Transaction();
        model.setId(this.id);
        model.setIcon(this.icon);
        model.setDescription(this.description);
        return model;
    }
}