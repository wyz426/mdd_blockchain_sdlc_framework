import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.io.*;

public class BpmnToUmlConverter {

    public static void main(String[] args) {
        try {
            Map<String, List<MethodInfo>> parse = BpmnParser.parse("src/main/resources/diagram.bpmn");
            if (parse != null) {
                ClassGenerator.generator(parse, "F:\\bpnm\\generated-code\\src\\main\\java");
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        BpmnToUmlConverter b2u= new BpmnToUmlConverter();
        b2u.Create_Role();
        b2u.Create_Role_Relation();
    }
    public void Create_Role(){
        String filePth="F:\\bpnm\\generated-code\\src\\main\\java\\Role.java";
        String interfaceContent="public interface Role{\n"+
                                 " void performRole();\n"+
                                 "}\n";
        try{
            File file= new File(filePth);
            if(file.createNewFile()){
                System.out.println("create success");
            }
            else {
                System.out.println("create erro");
            }
            //写入
            BufferedWriter writer= new BufferedWriter(new FileWriter(file));
            writer.write(interfaceContent);
            writer.close();
            System.out.println("写入成功");
        }catch (IOException e){
            System.out.println("erro"+e.getMessage());
        }
    }
    public  void  Create_Role_Relation(){
        String directoryPath= "F:\\bpnm\\generated-code\\src\\main\\java";
        File directory= new File(directoryPath);
        File[] files=directory.listFiles((dir,name)->name.endsWith(".java"));
        if(files!=null){
            for(File file:files){
                try{
                    StringBuilder fileContent= new StringBuilder();
                    BufferedReader reader= new BufferedReader(new FileReader(file));
                    String line;
                    boolean classFound = false;
                    String className="";
                    while ((line=reader.readLine())!=null){
                        fileContent.append(line).append("\n");
                        if(line.contains("class ")) {
                            classFound = true;

                            int startIndex = line.indexOf("class ") + 6;
                            int endIndex = line.indexOf(" ", startIndex);
                            if (endIndex == -1) {
                                endIndex = line.indexOf("{", startIndex);
                            }
//                            System.out.println(line);
//                            System.out.println(startIndex);
//                            System.out.println(endIndex);
                            className = line.substring(startIndex, endIndex).trim();
                        }

                    }
                    reader.close();
                    //找到类名之后，添加接口内容
                    if(classFound){
                        System.out.println("找到类名"+className);
                        String updatedContent=fileContent.toString().trim().replaceFirst(
                          "class "+className,
                          "class "+className+" implements Role"
                        );
                        //添加方法
                        if(updatedContent!=null &&updatedContent.length()>1){
                            updatedContent=updatedContent.substring(0,updatedContent.length()-2);
                        }
                        else {
                            System.out.println("字符串为长度");
                        }
                        updatedContent+="\n\n"+
                                "@Override\n"+
                                "public void performRole(){\n"+
                                "         //实现方法\n"+
                               // "return null;\n"+
                                "}\n"+
                                "}\n";

                        BufferedWriter writer= new BufferedWriter(new FileWriter(file));
                        writer.write(updatedContent);
                        writer.close();
                    }

                }
                catch (IOException e){
                    System.out.println("erro"+e.getMessage());
                }
            }
        }
        else{
            System.out.println("未找到文件");
        }


    }

}
