package Domain;

import javafx.util.Pair;

/**
 * the domain for Nota
 */
public class Nota implements HasID<Pair<Integer,Integer>> {
    private float nota;
    private Pair<Integer,Integer> ID;
    private String cadruDidactic;

    /**
     * Constructor Nota
     * @param p - the id of the entity to be returned id must not be null
     * @param nota - the grade of the entity Nota
     * @param cadruDidactic - the teacher of the entity
     */
    public Nota(Pair<Integer,Integer> p, float nota, String cadruDidactic) {
        this.ID= p;
        this.nota = nota;
        this.cadruDidactic = cadruDidactic;
    }

    /**
     *
     * @return Nota as a string
     */
    @Override
    public String toString() {
        return "NOTA- " + "IDStud: " + ID.getKey() + " | IDTema: " + ID.getValue() + " | Nota: " + nota+" | Cadru: "+this.cadruDidactic;
    }

    /**
     *
     * @return return the grade of the entity
     */
    public float getNota() {
        return nota;
    }

    /**
     * Set param nota with another value
     * @param nota - the new grade of the entity
     */
    public void setNota(float nota) {
        this.nota = nota;
    }

    /**
     *
     * @return the ID of the entity
     */
    @Override
    public Pair<Integer, Integer> getID() {
        return ID;
    }

    /**
     * Set param ID with another value
     * @param ID - the new ID for the entity
     */
    @Override
    public void setID(Pair<Integer, Integer> ID) {
        this.ID = ID;
    }

    /**
     *
     * @return the teacher of the entity
     */
    public String getCadruDidactic() {
        return cadruDidactic;
    }

    /**
     * Set param teacher with another value
     * @param cadruDidactic - the new teacher
     */
    public void setCadruDidactic(String cadruDidactic) {
        this.cadruDidactic = cadruDidactic;
    }

    public Integer getSID(){return ID.getKey();}
    public Integer getTID(){return ID.getValue();}
}
