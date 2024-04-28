package org.example.service;

import org.example.modul.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> getAllSubjects();
    Subject getSubjectById(long id);
    boolean createSubject(Subject subject);
    boolean updateSubjectById(Long id, Subject subject);
}