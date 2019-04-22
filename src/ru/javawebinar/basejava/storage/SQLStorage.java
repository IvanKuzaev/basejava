package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.ProcessQueryResult;
import ru.javawebinar.basejava.sql.SQLHelper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLStorage implements Storage {
    private final SQLHelper sqlHelper;

    private final String complexRetrieveQuery = ""
        + " WITH"
        + " bio_sections AS ("
        + "     SELECT resume_uuid, STRING_AGG(CONCAT(type, '<bsh>', life_stage), '<bs>') string_bio_sections"
        + "     FROM (SELECT resume_uuid, type, STRING_AGG(CONCAT(o.id, '<e>', o.title, '<e>', COALESCE(o.description, '<null>'), '<e>', COALESCE(o.weblink, '<null>'), '<lsh>', life_period), '<ls>') life_stage"
        + "           FROM (SELECT resume_uuid,"
        + "                        type,"
        + "                        organization_id,"
        + "                        STRING_AGG(CONCAT(start_date, '<e>', end_date, '<e>', title, '<e>', COALESCE(description, '<null>')), '<lp>') life_period"
        + "                 FROM biography b"
        + "                 GROUP BY resume_uuid, type, organization_id) sq1"
        + "                       LEFT JOIN organization o ON sq1.organization_id=o.id"
        + "           GROUP BY resume_uuid, type) sq2"
        + "     WHERE resume_uuid = COALESCE(?, resume_uuid)"
        + "     GROUP BY resume_uuid"
        + " ),"
        + " sections AS ("
        + "     SELECT resume_uuid, STRING_AGG(CONCAT(type, '<h>', value), '<s>') string_sections"
        + "     FROM section"
        + "     WHERE resume_uuid = COALESCE(?, resume_uuid)"
        + "     GROUP BY resume_uuid"
        + " ),"
        + " contacts AS ("
        + "       SELECT resume_uuid, STRING_AGG(CONCAT(type, '<e>', value), '<s>') string_contacts"
        + "       FROM contact"
        + "       WHERE resume_uuid = COALESCE(?, resume_uuid)"
        + "       GROUP BY resume_uuid"
        + " )"
        + " SELECT r.uuid, r.full_name, bs.string_bio_sections, s.string_sections, c.string_contacts"
        + " FROM resume r"
        + "     LEFT JOIN bio_sections bs ON r.uuid=bs.resume_uuid"
        + "         LEFT JOIN sections s ON r.uuid=s.resume_uuid"
        + "             LEFT JOIN contacts c ON r.uuid=c.resume_uuid"
        + " WHERE r.uuid = COALESCE(?, r.uuid)"
    ;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SQLHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.executeSQLCommand("DELETE FROM resume;");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.<Void>executeSQLTransaction(con -> {
            String uuid = resume.getUuid();
            try (PreparedStatement ps = con.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?;")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() < 1) {
                    throw new NotExistStorageException(uuid);
                };
                sqlSaveContacts(con, resume);
                sqlSaveSections(con, resume);
                sqlSaveBioSection(con, Sections.EDUCATION, resume);
                sqlSaveBioSection(con, Sections.EXPERIENCE, resume);
                return null;
            }
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.<Void>executeSQLTransaction(con -> {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?);")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
                sqlSaveContacts(con, resume);
                sqlSaveSections(con, resume);
                sqlSaveBioSection(con, Sections.EDUCATION, resume);
                sqlSaveBioSection(con, Sections.EXPERIENCE, resume);
                return null;
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        ProcessQueryResult<Resume> pqr = (rs, uc) -> {
            if (rs.next()) {
                Resume resume = new Resume(rs.getString("uuid").trim(), rs.getString("full_name").trim());
                initContacts(resume, rs.getString("string_contacts"));
                initSections(resume, rs.getString("string_sections"));
                initBioSections(resume, rs.getString("string_bio_sections"));
                return resume;
            } else {
                throw new NotExistStorageException(uuid);
            }
        };
        return sqlHelper.<Resume>executeSQLCommand(pqr, complexRetrieveQuery, uuid, uuid, uuid, uuid);
    }

    @Override
    public void delete(String uuid) {
        ProcessQueryResult<Void> pqr = (rs, uc) -> {
            if (uc == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        };
        sqlHelper.executeSQLCommand(pqr, "DELETE FROM resume WHERE uuid=?;", uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        ProcessQueryResult<List<Resume>> pqr = (rs, uc) -> {
            List<Resume> listResume = new ArrayList<>();
            while (rs.next()) {
                Resume resume = new Resume(rs.getString("uuid").trim(), rs.getString("full_name").trim());
                initContacts(resume, rs.getString("string_contacts"));
                initSections(resume, rs.getString("string_sections"));
                initBioSections(resume, rs.getString("string_bio_sections"));
                listResume.add(resume);
            }
            return listResume;
        };
        return sqlHelper.<List<Resume>>executeSQLCommand(pqr, complexRetrieveQuery + " ORDER BY full_name, uuid", null, null, null, null);
    }

    @Override
    public int size() {
        ProcessQueryResult<Integer> pqr = (rs, uc) -> {
            rs.next();
            return rs.getInt(1);
        };
        return sqlHelper.executeSQLCommand(pqr, "SELECT COUNT(*) FROM resume;");
    }

    private void initContacts(Resume resume, String contactsString) {
        if (contactsString != null) {
            for (String contactString : contactsString.split("<s>")) {
                String[] contact = contactString.split("<e>");
                resume.setContact(Contacts.valueOf(contact[0]), contact[1]);
            }
        }
    }

    private void initSections(Resume resume, String sectionsString) {
        if (sectionsString != null) {
            for (String sectionString : sectionsString.split("<s>")) {
                String[] sectionParsed = sectionString.split("<h>");
                Sections section = Sections.valueOf(sectionParsed[0]);
                switch(section) {
                    case OBJECTIVE: case PERSONAL:
                        resume.setSection(section, new StringSection(sectionParsed[1]));
                        break;
                    case ACHIEVEMENTS: case QUALIFICATIONS:
                        resume.setSection(section, new StringListSection(sectionParsed[1].split("<e>")));
                        break;
                }
            }
        }
    }

    private String convertNulls(String string) {
        return string.equals("<null>") ? null : string;
    }

    private LocalDate toLocalDate(String string) {
        String[] date = string.split("-");
        return LocalDate.of(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2]));
    }

    private void initBioSections(Resume resume, String bioSectionsString) {
        if (bioSectionsString != null) {
            for (String bioSectionString : bioSectionsString.split("<bs>")) {
                String[] bioSectionParsed = bioSectionString.split("<bsh>");
                Sections section = Sections.valueOf(bioSectionParsed[0]);
                List<LifeStage> lsl = new ArrayList<>();
                for (String lifeStage : bioSectionParsed[1].split("<ls>")) {
                    String[] lifeStageParsed = lifeStage.split("<lsh>");
                    String[] organizationStrings = lifeStageParsed[0].split("<e>");
                    Organization organization = new Organization(organizationStrings[1], convertNulls(organizationStrings[2]), convertNulls(organizationStrings[3]));
                    List<LifePeriod> lpl = new ArrayList<>();
                    for (String lifePeriodString : lifeStageParsed[1].split("<lp>")) {
                        String[] lifePeriodStrings = lifePeriodString.split("<e>");
                        lpl.add(new LifePeriod(toLocalDate(lifePeriodStrings[0]), toLocalDate(lifePeriodStrings[1]), lifePeriodStrings[2], convertNulls(lifePeriodStrings[3])));
                    }
                    lsl.add(new LifeStage(organization, lpl));
                }
                resume.setSection(section, new BioSection(lsl));
            }
        }
    }

    private int sqlSaveOrganization(Connection con, Organization organization) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO organization (title, description, weblink) VALUES (?, ?, ?) ON CONFLICT (title) DO UPDATE SET description=EXCLUDED.description, weblink=EXCLUDED.weblink;", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, organization.getTitle());
            ps.setString(2, organization.getDescription());
            ps.setString(3, organization.getWebLink());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        }
    }

    private Organization sqlLoadOrganization(Connection con, int id) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM organization WHERE id=?;")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Organization(rs.getString("title"), rs.getString("description"), rs.getString("weblink"));
            } else {
                return null;
            }
        }
    }

    private void sqlSaveBioSection(Connection con, Sections section, Resume resume) throws SQLException {
        String uuid = resume.getUuid();
        String type = section.name();
        try (PreparedStatement ps = con.prepareStatement("DELETE FROM biography WHERE resume_uuid=? AND type=?;")) {
            ps.setString(1, uuid);
            ps.setString(2, type);
            ps.execute();
        }
        BioSection bioSection = (BioSection)resume.getSection(section);
        if (bioSection != null) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO biography (resume_uuid, type, organization_id, start_date, end_date, title, description) VALUES (?, ?, ?, ?, ?, ?, ?);")) {
                for (LifeStage ls : (List<LifeStage>) bioSection.getData()) {
                    int organization_id = sqlSaveOrganization(con, ls.getOrganization());
                    for (LifePeriod lp : ls.getData()) {
                        ps.setString(1, uuid);
                        ps.setString(2, type);
                        ps.setInt(3, organization_id);
                        ps.setDate(4, java.sql.Date.valueOf(lp.getStartDate()));
                        ps.setDate(5, java.sql.Date.valueOf(lp.getEndDate()));
                        ps.setString(6, lp.getTitle());
                        ps.setString(7, lp.getDescription());
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }
        }
    }

