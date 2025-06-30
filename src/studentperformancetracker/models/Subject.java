package studentperformancetracker.models;

public class Subject {
    private String subjectCode;
    private String subjectName;
    private int credits;
    
    public Subject(String subjectCode, String subjectName, int credits){
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credits = credits;
    }
    
    // Getters and Setters;
    public String getSubjectCode(){ return subjectCode; }
    
    public String getSubjectName(){return subjectName; }
    
    public int getCredits(){return credits; }
    
    public void setSubjectName(String subjectName){
        this.subjectName = subjectName;
    }
    
    public void setCredits(int credits){
        this.credits = credits;
    }
    
    @Override
    public String toString(){
        return subjectCode + " | " + subjectName + " | " + credits + " credits"; 
    }
}

