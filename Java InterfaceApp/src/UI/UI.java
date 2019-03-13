package UI;

import Domain.Student;
import Domain.Tema;
import Service.Service;
import Validator.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UI {
    private BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
    private Service serv;

    public UI(Service serv) {
        this.serv=serv;
    }


    public void menu(){
        System.out.println("\nAlegeti:");
        System.out.println("0: Exit");
        System.out.println("1: Operatii student");
        System.out.println("2: Operatii tema");
        System.out.println("3: Operatii nota");
        System.out.println("Saptamana curenta: "+serv.getSaptamana());
        System.out.println();
    }

    public void menuStudent(){
        System.out.println("\n0: Inapoi");
        System.out.println("p: Print studenti");
        System.out.println("1: Adauga student");
        System.out.println("2: Modifica student");
        System.out.println("3: Sterge student");
        System.out.println("Saptamana curenta: "+serv.getSaptamana());
        System.out.println();
    }

    public void menuTema(){
        System.out.println("\n0: Inapoi");
        System.out.println("p: Print teme");
        System.out.println("1: Adauga tema");
        System.out.println("2: Modifica deadline");
        System.out.println("Saptamana curenta: "+serv.getSaptamana());
        System.out.println();
    }

    public void menuNota(){
        System.out.println("\n0: Inapoi");
        System.out.println("p: Print note");
        System.out.println("1: Adauga nota");
        System.out.println("Saptamana curenta: "+serv.getSaptamana());
        System.out.println();
    }

    public void adaugaStudent(){
        try {
            System.out.print("Dati Id: ");
            String id = cin.readLine();
            System.out.print("Dati nume: ");
            String nume=cin.readLine();
            System.out.print("Dati grupa: ");
            String grupa=cin.readLine();
            System.out.print("Dati email: ");
            String email=cin.readLine();

            serv.saveS(new Student(Integer.parseInt(id),nume,Integer.parseInt(grupa),email));
            System.out.println("Student adaugat cu succes!");
        }catch (IOException err){
            System.out.println("Eroare la citire consola!");
        }catch (RuntimeException err){
            System.out.println(err.toString());
        }
    }

    public void modificaStudent(){
        try {
            System.out.print("Dati Id: ");
            String id = cin.readLine();
            System.out.print("Dati noul nume: ");
            String nume=cin.readLine();
            System.out.print("Dati noua grupa: ");
            String grupa=cin.readLine();
            System.out.print("Dati noul email: ");
            String email=cin.readLine();
            serv.updateS(new Student(Integer.parseInt(id),nume,Integer.parseInt(grupa),email));
            System.out.println("Student modificat cu succes!");
        }catch (IOException err){
            System.out.println("Eroare la citire consola!");
        }catch (RuntimeException err){
            System.out.println(err.toString());
        }
    }

    public void stergeStudent(){
        try {
            System.out.print("Dati Id pentru stergere: ");
            String id = cin.readLine();
            serv.deleteS(Integer.parseInt(id));
            System.out.println("Student sters cu succes!");
        }catch (IOException err){
            System.out.println("Eroare la citire consola!");
        }catch (RuntimeException err){
            System.out.println(err.toString());
        }
    }

    public void printStudenti(){
        serv.findAllS().forEach(System.out::println);
    }

    public void adaugaTema(){
        try {
            System.out.print("Dati Id: ");
            String id = cin.readLine();
            System.out.print("Dati descriere: ");
            String desc=cin.readLine();
            System.out.print("Dati deadline: ");
            String deadline=cin.readLine();
            System.out.print("Dati saptamana primire: ");
            String primire=cin.readLine();

            serv.saveT(new Tema(Integer.parseInt(id),desc,Integer.parseInt(deadline),Integer.parseInt(primire)));
            System.out.println("Tema adaugata cu succes!");
        }catch (IOException err){
            System.out.println("Eroare la citire consola!");
        }catch (RuntimeException err){
            System.out.println(err.toString());
        }
    }

    public void modificaDeadlineTema(){
        try {
            System.out.print("Dati Id: ");
            String id = cin.readLine();

            System.out.print("Dati nr de saptamani adaugate la deadline: ");
            String nr=cin.readLine();

            serv.prelungireTermen(Integer.parseInt(id),Integer.parseInt(nr));
            System.out.println("Deadline modificat!");
        }catch (IOException err){
            System.out.println("Eroare la citire consola!");
        }catch (RuntimeException err){
            System.out.println(err.toString());
        }
    }

    public void printTeme(){
        serv.findAllT().forEach(System.out::println);
    }

    public void adaugaNota(){
        try {
            System.out.print("Dati Id student: ");
            String idS = cin.readLine();
            System.out.print("Dati Id tema: ");
            String idT=cin.readLine();
            Float notaMax=10f;

            try{
                notaMax =serv.aplicaIntarzieriN(Integer.parseInt(idS),Integer.parseInt(idT),10);
                System.out.print("Nota maxima posibila este: "+notaMax);

                System.out.print("\nDati nota: ");
                String nota=cin.readLine();
                System.out.print("Dati nume cadru didactic: ");
                String numeC=cin.readLine();
                System.out.print("Dati un feedback: ");
                String feed=cin.readLine();

                System.out.println("S-a adaugata cu succes nota "+serv.saveN(idS,idT,nota,numeC,feed,false)+" !");
            }
            catch (ServiceException err){
                System.out.println("Termenul limita este depasit! Se va trece nota 1!");
                System.out.print("Dati nume cadru didactic: ");
                String numeC=cin.readLine();
                System.out.println("S-a adaugata cu succes nota "+serv.saveN(idS,idT,"1",numeC,"S-a depasit termenul de predare!",false)+" !");
            }

        }catch (IOException err){
            System.out.println("Eroare la citire consola!");
        }catch (RuntimeException err){
            System.out.print(err.toString());
        }
    }

    public void printNote(){
        serv.findAllN().forEach(System.out::println);
    }

    public void run(){
        try{
            System.out.print("Introduceti saptamana curenta: ");
            serv.setareSaptamana(Integer.parseInt(cin.readLine()));
        }
        catch( IOException e){

        }
        try{
            while(true) {
                menu();
                String o = cin.readLine();

                switch (o) {
                    case "0":
                        return;
                    case "1":
                        menuStudent();
                        String oo= cin.readLine();

                        switch (oo){
                            case "0":
                                break;
                            case "1":
                                adaugaStudent();
                                break;
                            case "2":
                                modificaStudent();
                                break;
                            case "3":
                                stergeStudent();
                                break;
                            case "p":
                                printStudenti();
                                break;
                            default:
                                System.out.println("Alegere invalida!");
                                break;
                        }
                        break;
                    case "2":
                        menuTema();
                        String ooo= cin.readLine();

                        switch (ooo){
                            case "0":
                                break;
                            case "1":
                                adaugaTema();
                                break;
                            case "2":
                                modificaDeadlineTema();
                                break;
                            case "p":
                                printTeme();
                                break;
                            default:
                                System.out.println("Alegere invalida!");
                                break;
                        }
                        break;

                    case "3":
                        menuNota();
                        String oooo= cin.readLine();

                        switch (oooo){
                            case "0":
                                break;
                            case "1":
                                adaugaNota();
                                break;
                            case "p":
                                printNote();
                                break;
                            default:
                                System.out.println("Alegere invalida!");
                                break;
                        }

                        break;

                    default:
                        System.out.println("Alegere invalida!");
                        break;
                }
            }
        }catch (IOException err){
            System.out.println("Valoare invalida!");
        }catch (NumberFormatException err) {
            System.out.println("Valoare invalida!");
        }
    }
}
