package GUI.View;

import Domain.*;
import event.ChangeEventType;
import event.ObjectEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Service.Service;
import Validator.ValidationException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Controller implements Observer<ObjectEvent<Student>> {
    private Service service;
    private ObservableList<Student> observableList;

    public Controller(Service service) {
        this.service = service;
        this.service.addObserverE(this);
        observableList = FXCollections.observableList(StreamSupport.stream(service.findAllS().spliterator(), false).collect(Collectors.toList()));
    }

    @Override
    public void update(ObjectEvent<Student> s) {
        if(s.getType() == ChangeEventType.ADD){
            observableList.add(s.getData());
        }
        if(s.getType() == ChangeEventType.UPDATE){
            observableList.remove(s.getOldData());
            observableList.add(s.getData());
        }
        if(s.getType() == ChangeEventType.DELETE){
            observableList.remove(s.getData());
        }
    }

    public ObservableList getList(){
        return observableList;
    }

    public void addStudent(int id, String nume,int grupa, String email) throws ValidationException {
        Student s = new Student(id, nume, grupa, email);
        service.saveS(s);
    }

    public void updateStudent(int id, String nume,int grupa, String email) throws ValidationException {
        Student s = new Student(id, nume, grupa, email);
        service.updateS(s);
    }

    public void deleteStudent(int id) throws ValidationException {
        service.deleteS(id);
    }
}
