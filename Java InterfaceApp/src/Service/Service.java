package Service;

import Domain.*;
import Repo.CrudRepository;
import Validator.ServiceException;
import Validator.ValidationException;
import event.ChangeEventType;
import GUI.View.Observable;
import GUI.View.Observer;
import event.ObjectEvent;
import javafx.collections.FXCollections;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * The service for the entity Tema
 */
public class Service implements Observable<ObjectEvent<Student>,ObjectEvent<Tema>,ObjectEvent<Nota>>{
    public static int saptCurenta=0;

    private CrudRepository<Integer,Tema> repoT;
    private CrudRepository<Integer, Student> repoS;
    private CrudRepository<Pair<Integer,Integer>, Nota> repoN;

    private static ArrayList<Observer<ObjectEvent<Student>>> observersS;
    private static ArrayList<Observer<ObjectEvent<Tema>>> observersT;
    private static ArrayList<Observer<ObjectEvent<Nota>>> observersN;

    /**
     * Constructor
     * @param rt - the repository for Tema
     * @param rs - the repository for Dtudent
     */
    public Service(CrudRepository rs, CrudRepository rt, CrudRepository rn){
        this.repoT=rt;
        this.repoS=rs;
        this.repoN=rn;
        this.observersS = new ArrayList<>();
        this.observersT = new ArrayList<>();
        this.observersN = new ArrayList<>();
    }

    @Override
    public void addObserverE(Observer<ObjectEvent<Student>> e) {
        observersS.add(e);
    }

    @Override
    public void removeObserverE(Observer<ObjectEvent<Student>> e) {
        observersS.remove(e);
    }

    @Override
    public void notifyObserversE(ObjectEvent<Student> s) {
        observersS.forEach(obs -> obs.update(s));
    }

    @Override
    public void addObserverF(Observer<ObjectEvent<Tema>> e) {
        observersT.add(e);
    }

    @Override
    public void removeObserverF(Observer<ObjectEvent<Tema>> e) {
        observersT.remove(e);
    }

    @Override
    public void notifyObserversF(ObjectEvent<Tema> s) {
        observersT.forEach(obs -> obs.update(s));
    }

    @Override
    public void addObserverG(Observer<ObjectEvent<Nota>> e) {
        observersN.add(e);
    }

    @Override
    public void removeObserverG(Observer<ObjectEvent<Nota>> e) {
        observersN.remove(e);
    }

    @Override
    public void notifyObserversG(ObjectEvent<Nota> s) {
        observersN.forEach(obs -> obs.update(s));
    }



    /**
     * modify the value of the week
     * @param sapt- the curent week
     */
    public void setareSaptamana(int sapt){
        saptCurenta=sapt;
    }
    public static int getSaptamana(){return saptCurenta;}


    /**
     * Find an entity by ID
     * @param id - the ID of the entity student
     * @return - the entity with the ID=id
     */
    public Student findOneS(Integer id){
        return repoS.findOne(id);
    }

    /**
     * Find all the entities in the repo
     * @return all the entities student from repo
     */
    public Iterable<Student> findAllS(){
        return repoS.findAll();
    }

    /**
     * add a student in the list
     * @param t the new student
     */
    public void saveS(Student t){
        repoS.save(t);
        notifyObserversE(new ObjectEvent(null, t, ChangeEventType.ADD));
    }

    /**
     * Delete a student
     * @param id - the id of the student to be deleted
     * @return the deleted element
     */
    public Student deleteS(Integer id){

        ArrayList<Nota> toate = new ArrayList<>();
        for( Nota n : findAllN())
            toate.add(n);

        for(int i=0;i< toate.size();i++) {
            Nota aux = toate.get(i);
            if (aux.getSID() == id) {
                deleteN(aux.getID());
            }
        }

        Student s = repoS.delete(id);
        notifyObserversE(new ObjectEvent(repoS.findOne(id), s, ChangeEventType.DELETE));

        return s;
    }

    /**
     * Update a student
     * @param t the new student
     * @return the updated student
     */
    public Student updateS(Student t){
        notifyObserversE(new ObjectEvent(repoS.findOne(t.getID()), t, ChangeEventType.UPDATE));
        Student s=repoS.update(t);
        return s;
    }

