package Domain;

/**
 * the domain for Student
 */
public class Student implements HasID<Integer>{
    private int ID;
    private String nume;
    private int grupa;
    private String email;

    /**
     * Constructor
     */
    public Student(){
        this.ID=0;
        this.nume="";
        this.grupa=0;
        this.email="";
    }

    /**
     * Constructor with params
     * @param ID - the ID of the entity
     * @param nume - the name of the entity
     * @param grupa - the group of the entity
     * @param email - the email of the entity
     */
    public Student(int ID, String nume, int grupa, String email) {
        this.ID = ID;
        this.nume = nume;
        this.grupa = grupa;
        this.email = email;
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
     *
     * @return the name of the entity
     */
    public String getNume() {
        return nume;
    }

    /**
     *
     * @return the group of the entity
     */
    public Integer getGrupa() {
        return grupa;
    }

    /**
     *
     * @return the email of the entity
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set param ID with another value
     * @param ID - the new ID for the entity
     */
    @Override
    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     * Set param name with another value
     * @param nume - the new name of the entity
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Set param group with another value
     * @param grupa - the new group of the entity
     */
    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    /**
     * Set param email with another value
     * @param email - the new email for the entity
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return the entity as a string
     */
    @Override
    public String toString(){
        return "STUDENT- ID: "+this.ID+" | Nume: "+this.nume+" | Grupa: "+this.grupa+" | Email: "+this.email;
    }

}
