package io.davorpatech.apps.springbootdemo.services.peopledir;

import io.davorpatech.apps.springbootdemo.persistence.model.peopledir.Person;
import io.davorpatech.fwk.validation.ValidatedGroups;
import io.davorpatech.fwk.validation.groups.OnCreate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;

@Validated({ Default.class })
public interface PeopleService
{
    List<Person> findAll();

    List<Person> findAllBySurnameInitialAndAge(
            final @Nullable String initial,
            final @Nullable Long age);

    Person read(
            final @NonNull Long id);

    @ValidatedGroups({ OnCreate.class })
    Person create(
            final @NonNull @Valid Person person);
}
