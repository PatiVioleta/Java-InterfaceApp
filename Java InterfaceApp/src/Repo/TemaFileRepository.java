package Repo;

import Domain.Tema;
import Validator.FileException;
import Validator.Validator;

import java.io.*;


public class TemaFileRepository extends AbstractFileRepository<Integer, Tema> {

    public TemaFileRepository(Validator v, String file){
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
                Tema t = new Tema(Integer.parseInt(elem[0]),elem[1],Integer.parseInt(elem[2]),Integer.parseInt(elem[3]));
                save(t);
            }
            br.close();
        }catch(FileNotFoundException err) {
            throw new FileException("Eroare la creare buffer");
        }catch(IOException err){
            throw new FileException("Eroare la citire din fisier");
        }
    }

    @Override
    public void storeToFile(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            //for(Tema t : entities.values()) {
            //    bw.write(t.getID() + "#" + t.getDescriere() + "#" + t.getDeadline() + "#" + t.getPrimire());
            //    bw.newLine();
            //}

            entities.values().forEach((Tema t)->{
                try {
                    bw.write(t.getID() + "#" + t.getDescriere() + "#" + t.getDeadline() + "#" + t.getPrimire());
                    bw.newLine();
                } catch(IOException err){ throw new FileException("Eroare la citire din fisier"); }
            });

            bw.close();
        }catch(IOException err){
            throw new FileException("Eroare la citire din fisier");
        }
    }
}
