package Repo;

import Domain.Student;
import Validator.FileException;
import Validator.Validator;

import java.io.*;


public class StudentFileRepository extends AbstractFileRepository<Integer, Student> {

    public StudentFileRepository(Validator v, String file){
        super(v,file);
    }

    @Override
    public void loadFromFile(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine())!=null){
                String[] elem= line.split("#");
                //System.out.println(line);
                Student s = new Student(Integer.parseInt(elem[0]),elem[1],Integer.parseInt(elem[2]),elem[3]);
                save(s);
            }

            br.close();
        }catch(FileNotFoundException err) {
            throw new FileException("Eroare la creare buffer");
        }catch(IOException err){
            throw new FileException("Eroare la citire din fisier");
        }
    }

    @Override
    public void storeToFile() {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            //for(Student s : entities.values()) {
            //    bw.write(s.getID() + "#" + s.getNume() + "#" + s.getGrupa() + "#" + s.getEmail());
            //    bw.newLine();
            //}

            entities.values().forEach((Student s)->{
                try {
                    bw.write(s.getID() + "#" + s.getNume() + "#" + s.getGrupa() + "#" + s.getEmail());
                    bw.newLine();
                } catch(IOException err){ throw new FileException("Eroare la citire din fisier"); }
            });

            bw.close();
        }catch(IOException err){
            throw new FileException("Eroare la citire din fisier");
        }
    }

}
