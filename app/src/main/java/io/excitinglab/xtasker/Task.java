package io.excitinglab.xtasker;

public class Task {

    int id;
    String name;
    int status;
    long deadline;
    long reminder;
    String note;

    public Task() {

    }

    public Task(String name) {
        this.name = name;
    }

    public Task(String name, int status, long deadline, long reminder, String note) {
        this.name = name;
        this.status = status;
        this.deadline = deadline;
        this.reminder = reminder;
        this.note = note;
    }

//    public Task(String name, int status) {
//        this.id = id;
//        this.name = name;
//        this.status = status;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public void setReminder(long reminder) {
        this.reminder = reminder;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getStatus() {
        return this.status;
    }

    public long getDeadline() {
        return this.deadline;
    }

    public long getReminder() {
        return this.reminder;
    }

    public String getNote() {
        return this.note;
    }
}
