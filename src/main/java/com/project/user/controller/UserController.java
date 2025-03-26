package com.project.user.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.user.configuration.UserConfiguration;
import com.project.user.model.User;
import com.project.user.util.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class UserController {
    @RequestMapping(value = "/api/v1/users", method = RequestMethod.GET)
    public String getUserByName(ModelMap model, @RequestParam(name = "name") String name) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM User AS user "
                + "WHERE user.name = :name "
                + "ORDER BY user.id");
        query.setParameter("name", name);

        Object result = query.uniqueResult();

        if (result != null) {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            context.register(UserConfiguration.class);
            String userId = ((User) result).getId();

            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
            builder.addPropertyValue("id", userId);
            builder.addPropertyValue("name", ((User) result).getName());
            builder.addPropertyValue("age", ((User) result).getAge());
            builder.addPropertyValue("userContact", ((User) result).getUserContact());
            beanFactory.registerBeanDefinition("user", builder.getRawBeanDefinition());
            context.refresh();

            Map<String, User> userMap = new ObjectMapper()
                    .convertValue(context.getBean("userMap"),
                    new TypeReference<Map<String, User>>() {});

            model.addAttribute("userID", userMap.get(userId).getId());
            model.addAttribute("name", userMap.get(userId).getName());
            model.addAttribute("age", userMap.get(userId).getAge());
            model.addAttribute("userContactID", userMap.get(userId).getUserContact().getId());
            model.addAttribute("address", userMap.get(userId).getUserContact().getAddress());

            context.close();
        } else {
            return String.valueOf(HttpStatus.NOT_FOUND);
        }

        return "user";
    }
}