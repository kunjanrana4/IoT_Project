package genuine.raj.example.com.project_17s1;

public class Patients {
    public String id,name,weight,height,contact,gender,dob;
    public Patients()
    {
    }

    public Patients(String id,String name, String weight, String height, String contact, String gender,String dob) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.contact = contact;
        this.gender = gender;
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;

    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getContact() {
        return contact;
    }

    public String getGender() {
        return gender;
    }
}
