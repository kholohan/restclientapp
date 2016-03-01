package com.kylecorp.RestClientApp.logging;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class LogService
{

    public static Logger getLogger()
    {
        final Throwable t = new Throwable();
        t.fillInStackTrace();
        return LoggerFactory.getLogger(t.getStackTrace()[1].getClassName());
    }


    public static Logger getLogger(final String name)
    {
        return LoggerFactory.getLogger(name);
    }


    private LogService()
    {

    }

}
