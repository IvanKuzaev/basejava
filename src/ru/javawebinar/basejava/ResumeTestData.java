package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.List;

public class ResumeTestData {

    public static Resume fillDummyResume(Resume resume, int dummy) {

        resume.setContact(Contacts.MOBILE_PHONE, "+7(123) 456-78" + dummy);
        resume.setContact(Contacts.SKYPE, "dummy.dummy" + dummy);
        resume.setContact(Contacts.EMAIL, "dummy" + dummy + "@dummy.ru");
//        resume.setContact(Contacts.HOMEPAGE, "http://dummy" + dummy + ".ru");
        resume.setContact(Contacts.LINKEDIN, "https://www.linkedin.com/in/dummy" + dummy);
//        resume.setContact(Contacts.GITHUB, "https://github.com/dummy" + dummy);
        resume.setContact(Contacts.STACKOVERFLOW, "https://stackoverflow.com/users/dummy" + dummy);

        resume.setSection(Sections.OBJECTIVE, new StringSection("Objective" + dummy));
        resume.setSection(Sections.PERSONAL, new StringSection("Personal data " + dummy));
        resume.setSection(Sections.ACHIEVEMENTS, new StringListSection("Achievement1_" + dummy, "Achievement2_" + dummy, "Achievement3_" + dummy));
//        resume.setSection(Sections.QUALIFICATIONS, new StringListSection("Qualification1_" + dummy, "Qualification2_" + dummy, "Qualification3_" + dummy));
//        resume.setSection(Sections.EXPERIENCE, new BioSection(
//                new LifeStage(new Organization("Organization1_" + dummy, "http://organization1_" + dummy + ".com"),
//                        new LifePeriod(LocalDate.of(2013, 10, 1), LocalDate.of(9999, 1, 1),"Position1_" + dummy, "content1_" + dummy)),
//                new LifeStage(new Organization("Organization2_" + dummy, "http://organization2_" + dummy + ".com"),
//                        new LifePeriod(LocalDate.of(2012, 10, 1), LocalDate.of(2009, 1, 1),"Position2_" + dummy, "content2_" + dummy))
//        ));
        resume.setSection(Sections.EDUCATION, new BioSection(
                new LifeStage(new Organization("Institute1_" + dummy, "https://institute1_" + dummy + ".com"),
                        new LifePeriod(LocalDate.of(2013, 3, 1), LocalDate.of(2011, 5, 1),"study1_" + dummy, null)),
                new LifeStage(new Organization("Institute2_" + dummy, "https://institute2_" + dummy + ".com"),
                        new LifePeriod(LocalDate.of(2010, 3, 1), LocalDate.of(2005, 5, 1),"study2_" + dummy, null))
        ));

        return resume;
    }

