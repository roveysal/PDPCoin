package org.example.service;

import org.example.modul.Subject;
import org.example.repository.SubjectRepository;
import org.example.service.SubjectService ;

import java.util.List;

public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAllSubject();
    }

    @Override
    public Subject getSubjectById(long id) {
        return subjectRepository.getSubjectById(id);
    }

    @Override
    public boolean createSubject(Subject subject) {
        return subjectRepository.createSubject(subject);
    }

    @Override
    public boolean updateSubjectById(Long id, Subject subject) {
        return subjectRepository.getUpdateById(id, subject);
    }
}