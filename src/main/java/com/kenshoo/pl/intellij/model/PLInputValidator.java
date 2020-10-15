package com.kenshoo.pl.intellij.model;

import java.util.Optional;
import java.util.stream.Stream;
import static com.google.common.base.Strings.isNullOrEmpty;


public class PLInputValidator {

    public final static PLInputValidator INSTANCE = new PLInputValidator();

    public Stream<ValidationError> validate(EntityInput input) {

        return Stream.of(
                validateTableNameExists(input),
                validateEntityNameExists(input),
                validateAllFieldsHaveType(input),
                validatePKDefined(input)

        ).filter(Optional::isPresent).map(Optional::get);
    }

    private Optional<ValidationError> validatePKDefined(EntityInput input) {
        return input.getFields().stream().anyMatch(f -> f.getFlags().isPk())
                ? Optional.empty()
                : Optional.of(new ValidationError("At least one field must be flagged as \'Primary Key\'."));
    }

    private Optional<ValidationError> validateTableNameExists(EntityInput input) {
        return isNullOrEmpty(input.getTableName().trim())
                ? Optional.of(new ValidationError("Table name must not be empty"))
                : Optional.empty();
    }

    private Optional<ValidationError> validateEntityNameExists(EntityInput input) {
        return isNullOrEmpty(input.getEntityName().trim())
                ? Optional.of(new ValidationError("Entity name must not be empty"))
                : Optional.empty();
    }

    private Optional<ValidationError> validateAllFieldsHaveType(EntityInput input) {
        return input.getFields()
                .stream()
                .filter(field -> field.getType() == null)
                .findFirst()
                .map(field -> new ValidationError("Field \"" + field.getFieldName() + "\" is missing a type"));
    }

}
