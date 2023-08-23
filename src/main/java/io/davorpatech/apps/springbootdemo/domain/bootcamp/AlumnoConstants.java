package io.davorpatech.apps.springbootdemo.domain.bootcamp;

public interface AlumnoConstants // NOSONAR
{
    String DOMAIN_NAME = "bootcamp.Alumno";

    String NID_REGEX = "[A-Z0-9]+";

    int NID_MINLEN = 1;

    int NID_MAXLEN = 20;

    int FULLNAME_MINLEN = 1;

    int FULLNAME_MAXLEN = 255;
}