    public Iterable<String> findAllSString(){
        ArrayList<String> rez = new ArrayList<>();
        Iterable<Student> list =repoS.findAll();
        list.forEach((x)->{rez.add(x.getNume());});
        return rez;
    }

    public Iterable<String> findAllSGrupa(){
        Set<String> rez = new HashSet<>();
        Iterable<Student> list =repoS.findAll();
        list.forEach((x)->{
            rez.add(x.getGrupa().toString());
        });

        return rez;
    }

    public Student findOneSNume(String nume){
        for(Student s : findAllS())
            if(s.getNume().equals(nume))
                return s;
            throw new ServiceException("Nu exista studentul cu numele acesta!");
    }

    /**
     * Find an entity by ID
     * @param id - the ID of the entity tema
     * @return - the entity with the ID=id
     */
    public Tema findOneT(Integer id){
        return repoT.findOne(id);
    }

    public Tema findOneTPredare(){
        for(Tema x : findAllT())
            if(x.getDeadline()==saptCurenta)
                return x;
        throw new ServiceException("Nu este nimic de predat!");
    }

    /**
     * Find all the entities in the repo
     * @return all the entities tema from repo
     */
    public Iterable<Tema> findAllT(){
        return repoT.findAll();
    }

    public Iterable<String> findAllTString(){
        ArrayList<String> rez = new ArrayList<>();
        Iterable<Tema> list =repoT.findAll();
        list.forEach((x)->{rez.add(x.getID()+" | Descriere: "+x.getDescriere()+" | Primire: "+x.getPrimire()+" | Predare: "+x.getDeadline());});
        return rez;
    }

    /**
     * Save a new entity in the repo
     * @param t - the new entity Tema
     */
    public void saveT(Tema t){
        repoT.save(t);
        notifyObserversF(new ObjectEvent(null, t, ChangeEventType.ADD));
    }

    /**
     * Extends deadline
     * @param id - the entity we change the deadline
     * @param nrSaptamani - the number of weeks to be added at the deadline
     * @throws ServiceException if the deadline can't be changed
     */
    public void prelungireTermen(Integer id, int nrSaptamani){
        Tema t = findOneT(id);
        if(saptCurenta<=t.getDeadline() && t.getDeadline()+nrSaptamani<=14 && t.getDeadline()+nrSaptamani>=1){
            Tema tt=new Tema(t.getID(),t.getDescriere(),t.getDeadline()+nrSaptamani,t.getPrimire());
            repoT.update(tt);
        }
        else throw new ServiceException("Nu se poate modifica deadline-ul!\n");
    }




    /**
     * Verify if exists an entity student with idS and an entity tema with idT
     * @param idS id Student
     * @param idT id Tema
     */
    public void existentaST(int idS, int idT){
        String err=new String();
        if(repoS.findOne(idS)==null)
            err=err+"Studentul este inexistent!\n";
        if(repoT.findOne(idT)==null)
            err=err+"Tema este inexistenta!\n";
        if(err.length()!=0)
            throw new ValidationException(err);
    }

