package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.Month;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void toWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeCollection(dos, contacts.entrySet(), (elementCollection) -> {
                dos.writeUTF(elementCollection.getKey().name());
                dos.writeUTF(elementCollection.getValue());
            });
            Map<SectionType, Section> sections = resume.getSections();
            writeCollection(dos, sections.entrySet(), (elementCollection) -> {
                dos.writeUTF(elementCollection.getKey().name());
                SectionType sectionType = elementCollection.getKey();
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) elementCollection.getValue()).getSectionContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> listSectionContent = ((ListSection) elementCollection.getValue()).getSectionContent();
                        writeCollection(dos, listSectionContent, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Company> companies = ((CompanySection) elementCollection.getValue()).getSectionContent();
                        writeCollection(dos, companies, (elementCollection1) -> {
                            dos.writeUTF(elementCollection1.getCompanyName());
                            if (elementCollection1.getUrl() == null) {
                                dos.writeUTF("");
                            } else {
                                dos.writeUTF(elementCollection1.getUrl());
                            }
                            List<Company.PositionInCompany> positionList = elementCollection1.getListOfPositions();
                            writeCollection(dos, positionList, (elementInInScope) -> {
                                dos.writeInt(elementInInScope.getStartDate().getYear());
                                dos.writeInt(elementInInScope.getStartDate().getMonth().getValue());
                                if (elementInInScope.getEndDate() == null) {
                                    dos.writeInt(3000);
                                    dos.writeInt(1);
                                } else {
                                    dos.writeInt(elementInInScope.getEndDate().getYear());
                                    dos.writeInt(elementInInScope.getEndDate().getMonth().getValue());
                                }
                                dos.writeUTF(elementInInScope.getPosition());
                                if (elementInInScope.getFunctions() == null) {
                                    dos.writeUTF("");
                                } else {
                                    dos.writeUTF(elementInInScope.getFunctions());
                                }
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume toRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readData(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readData(dis, () -> {
                String sectionType = dis.readUTF();
                switch (sectionType) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        resume.setSection(SectionType.valueOf(sectionType), new TextSection(dis.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        resume.setSection(SectionType.valueOf(sectionType), new ListSection(readCollection(dis, dis::readUTF)));
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        resume.setSection(SectionType.valueOf(sectionType), new CompanySection(
                                readCollection(dis, () -> new Company(
                                        new Link(dis.readUTF(), dis.readUTF()),
                                        readCollection(dis, () -> new Company.PositionInCompany(dis.readInt(), Month.of(dis.readInt()),
                                                dis.readInt(), Month.of(dis.readInt()), dis.readUTF(), dis.readUTF()))))));
                        break;
                }
            });
            return resume;
        }
    }

    @FunctionalInterface
    private interface WriteCollection<T> {
        void write(T t) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, WriteCollection<T> writeFunctionalInterface) throws IOException {
        dos.writeInt(collection.size());
        for (T collectionElement : collection) {
            writeFunctionalInterface.write(collectionElement);
        }
    }

    @FunctionalInterface
    private interface ReadData {
        void read() throws IOException;
    }

    private void readData(DataInputStream dis, ReadData readFunctionalInterface) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            readFunctionalInterface.read();
        }
    }

    @FunctionalInterface
    private interface ReadCollection<T> {
        T read() throws IOException;
    }

    private <T> List<T> readCollection(DataInputStream dis, ReadCollection<T> readFunctionalInterface) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int point = 0; point < size; point++) {
            list.add(readFunctionalInterface.read());
        }
        return list;
    }
}
