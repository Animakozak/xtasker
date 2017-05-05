package io.excitinglab.xtasker;

public class Lists {
    int id;
    String name;

    public Lists() {

    }

    public Lists(String name) {
        this.name = name;
    }

//    public Lists(int id, String name) {
//        this.id = id;
//        this.name = name;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
