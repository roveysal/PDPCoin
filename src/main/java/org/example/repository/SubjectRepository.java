package org.example.repository;

import org.example.jdbc.CustomDataSource;
import org.example.modul.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepository {
    private final String findAllSubjects = "SELECT * FROM subject";
    private final String findSubjectById = "SELECT * FROM subject WHERE id = ?";
    private final String findSubjectName= "SELECT DISTINCT(name) FROM subject";
    private final String updateSubjectById = "UPDATE subject SET name=?, grade = ? WHERE id = ?";
    private final String insertSubject = "INSERT INTO subject(name, grade) VALUES(?,?)";

    public List<Subject> findAllSubject(){
        List<Subject> subjectList = new ArrayList<>();
        try (
                Connection connection = CustomDataSource.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ){
            ResultSet resultSet = statement.executeQuery(findAllSubjects);
            while (resultSet.next()){
                Subject subject = new Subject();
                subject.setName(resultSet.getString("name"));
                subject.setGrade(resultSet.getInt("grade"));
                subjectList.add(subject);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subjectList;
    }
    public List<Subject> getSubjectsNames(){
        List<Subject> subjectList = new ArrayList<>();
        try (
                Connection connection = CustomDataSource.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ){
            ResultSet resultSet = statement.executeQuery(findSubjectName);
            while (resultSet.next()){
                Subject subject = new Subject();
                subject.setName(resultSet.getString("name"));
                subjectList.add(subject);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subjectList;
    }
    public Subject getSubjectById(long id){
        Subject subject = new Subject();
        try (
                Connection connection = CustomDataSource.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(findSubjectById, Statement.RETURN_GENERATED_KEYS)
                ){
            ps.setObject(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                subject.setName(resultSet.getString("name"));
                subject.setGrade(resultSet.getInt("grade"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subject;
    }
    public boolean createSubject(Subject subject){
        try (
                Connection connection = CustomDataSource.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(insertSubject, Statement.RETURN_GENERATED_KEYS)
                ){
            ps.setObject(1, subject.getName());
            ps.setObject(2, subject.getGrade());
            return ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean getUpdateById(Long id, Subject subject){
        try(
                Connection connection = CustomDataSource.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(updateSubjectById)
                ) {
            ps.setObject(1, subject.getName());
            ps.setObject(2, subject.getGrade());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
