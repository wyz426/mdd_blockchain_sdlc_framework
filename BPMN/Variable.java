//private String name;

public class Variable {
    String attribute;
    String type;
    String name;
    public Variable(){
        this.attribute="";
        this.type="";
        this.name="";
    }
    public Variable(String type,String name){
        this.attribute="public";
        this.type=type;
        this.name=name;
    }
    public Variable(String attribute,String type,String name){
        this.attribute=attribute;
        this.type=type;
        this.name=name;
    }
    public String generate(){
        return "    "+attribute+" "+type+" "+name+";\n";
    }


}
