package io.davorpatech.apps.springbootdemo.domain.bookers;

public interface BookConstants // NOSONAR
{
    String DOMAIN_NAME = "bookers.Book";

    int ISBN_MINLEN = 11;

    int ISBN_MAXLEN = 20;

    int TITLE_MINLEN = 1;

    int TITLE_MAXLEN = 255;

    int SYNOPSIS_MINLEN = 0;

    int SYNOPSIS_MAXLEN = 2048;
}
