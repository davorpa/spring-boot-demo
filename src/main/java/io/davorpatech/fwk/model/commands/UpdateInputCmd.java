package io.davorpatech.fwk.model.commands;

import io.davorpatech.fwk.model.Identifiable;
import io.davorpatech.fwk.model.ValueObject;

import java.io.Serializable;

/**
 * Input Command DTO (Data Transfer Object) that defines the contract of
 * those data objects used to update a concrete domain business entity.
 *
 * @param <ID> component type for the business entity ID.
 */
public interface UpdateInputCmd<ID extends Serializable> // NOSONAR
        extends ValueObject, Identifiable<ID> // NOSONAR
{

}
