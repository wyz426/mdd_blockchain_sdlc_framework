import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;

public class Interface {

    String className;
    String fatherClass;
    HashSet<Method> methods;

    String filePath;

    public Interface(String className,String filePath){
        this.className=className;
        this.fatherClass="";
        this.methods=new HashSet<Method>();
        this.filePath=filePath;
    }

    public void Set_fatherClass(String name){
        this.fatherClass=name;
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
        result+=("public interface "+className+" ");

        //继承父类
        if(fatherClass!=""&&fatherClass!=null){
            result+=(" extends "+fatherClass);
        }

        result+=("{\n");

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
