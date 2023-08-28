package io.davorpatech.fwk.auditing.jpa;

/**
 * Interface that provides access to JPA audit info.
 */
public interface AuditAccessor // NOSONAR
{
    Audit getAudit();
}
