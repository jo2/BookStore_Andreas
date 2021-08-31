package com.adesso.commentator.bookstore.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntityWithIdNotFondException extends IllegalArgumentException {

    Class<?> entityType;
    Long id;

    public EntityWithIdNotFondException(Class<?> entitytype, Long id, String message) {
        super(message);
        this.entityType = entitytype;
        this.id = id;
    }
}
