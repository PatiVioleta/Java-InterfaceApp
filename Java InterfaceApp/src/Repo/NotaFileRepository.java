package Repo;

import Domain.Nota;
import Validator.FileException;
import Validator.Validator;
import javafx.util.Pair;

import java.io.*;

/**
 *
 */
public class NotaFileRepository extends AbstractFileRepository<Pair<Integer,Integer>, Nota> {

    public NotaFileRepository(Validator v, String file){
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
                Nota n = new Nota(new Pair(Integer.parseInt(elem[0]),Integer.parseInt(elem[1])),Float.parseFloat(elem[2]),elem[3]);
                save(n);
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
            //for(Nota n : entities.values()) {
            //    bw.write(n.getID().getKey() + "#" + n.getID().getValue() + "#" + n.getNota()+ "#" + n.getCadruDidactic());
            //    bw.newLine();
            //}

            entities.values().forEach((Nota n)->{
                try {
                    bw.write(n.getID().getKey() + "#" + n.getID().getValue() + "#" + n.getNota() + "#" + n.getCadruDidactic());
                    bw.newLine();
                } catch(IOException err){ throw new FileException("Eroare la citire din fisier"); }
            });

            bw.close();
        }catch(IOException err){
            throw new FileException("Eroare la citire din fisier");
        }
    }

}