//    private BioSection sqllLoadBioSection(Connection con, Sections section, Resume resume) throws SQLException {
//        String uuid = resume.getUuid();
//        String type = section.name();
//        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM biography WHERE resume_uuid=? AND type=? ORDER BY start_date DESC;")) {
//            ps.setString(1, uuid);
//            ps.setString(2, type);
//            ResultSet rs = ps.executeQuery();
//            int organizationId;
//            int organizationIdPrev = -1;
//            while (rs.next()) {
//                List<LifePeriod> llp = new ArrayList<>();
//                organizationId = rs.getInt("organization_id");
//                Organization organization = sqlLoadOrganization(con, organizationId);
//                LifePeriod lp = new LifePeriod(rs.getDate("start_date").toLocalDate(), rs.getDate("end_date").toLocalDate(), rs.getString("title"), rs.getString("description"));
//                llp.add(lp);
//            }
//        }
//        return null;
//    }

    private boolean sqlLoadContacts(ResultSet rs, Resume resume) throws SQLException {
        if (rs.getString("type") != null) {
            boolean toContinue;
            String uuid = resume.getUuid();
            do {
                resume.setContact(Contacts.valueOf(rs.getString("type")), rs.getString("value"));
            } while ((toContinue = rs.next()) && rs.getString("uuid").trim().equals(uuid));
            return toContinue;
        } else {
            return rs.next();
        }
    }

    private void sqlSaveContacts(Connection con, Resume resume) throws SQLException {
        String uuid = resume.getUuid();
        try (PreparedStatement ps = con.prepareStatement("DELETE FROM contact WHERE resume_uuid=?;")) {
            ps.setString(1, uuid);
            ps.execute();
        }
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?);")) {
            for (Map.Entry<Contacts, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, uuid);
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private AbstractResumeSection stringToSection(Sections section, String string) {
        AbstractResumeSection resumeSection = null;
        switch (section) {
            case OBJECTIVE:
            case PERSONAL:
                resumeSection = new StringSection(string);
                break;
            case ACHIEVEMENTS:
            case QUALIFICATIONS:
                String[] strings = string.split("<e>");
                resumeSection = new StringListSection(strings);
                break;
        }
        return resumeSection;
    }

    private String sectionToString(Sections section, Resume resume) {
        String string = "";
        switch (section) {
            case OBJECTIVE:
            case PERSONAL:
                string = (String)resume.getSection(section).getData();
                break;
            case ACHIEVEMENTS:
            case QUALIFICATIONS:
                List<String> list = (List<String>)resume.getSection(section).getData();
                for (int i = 0; i < list.size(); i++) {
                    string += list.get(i) + "<e>";
                }
                break;
        }
        return string;
    }

    private void sqlSaveSections(Connection con, Resume resume) throws SQLException {
        String uuid = resume.getUuid();
        try (PreparedStatement ps = con.prepareStatement("DELETE FROM section WHERE resume_uuid=?;")) {
            ps.setString(1, uuid);
            ps.execute();
        }
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?, ?, ?);")) {
            for (Map.Entry<Sections, AbstractResumeSection> e : resume.getSections().entrySet()) {
                ps.setString(1, uuid);
                ps.setString(2, e.getKey().name());
                ps.setString(3, sectionToString(e.getKey(), resume));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

}
