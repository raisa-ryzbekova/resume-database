package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void toWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeCollection(dos, resume.getContacts().entrySet(), contact -> {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            });
            writeCollection(dos, resume.getSections().entrySet(), section -> {
                SectionType sectionType = section.getKey();
                Section sectionElement = section.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) sectionElement).getSectionContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((ListSection) sectionElement).getSectionContent(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((CompanySection) sectionElement).getSectionContent(), company -> {
                            dos.writeUTF(company.getCompanyName());
                            dos.writeUTF(company.getUrl());
                            writeCollection(dos, company.getListOfPositions(), position -> {
                                writeLocalDate(dos, position.getStartDate());
                                if (position.getEndDate() == null) {
                                    dos.writeInt(3000);
                                    dos.writeInt(1);
                                } else {
                                    dos.writeInt(position.getEndDate().getYear());
                                    dos.writeInt(position.getEndDate().getMonth().getValue());
                                }
                                dos.writeUTF(position.getPosition());
                                dos.writeUTF(position.getFunctions());
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
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.setSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readCollection(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new CompanySection(
                        readCollection(dis, () -> new Company(
                                new Link(dis.readUTF(), dis.readUTF()),
                                readCollection(dis, () -> new Company.PositionInCompany(dis.readInt(), Month.of(dis.readInt()),
                                        dis.readInt(), Month.of(dis.readInt()), dis.readUTF(), dis.readUTF()
                                ))
                        )));
            default:
                throw new IllegalStateException();
        }
    }

    @FunctionalInterface
    private interface WriteCollectionElement<T> {
        void write(T t) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, WriteCollectionElement<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T collectionElement : collection) {
            writer.write(collectionElement);
        }
    }

    @FunctionalInterface
    private interface ReadCollectionElement<T> {
        T read() throws IOException;
    }

    private <T> List<T> readCollection(DataInputStream dis, ReadCollectionElement<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    @FunctionalInterface
    private interface ReadData {
        void read() throws IOException;

    }

    private void readData(DataInputStream dis, ReadData reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
    }
}
