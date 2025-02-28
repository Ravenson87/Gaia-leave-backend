package com.caci.gaia_leave.shared_tools.helper;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class AllHelpers {

    public static <T> List<T> listConverter(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }



}
