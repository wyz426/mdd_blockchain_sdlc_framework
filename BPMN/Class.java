import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;

public class Class {
    String className;
    String fatherClass;
    HashSet<String> interfaces;
    HashSet<Variable> variables;
    HashSet<Method> methods;

    String filePath;

    public Class(String className,String filePath){
        this.className=className;
        this.fatherClass="";
        this.interfaces=new HashSet<String>();
        this.variables=new HashSet<Variable>();
        this.methods=new HashSet<Method>();
        this.filePath=filePath;
    }

    public void Set_fatherClass(String name){
        this.fatherClass=name;
    }

    public void Add_interface(String name){
        this.interfaces.add(name);
    }

    public void Add_variable(Variable v){
        this.variables.add(v);
    }

    public void Add_method(Method m){
        this.methods.add(m);
    }

    public void generate(){
        try {
            File file= new File(filePath+"\\"+className+".java");
            if(file.createNewFile()){
                System.out.println("create success");
            }
            else {
                System.out.println("create erro");
            }
            //写入
            BufferedWriter writer= new BufferedWriter(new FileWriter(file));
            writer.write(generateContent());
            writer.close();
            System.out.println("写入成功");

        } catch (Exception  e) {
            e.printStackTrace();
        }
    }
    public String generateContent(){
        String result="" ;
        //类名
        result+=("public class "+className+" ");

        //继承父类
        if(fatherClass!=""&&fatherClass!=null){
            result+=(" extends "+fatherClass);
        }

        //实现接口
        if(!interfaces.isEmpty()){
            result+=(" implements ");
            String[] interfaces_stringArray = interfaces.toArray(new String[0]);
            for(int i=0;i<interfaces_stringArray.length;i++){
                result+=interfaces_stringArray[i];
                if(i<interfaces_stringArray.length-1){
                    result+=",";
                }
            }
            result+=" ";
        }

        result+=("{\n");

        //生成变量
        if(!variables.isEmpty()){
            for(Variable i:variables){
                result+=i.generate();
            }
        }

        //生成方法
        if(!methods.isEmpty()){
            for(Method i:methods){
                result+=i.generate();
            }
        }

        result+=("}\n");

        return result;
    }



}
