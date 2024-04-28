public class Identifier {
    private String task, event, chore, other;
    private String id;

    public Identifier(String event){
        setId(event);

    }


    public String getId(){
        return id;

    }
    private void setId(String identifier){
        switch (identifier){
            case "task":
                id = "task";
            case "event":
                id = "event";
            case "chore":
                id = "chore";
            default:
                id = "other";
        }


    }
}
