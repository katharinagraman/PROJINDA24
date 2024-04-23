public class Task{
    private String taskName;
    private String description;
    private boolean task = false; // gör såhär nog ist för det blir lättare isf döp om till Type kanske
    // osen typ att man ärver så type = new task ()
    // type = new event() men isf kan man ha type som en typ i map
    // private date = Localtime

    // Boolean event = false;


    // Constructor
    public Task(String name, String description){
        this.taskName = name;
        this.description = description;
    }


    // addName
    public void addName(String name){
        taskName = name;

    }

    
}