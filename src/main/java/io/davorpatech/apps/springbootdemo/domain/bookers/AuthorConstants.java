package io.davorpatech.apps.springbootdemo.domain.bookers;

public interface AuthorConstants // NOSONAR
{
    String DOMAIN_NAME = "bookers.Author";

    int FULLNAME_MINLEN = 1;

    int FULLNAME_MAXLEN = 255;

    int EMAIL_MINLEN = 0;

    int EMAIL_MAXLEN = 255;

    int COUNTRY_MINLEN = 0;

    int COUNTRY_MAXLEN = 255;
}