    public static Resume fillResume(Resume resume) {

        resume.setContact(Contacts.MOBILE_PHONE, "+7(921) 855-0482");
        resume.setContact(Contacts.SKYPE, "grigory.kislin");
        resume.setContact(Contacts.EMAIL, "gkislin@yandex.ru");
        resume.setContact(Contacts.HOMEPAGE, "http://gkislin.ru/");
        resume.setContact(Contacts.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.setContact(Contacts.GITHUB, "https://github.com/gkislin");
        resume.setContact(Contacts.STACKOVERFLOW, "https://stackoverflow.com/users/548473");

        resume.setSection(Sections.OBJECTIVE, new StringSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.setSection(Sections.PERSONAL, new StringSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.setSection(Sections.ACHIEVEMENTS, new StringListSection(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        ));
        resume.setSection(Sections.QUALIFICATIONS, new StringListSection(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
                "Родной русский, английский \"upper intermediate\""
        ));
        resume.setSection(Sections.EXPERIENCE, new BioSection(
                new LifeStage(new Organization("Java Online Projects", "http://javaops.ru/"),
                        new LifePeriod(LocalDate.of(2013, 10, 1), LocalDate.of(9999, 1, 1),"Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.")),
                new LifeStage(new Organization("Wrike", "https://www.wrike.com/"),
                        new LifePeriod(LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1),"Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")),
                new LifeStage(new Organization("RIT Center", null),
                        new LifePeriod(LocalDate.of(2012, 4, 1), LocalDate.of(2014, 10, 1),"Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python")),
                new LifeStage(new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/"),
                        new LifePeriod(LocalDate.of(2010, 12, 1), LocalDate.of(2014, 4, 1),"Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.")),
                new LifeStage(new Organization("Yota", "https://www.yota.ru/"),
                        new LifePeriod(LocalDate.of(2008, 6, 1), LocalDate.of(2010, 12, 1),"Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)")),
                new LifeStage(new Organization("Enkata", "http://enkata.com/"),
                        new LifePeriod(LocalDate.of(2007, 3, 1), LocalDate.of(2008, 6, 1),"Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).")),
                new LifeStage(new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html"),
                        new LifePeriod(LocalDate.of(2005, 1, 1), LocalDate.of(2007, 2, 1),"Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).")),
                new LifeStage(new Organization("Alcatel", "http://www.alcatel.ru/"),
                        new LifePeriod(LocalDate.of(1997, 9, 1), LocalDate.of(2005, 1, 1),"Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."))
        ));
        resume.setSection(Sections.EDUCATION, new BioSection(
                new LifeStage(new Organization("Coursera", "https://www.coursera.org/course/progfun"),
                        new LifePeriod(LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1),"\"Functional Programming Principles in Scala\" by Martin Odersky", null)),
                new LifeStage(new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
                        new LifePeriod(LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1),"Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null)),
                new LifeStage(new Organization("Siemens AG", "http://www.siemens.ru/"),
                        new LifePeriod(LocalDate.of(2005, 1, 1), LocalDate.of(2005, 4, 1),"3 месяца обучения мобильным IN сетям (Берлин)", null)),
                new LifeStage(new Organization("Alcatel", "http://www.alcatel.ru/"),
                        new LifePeriod(LocalDate.of(1997, 9, 1), LocalDate.of(1998, 3, 1),"6 месяцев обучения цифровым телефонным сетям (Москва)", null)),
                new LifeStage(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/"),
                        new LifePeriod(LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1),"Аспирантура (программист С, С++)", null),
                        new LifePeriod(LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1),"Инженер (программист Fortran, C)", null)),
                new LifeStage(new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/"),
                        new LifePeriod(LocalDate.of(1984, 9, 1), LocalDate.of(1987, 6, 1),"\tЗакончил с отличием", null))
        ));

        return resume;
    }

    public static void main(String[] args) throws Throwable {

        //make initial resume
        Resume resumeInitial = new Resume("uuid01", "Григорий Кислин");
        fillResume(resumeInitial);

        //make a copy of initial resume
        Resume resumeCopy = new Resume("uuid01", "Григорий Кислин");
        fillResume(resumeCopy);

        //make a slightly different copy of initial resume
        Resume resumeDifference = new Resume("uuid01", "Григорий Кислин");
        fillResume(resumeDifference);
        List<LifeStage> lifeStages = (List<LifeStage>)resumeDifference.getSection(Sections.EXPERIENCE).getData();
        List<LifePeriod> lifePeriods = lifeStages.get(7).getData();
        lifePeriods.get(0).setStartDate(LocalDate.of(1983, 9, 1));

        //testing toString() method
        System.out.println(resumeInitial);

        //testing equals() method
        System.out.println("resumeInitial.equals(resumeCopy) = " + resumeInitial.equals(resumeCopy));
        System.out.println("resumeInitial.equals(resumeDifference) = " + resumeInitial.equals(resumeDifference));

        //testing hashCode() method
        System.out.println("resumeInitial.hashCode() = " + resumeInitial.hashCode());
        System.out.println("resumeCopy.hashCode() = " + resumeCopy.hashCode());
        System.out.println("resumeDifference.hashCode() = " + resumeDifference.hashCode());

    }
}
