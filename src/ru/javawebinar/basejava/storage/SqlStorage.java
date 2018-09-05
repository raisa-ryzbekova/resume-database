package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver doesn't exist: " + e);
        }
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.executeTransaction(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO resume(uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(connection, resume);
            insertSections(connection, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeTransaction(connection -> {
            Resume resume;
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
            }
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    setContact(resultSet, resume);
                }
            }
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    setSection(resultSet, resume);
                }
            }
            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeTransaction(connection -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    resumes.put(resultSet.getString("uuid"), (new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"))));
                }
            }
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact")) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    Resume resume = resumes.get(resultSet.getString("resume_uuid"));
                    setContact(resultSet, resume);
                }
            }
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section")) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    Resume resume = resumes.get(resultSet.getString("resume_uuid"));
                    setSection(resultSet, resume);
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.executeTransaction(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE resume SET full_name =? WHERE uuid =?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteAttributes(connection, resume, "DELETE FROM contact WHERE resume_uuid=?");
            insertContacts(connection, resume);
            deleteAttributes(connection, resume, "DELETE FROM section WHERE resume_uuid=?");
            insertSections(connection, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeQuery("DELETE FROM resume WHERE uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void clear() {
        sqlHelper.executeQuery("DELETE FROM resume");
    }

    @Override
    public int size() {
        return sqlHelper.executeQuery("SELECT count(*) FROM resume", ps -> {
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    private void setContact(ResultSet resultSet, Resume resume) throws SQLException {
        resume.setContact(ContactType.valueOf(resultSet.getString("contact_type")), resultSet.getString("contact_value"));
    }

    private void insertContacts(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, contact_type, contact_value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> contacts : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, contacts.getKey().name());
                ps.setString(3, contacts.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void setSection(ResultSet resultSet, Resume resume) throws SQLException {
        String sectionContent = resultSet.getString("section_value");
        resume.setSection(SectionType.valueOf(resultSet.getString("section_type")), JsonParser.read(sectionContent, Section.class));
    }

    private void insertSections(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO section (resume_uuid, section_type, section_value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> sections : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, sections.getKey().name());
                Section section = sections.getValue();
                ps.setString(3, JsonParser.write(section, Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteAttributes(Connection connection, Resume resume, String request) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(request)) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }
}