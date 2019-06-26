package com.gestaoespacos.app.security;

import org.joda.time.DateTime;

public interface DateService {

    /**
     * @return current date at the moment of the call
     */
    DateTime now();
}