    /**
     * Save a new entity nota
     * @param feed the feedback
     * @return the value of the final grade
     */
    public float saveN(String idS, String idT, String nota, String numeC, String feed, boolean motivat){
        Pair<Integer,Integer> id=new Pair(Integer.parseInt(idS),Integer.parseInt(idT));
        Nota n =new Nota(id,Float.parseFloat(nota),numeC);
        try {
            float posib = aplicaIntarzieriN(Integer.parseInt(idS), Integer.parseInt(idT), n.getNota());
            if(!motivat)
                n.setNota(posib);
        }
        catch (ServiceException err){
            n.setNota(1);
        }

        repoN.save(n);
        String num = repoS.findOne(n.getID().getKey()).getNume();
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("src/fisiere/" + num,true));
            br.append("Tema: "+n.getID().getValue().toString()); br.newLine();
            br.append("Nota: "+n.getNota()); br.newLine();
            br.append("Predata in saptamana: "+saptCurenta); br.newLine();
            br.append("Deadline: "+repoT.findOne(n.getID().getValue()).getDeadline()); br.newLine();
            br.append("Feedback: "+feed); br.newLine();
            br.newLine();
            br.close();
        }
        catch (IOException err){
            throw new ServiceException("Eroare la trecere nota in fisierul studentului!");
        }

        notifyObserversG(new ObjectEvent(null, n, ChangeEventType.ADD));
        return n.getNota();
    }

    /**
     * calculeaza nota maxima pe care o poate lua cineva la o anumita tema
     * @param idT id-ul temei
     * @return npya maxima
     */
    public float aplicaIntarzieriN(int idS,int idT,float nInitial){
        existentaST(idS,idT);
        Tema t= repoT.findOne(idT);
        float nota=nInitial;
        if(t.getDeadline()+2<saptCurenta) {
            throw new ServiceException("Nu se mai poate preda aceasta tema!\n");
        }
        if(t.getDeadline()+2==saptCurenta)
            nota-=5;
        if(t.getDeadline()+1==saptCurenta)
            nota-=2.5;
        if(nota<=0)
            nota=1;
        return nota;
    }

    public double puncteMinus(int idT){
        Tema t= repoT.findOne(idT);
        double puncte =0;
        if(t.getDeadline()+2<saptCurenta) {
            throw new ServiceException("Nu se mai poate preda aceasta tema!\n");
        }
        if(t.getDeadline()+2==saptCurenta)
            puncte=5;
        if(t.getDeadline()+1==saptCurenta)
            puncte=2.5;
        return puncte;
    }

    public Nota deleteN(Pair<Integer,Integer> id){
        Nota n = repoN.delete(id);
        notifyObserversG(new ObjectEvent(repoN.findOne(id), n, ChangeEventType.DELETE));
        return n;
    }

    /**
     * Find all the entities in the repo
     * @return all the entities student from repo
     */
    public Iterable<Nota> findAllN(){
        return repoN.findAll();
    }

    public Nota findOneN(Pair<Integer,Integer> id){
                return repoN.findOne(id);
    }

    public List<Nota> filtru1(Integer idT){
        List<Nota> rez = new ArrayList<>();
        Iterable<Nota> toate = findAllN();
        toate.forEach((x)->{
            if(x.getTID()==idT)
                rez.add(x);
        });
        return rez;
    }

    public List<Nota> filtru2(Integer idS){
        List<Nota> rez = new ArrayList<>();
        Iterable<Nota> toate = findAllN();
        toate.forEach((x)->{
            if(x.getSID()==idS)
                rez.add(x);
        });
        return rez;
    }

    public List<Nota> filtru3(Integer idT, int grupa){
        List<Nota> rez = new ArrayList<>();
        Iterable<Nota> toate = findAllN();
        toate.forEach((x)->{
            if(x.getTID()==idT && findOneS(x.getSID()).getGrupa()==grupa)
                rez.add(x);
        });
        return rez;
    }

    public List<Student> filtru4Stud(int grupa){
        List<Student> rez = new ArrayList<>();
        Iterable<Student> toate = findAllS();
        toate.forEach((x)->{
            if(x.getGrupa()==grupa)
                rez.add(x);
        });
        return rez;
    }

    public List<Tema> filtru4Teme(){
        List<Tema> rez = new ArrayList<>();
        Iterable<Tema> toate = findAllT();
        toate.forEach((x)->{
                rez.add(x);
        });
        return rez;
    }

    public List<String> findNoteStudent(Integer idS){
        List<String> rez = new ArrayList<>();
        for(int i=0;i<14;i++){
            try {
                Pair<Integer, Integer> id = new Pair<>(idS, i);
                Nota n = findOneN(id);
                rez.add(String.valueOf(n.getNota()));
            }catch (RuntimeException e){
                rez.add("-");
            }
        }
        return rez;
    }

    public List<NotaDTO> filtru4(List<Student> studenti){
        List<NotaDTO> rez = new ArrayList<>();
        for(int i=0;i<studenti.size();i++){
            String nume = studenti.get(i).getNume();
            List<String> listaNote = findNoteStudent(studenti.get(i).getID());
            NotaDTO aux = new NotaDTO(nume,listaNote);
            rez.add(aux);
        }

        return rez;
    }

}
