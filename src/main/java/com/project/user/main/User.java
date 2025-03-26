package com.project.user.main;

import com.project.user.util.HibernateUtil;

public class User {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();
    }
}
