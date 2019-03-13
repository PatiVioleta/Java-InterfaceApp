package Domain;

/**
 * the domain for Tema
 */
public class Tema implements HasID<Integer> {
    private int ID;
    private String descriere;
    private int deadline;
    private int primire;

    /**
     * Constructor
     */
    public Tema(){
        this.ID = 0;
        this.descriere ="";
        this.deadline = 0;
        this.primire = 0;
    }

    /**
     * Constructor with params
     * @param ID - the ID of the entity
     * @param descriere - the description of the entity
     * @param deadline - the deadline for the Lab
     * @param primire - the week in witch the Lab gets started
     */
    public Tema(int ID, String descriere, int deadline, int primire) {
        this.ID = ID;
        this.descriere = descriere;
        this.deadline = deadline;
        this.primire = primire;
    }

    /**
     *
     * @return the ID of the entity
     */
    @Override
    public Integer getID() {
        return ID;
    }

    /**
     * Set the ID with another value
     * @param ID - the new ID for the entity
     */
    @Override
    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     *
     * @return the description of the entity
     */
    public String getDescriere() {
        return descriere;
    }

    /**
     * Set the description of the entity with another one
     * @param descriere - the new description fot the entity
     */
    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    /**
     *
     * @return the deadline of the entity
     */
    public int getDeadline() {
        return deadline;
    }

    /**
     * Set a new deadline for the entity
     * @param deadline - the new deadline for the entity
     */
    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    /**
     *
     * @return the week in witch the Lab gets started
     */
    public int getPrimire() {
        return primire;
    }

    /**
     * Set the week in witch the Lab gets started with another value
     * @param primire - the new week in witch the Lab gets started
     */
    public void setPrimire(int primire) {
        this.primire = primire;
    }

    /**
     *
     * @return the entity Tema as a string
     */
    @Override
    public String toString(){
        return "TEMA- ID: "+this.ID+" | Descriere: "+this.descriere+" | Deadline: "+this.deadline+" | Primire: "+this.primire;
    }
}
