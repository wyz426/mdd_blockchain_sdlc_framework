//    public void eat() {
//        System.out.println(name + " is eating.");
//        }
public class Method {
    String attribute;
    String returnType;
    String name;
    String body="";

    public Method(){
        this.attribute="public";
        this.returnType="void";
        this.name="Method";
    }
    public Method(String name){
        this.attribute="public";
        this.returnType="void";
        this.name=name;
    }
    public Method(String name,String returnType){
        this.attribute="public";
        this.returnType=returnType;
        this.name=name;
    }
    public Method(String name,String returnType,String attribute){
        this.attribute=attribute;
        this.returnType=returnType;
        this.name=name;
    }
    public String generate(){
        String result ="";
        result+=("    "+attribute+" "+returnType+" "+name+"(){\n");
        result+=body;
        if(returnType.equalsIgnoreCase("void")){
            result+=("        return;\n");
        }
        else if(returnType.equalsIgnoreCase("String")){
            result+=("        return \"\";\n");
        }
        else if(returnType.equalsIgnoreCase("int")||returnType.equalsIgnoreCase("float")||returnType.equalsIgnoreCase("double")){
            result+=("        return 0;\n");
        }
        else if(returnType.equalsIgnoreCase("boolean")){
            result+=("        return true;\n");
        }
        else if(returnType.equalsIgnoreCase("char")){
            result+=("        return '';\n");
        }
        else {
            result+=("        return new "+returnType+"()"+";\n");
        }

        result+=("    }\n");
        return result;
    }



}
