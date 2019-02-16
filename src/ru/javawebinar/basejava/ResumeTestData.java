package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

public class ResumeTestData {
    public static void main(String[] args) throws Throwable {

        Resume resume = new Resume( "uuid01", "Григорий Кислин");

        resume.setMobilePhone("+7(921) 855-0482");
        resume.setEMail("gkislin@yandex.ru");
        resume.setSkype("grigory.kislin");
        resume.setHomePage("http://gkislin.ru/");
        resume.setSocialMedia("Профиль LinkedIn", "https://www.linkedin.com/in/gkislin");
        resume.setSocialMedia("Профиль GitHub", "https://github.com/gkislin");
        resume.setSocialMedia("Профиль Stackoverflow", "https://stackoverflow.com/users/548473");

        resume.sections[Sections.OBJECTIVE.ordinal()] = new ResumeDataString("Позиция", "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям" );
        resume.sections[Sections.PERSONAL.ordinal()] = new ResumeDataString("Личные качества", "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры." );
        resume.sections[Sections.ACHIEVEMENT.ordinal()] = new ResumeDataStrings("Достижения",
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
                );
        resume.sections[Sections.QUALIFICATIONS.ordinal()] = new ResumeDataStrings("Квалификация",
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
                );
        resume.sections[Sections.EXPERIENCE.ordinal()] = new ResumeData<ResumeDataLifePeriods>("Опыт работы", true,
                new ResumeDataLifePeriods("Java Online Projects",
                        new ResumeDataDate("Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.",
                                new DateInterval(10, 2013, 0, 0))),
                new ResumeDataLifePeriods("Wrike",
                        new ResumeDataDate("Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                                new DateInterval(10, 2014, 1, 2016))),
                new ResumeDataLifePeriods("RIT Center",
                        new ResumeDataDate("Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python",
                                new DateInterval(4, 2012, 10, 2014))),
                new ResumeDataLifePeriods("Luxoft (Deutsche Bank)",
                        new ResumeDataDate("Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.",
                                new DateInterval(12, 2010, 4, 2012))),
                new ResumeDataLifePeriods("Yota",
                        new ResumeDataDate("Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)",
                                new DateInterval(6, 2008, 12, 2010))),
                new ResumeDataLifePeriods("Enkata",
                        new ResumeDataDate("Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).",
                                new DateInterval(3, 2007, 6, 2008))),
                new ResumeDataLifePeriods("Siemens AG",
                        new ResumeDataDate("Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).",
                                new DateInterval(1, 2005, 2, 2007))),
                new ResumeDataLifePeriods("Alcatel",
                        new ResumeDataDate("Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                                new DateInterval(9, 1997, 1, 2005)))
                );
        resume.sections[Sections.EDUCATION.ordinal()] = new ResumeData<ResumeDataLifePeriods>("Образование", true,
                new ResumeDataLifePeriods("Coursera",
                        new ResumeDataDate("\"Functional Programming Principles in Scala\" by Martin Odersky", null,
                                new DateInterval(3, 2013, 5, 2013))),
                new ResumeDataLifePeriods("Luxoft",
                        new ResumeDataDate("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null,
                                new DateInterval(3, 2011, 4, 2011))),
                new ResumeDataLifePeriods("Siemens AG",
                        new ResumeDataDate("3 месяца обучения мобильным IN сетям (Берлин)", null,
                                new DateInterval(1, 2005, 4, 2005))),
                new ResumeDataLifePeriods("Alcatel",
                        new ResumeDataDate("6 месяцев обучения цифровым телефонным сетям (Москва)", null,
                                new DateInterval(9, 1997, 3, 1998))),
                new ResumeDataLifePeriods("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                        new ResumeDataDate("Аспирантура (программист С, С++)", null,
                                new DateInterval(9, 1993, 7, 1996)),
                        new ResumeDataDate("Инженер (программист Fortran, C)", null,
                                new DateInterval(9, 1987, 7, 1993))),
                new ResumeDataLifePeriods("Заочная физико-техническая школа при МФТИ",
                        new ResumeDataDate("Закончил с отличием", null,
                                new DateInterval(9, 1984, 6, 1987)))
                );

        Resume resumeInitial = resume;

        //make a copy of initial resume
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        resume =  new Resume( "uuid01", "Григорий Кислин");

        resume.setMobilePhone("+7(921) 855-0482");
        resume.setEMail("gkislin@yandex.ru");
        resume.setSkype("grigory.kislin");
        resume.setHomePage("http://gkislin.ru/");
        resume.setSocialMedia("Профиль LinkedIn", "https://www.linkedin.com/in/gkislin");
        resume.setSocialMedia("Профиль GitHub", "https://github.com/gkislin");
        resume.setSocialMedia("Профиль Stackoverflow", "https://stackoverflow.com/users/548473");

        resume.sections[Sections.OBJECTIVE.ordinal()] = new ResumeDataString("Позиция", "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям" );
        resume.sections[Sections.PERSONAL.ordinal()] = new ResumeDataString("Личные качества", "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры." );
        resume.sections[Sections.ACHIEVEMENT.ordinal()] = new ResumeDataStrings("Достижения",
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        );
        resume.sections[Sections.QUALIFICATIONS.ordinal()] = new ResumeDataStrings("Квалификация",
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
        );
        resume.sections[Sections.EXPERIENCE.ordinal()] = new ResumeData<ResumeDataLifePeriods>("Опыт работы", true,
                new ResumeDataLifePeriods("Java Online Projects",
                        new ResumeDataDate("Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.",
                                new DateInterval(10, 2013, 0, 0))),
                new ResumeDataLifePeriods("Wrike",
                        new ResumeDataDate("Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                                new DateInterval(10, 2014, 1, 2016))),
                new ResumeDataLifePeriods("RIT Center",
                        new ResumeDataDate("Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python",
                                new DateInterval(4, 2012, 10, 2014))),
                new ResumeDataLifePeriods("Luxoft (Deutsche Bank)",
                        new ResumeDataDate("Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.",
                                new DateInterval(12, 2010, 4, 2012))),
                new ResumeDataLifePeriods("Yota",
                        new ResumeDataDate("Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)",
                                new DateInterval(6, 2008, 12, 2010))),
                new ResumeDataLifePeriods("Enkata",
                        new ResumeDataDate("Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).",
                                new DateInterval(3, 2007, 6, 2008))),
                new ResumeDataLifePeriods("Siemens AG",
                        new ResumeDataDate("Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).",
                                new DateInterval(1, 2005, 2, 2007))),
                new ResumeDataLifePeriods("Alcatel",
                        new ResumeDataDate("Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                                new DateInterval(9, 1997, 1, 2005)))
        );
        resume.sections[Sections.EDUCATION.ordinal()] = new ResumeData<ResumeDataLifePeriods>("Образование", true,
                new ResumeDataLifePeriods("Coursera",
                        new ResumeDataDate("\"Functional Programming Principles in Scala\" by Martin Odersky", null,
                                new DateInterval(3, 2013, 5, 2013))),
                new ResumeDataLifePeriods("Luxoft",
                        new ResumeDataDate("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null,
                                new DateInterval(3, 2011, 4, 2011))),
                new ResumeDataLifePeriods("Siemens AG",
                        new ResumeDataDate("3 месяца обучения мобильным IN сетям (Берлин)", null,
                                new DateInterval(1, 2005, 4, 2005))),
                new ResumeDataLifePeriods("Alcatel",
                        new ResumeDataDate("6 месяцев обучения цифровым телефонным сетям (Москва)", null,
                                new DateInterval(9, 1997, 3, 1998))),
                new ResumeDataLifePeriods("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                        new ResumeDataDate("Аспирантура (программист С, С++)", null,
                                new DateInterval(9, 1993, 7, 1996)),
                        new ResumeDataDate("Инженер (программист Fortran, C)", null,
                                new DateInterval(9, 1987, 7, 1993))),
                new ResumeDataLifePeriods("Заочная физико-техническая школа при МФТИ",
                        new ResumeDataDate("Закончил с отличием", null,
                                new DateInterval(9, 1984, 6, 1987)))
        );

        Resume resumeCopy = resume;

        //make a slightly different copy of initial resume
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        resume =  new Resume( "uuid01", "Григорий Кислин");

        resume.setMobilePhone("+7(921) 855-0482");
        resume.setEMail("gkislin@yandex.ru");
        resume.setSkype("grigory.kislin");
        resume.setHomePage("http://gkislin.ru/");
        resume.setSocialMedia("Профиль LinkedIn", "https://www.linkedin.com/in/gkislin");
        resume.setSocialMedia("Профиль GitHub", "https://github.com/gkislin");
        resume.setSocialMedia("Профиль Stackoverflow", "https://stackoverflow.com/users/548473");

        resume.sections[Sections.OBJECTIVE.ordinal()] = new ResumeDataString("Позиция", "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям" );
        resume.sections[Sections.PERSONAL.ordinal()] = new ResumeDataString("Личные качества", "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры." );
        resume.sections[Sections.ACHIEVEMENT.ordinal()] = new ResumeDataStrings("Достижения",
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        );
        resume.sections[Sections.QUALIFICATIONS.ordinal()] = new ResumeDataStrings("Квалификация",
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
        );
        resume.sections[Sections.EXPERIENCE.ordinal()] = new ResumeData<ResumeDataLifePeriods>("Опыт работы", true,
                new ResumeDataLifePeriods("Java Online Projects",
                        new ResumeDataDate("Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.",
                                new DateInterval(10, 2013, 0, 0))),
                new ResumeDataLifePeriods("Wrike",
                        new ResumeDataDate("Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                                new DateInterval(10, 2014, 1, 2016))),
                new ResumeDataLifePeriods("RIT Center",
                        new ResumeDataDate("Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python",
                                new DateInterval(4, 2012, 10, 2014))),
                new ResumeDataLifePeriods("Luxoft (Deutsche Bank)",
                        new ResumeDataDate("Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.",
                                new DateInterval(12, 2010, 4, 2012))),
                new ResumeDataLifePeriods("Yota",
                        new ResumeDataDate("Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)",
                                new DateInterval(6, 2008, 12, 2010))),
                new ResumeDataLifePeriods("Enkata",
                        new ResumeDataDate("Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).",
                                new DateInterval(3, 2007, 6, 2008))),
                new ResumeDataLifePeriods("Siemens AG",
                        new ResumeDataDate("Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).",
                                new DateInterval(1, 2005, 2, 2007))),
                new ResumeDataLifePeriods("Alcatel",
                        new ResumeDataDate("Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                                new DateInterval(9, 1997, 1, 2005)))
        );
        resume.sections[Sections.EDUCATION.ordinal()] = new ResumeData<ResumeDataLifePeriods>("Образование", true,
                new ResumeDataLifePeriods("Coursera",
                        new ResumeDataDate("\"Functional Programming Principles in Scala\" by Martin Odersky", null,
                                new DateInterval(3, 2013, 5, 2013))),
                new ResumeDataLifePeriods("Luxoft",
                        new ResumeDataDate("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null,
                                new DateInterval(3, 2011, 4, 2011))),
                new ResumeDataLifePeriods("Siemens AG",
                        new ResumeDataDate("3 месяца обучения мобильным IN сетям (Берлин)", null,
                                new DateInterval(1, 2005, 4, 2005))),
                new ResumeDataLifePeriods("Alcatel",
                        new ResumeDataDate("6 месяцев обучения цифровым телефонным сетям (Москва)", null,
                                new DateInterval(9, 1997, 3, 1998))),
                new ResumeDataLifePeriods("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                        new ResumeDataDate("Аспирантура (программист С, С++)", null,
                                new DateInterval(9, 1993, 7, 1996)),
                        new ResumeDataDate("Инженер (программист Fortran, C)", null,
                                new DateInterval(9, 1987, 7, 1993))),
                new ResumeDataLifePeriods("Заочная физико-техническая школа при МФТИ",
                        new ResumeDataDate("Закончил с отличием", null,
                                new DateInterval(9, 1984, 6, 1986)))//a slight difference
        );

        Resume resumeDifference = resume;

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
