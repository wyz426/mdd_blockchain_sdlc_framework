import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class Main {
    public static String generatePath="F:\\Xml_generated_code\\src\\main\\java";
    public static void generateClassesFiles(){
        System.out.println("生成函数running");
        Class classA=new Class("ClassNameA","F:\\Xml_generated_code\\src\\main\\java");
        classA.Set_fatherClass("MyfaterNameA");
        classA.Add_interface("interfaceA");
        classA.Add_interface("interfaceCC");
        classA.Add_variable(new Variable("int","id"));
        classA.Add_variable(new Variable("String","name"));
        classA.Add_variable(new Variable("char","c"));
        classA.Add_method(new Method("MethodA","void"));
        classA.Add_method(new Method("MethodB","void"));
        classA.generate();
        Interface interfaceA=new Interface("interfaceA","F:\\Xml_generated_code\\src\\main\\java");
        interfaceA.generate();
        Interface interfaceCC=new Interface("interfaceCC","F:\\Xml_generated_code\\src\\main\\java");
        interfaceCC.generate();
        Class classf=new Class("MyfaterNameA","F:\\Xml_generated_code\\src\\main\\java");
        classf.generate();
    }

    public static void XML2UML(){
        try {
            // 1. 创建DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 2. 创建DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 3. 解析XML文件并返回Document对象
            Document doc = builder.parse(new File("F:\\XML2Class\\src\\main\\resources\\XML2.xml"));
            // 4. 规范化XML结构
            doc.getDocumentElement().normalize();

            // 5. 获取根节点
            Element root = doc.getDocumentElement();
            System.out.println("Root Element: " + root.getNodeName());

            // 6. 获取所有类和接口的节点
            NodeList classList = doc.getElementsByTagName("class");
            NodeList interfaceList = doc.getElementsByTagName("interface");

            // 7. 解析所有类的信息
            //System.out.println("\nClasses:");
            for (int i = 0; i < classList.getLength(); i++) {
                Node classNode = classList.item(i);
                if (classNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element classElement = (Element) classNode;
                    String className = classElement.getAttribute("name");
                    String accessModifier = classElement.getAttribute("access");
                    String extendsClass = classElement.getAttribute("extends");
                    String implementsInterface = classElement.getAttribute("implements");

                    //创建Class对象
                    //System.out.println("Class Name: " + className);
                    Class temp_class=new Class(className,generatePath);
                    //System.out.println("Access Modifier: " + accessModifier);
                    if (!extendsClass.isEmpty()) {
                        //设置继承的父类
                        temp_class.Set_fatherClass(extendsClass);
                        //System.out.println("Extends: " + extendsClass);
                    }
                    if (!implementsInterface.isEmpty()) {
                        //添加实现的接口
                        temp_class.Add_interface(implementsInterface);
                        //System.out.println("Implements: " + implementsInterface);
                    }

                    // 8. 解析类的变量
                    NodeList variableList = classElement.getElementsByTagName("variable");
                    for (int j = 0; j < variableList.getLength(); j++) {
                        Element varElement = (Element) variableList.item(j);
                        String varName = varElement.getAttribute("name");
                        String varType = varElement.getAttribute("type");
                        String varAccess = varElement.getAttribute("access");
                        //添加变量
                        temp_class.Add_variable(new Variable(varAccess,varType,varName));
                        //System.out.println("Variable: " + varName + " (Type: " + varType + ", Access: " + varAccess + ")");
                    }

                    // 9. 解析类的方法
                    NodeList methodList = classElement.getElementsByTagName("method");
                    for (int k = 0; k < methodList.getLength(); k++) {
                        Element methodElement = (Element) methodList.item(k);
                        String methodName = methodElement.getAttribute("name");
                        String returnType = methodElement.getAttribute("returnType");
                        String methodAccess = methodElement.getAttribute("access");
                        //添加方法
                        temp_class.Add_method(new Method(methodName,returnType,methodAccess));
                        //System.out.println("Method: " + methodName + " (Return Type: " + returnType + ", Access: " + methodAccess + ")");
                    }
                    //生成类文件
                    temp_class.generate();
                    //System.out.println();
                }
            }

            // 10. 解析所有接口的信息
            //System.out.println("Interfaces:");
            for (int i = 0; i < interfaceList.getLength(); i++) {
                Node interfaceNode = interfaceList.item(i);
                if (interfaceNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element interfaceElement = (Element) interfaceNode;
                    String interfaceName = interfaceElement.getAttribute("name");
                    String interfaceAccess = interfaceElement.getAttribute("access");

                    //创建接口
                    Interface temp_interface= new Interface(interfaceName,generatePath);
                    //System.out.println("Interface Name: " + interfaceName);
                    //System.out.println("Access Modifier: " + interfaceAccess);

                    // 11. 解析接口的方法
                    NodeList methodList = interfaceElement.getElementsByTagName("method");
                    for (int k = 0; k < methodList.getLength(); k++) {
                        Element methodElement = (Element) methodList.item(k);
                        String methodName = methodElement.getAttribute("name");
                        String returnType = methodElement.getAttribute("returnType");
                        String methodAccess = methodElement.getAttribute("access");
                        //添加方法
                        temp_interface.Add_method(new Method(methodName,returnType,methodAccess));
                        //System.out.println("Method: " + methodName + " (Return Type: " + returnType + ", Access: " + methodAccess + ")");
                    }
                    //生成接口文件
                    temp_interface.generate();
                    //System.out.println();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void AOM2XML(){
        try {
            // 输入和输出文件
            File inputFile = new File("F:\\XML2Class\\src\\main\\resources\\AOM.xml"); // 原始 XML 文件名
            File outputFile = new File("F:\\XML2Class\\src\\main\\resources\\XML2.xml"); // 转化后的 XML 文件名

            // 创建文档构建器
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // 读取原始 XML 文件
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // 创建新的 XML 文档
            Document newDoc = dBuilder.newDocument();
            Element projectElement = newDoc.createElement("project");
            newDoc.appendChild(projectElement);

            // 获取所有代理
            NodeList agents = doc.getElementsByTagName("agent");

            //接口集合
            HashSet<String> interfaces= new HashSet<>();

            //创建各种类
            for (int i = 0; i < agents.getLength(); i++) {
                Element agent = (Element) agents.item(i);


                // 创建类元素
                String agentName = agent.getElementsByTagName("name").item(0).getTextContent();
                Element classElement = newDoc.createElement("class");//创建的类元素
                classElement.setAttribute("name", agentName);
                classElement.setAttribute("access", "public");

                // 是否实现接口
                //String interface_implemented=agent.getElementsByTagName("agenttype").item(0).getTextContent();
                NodeList interface_list=agent.getElementsByTagName("agenttype");
                if(interface_list.getLength()>0){
                    interfaces.add(interface_list.item(0).getTextContent());
                    classElement.setAttribute("implements",interface_list.item(0).getTextContent());
                }
//                if (!(interface_implemented.isEmpty()||interface_implemented.equals(""))) {
//                    interfaces.add(interface_implemented);
//                    classElement.setAttribute("implements","Role");
//                }

                //获取goals标签，根据goals生成属性和方法
                NodeList goals=agent.getElementsByTagName("goal");
                //System.out.println(goals.getLength());
                for (int j = 0; j< goals.getLength();j++){
                    Element goal = (Element) goals.item(j);
                    String methodNameSentence= goal.getElementsByTagName("description").item(0).getTextContent();
                    StringBuilder result = new StringBuilder();
                    //return input.trim().split("\\s+");
                    //String[] words = methodNameSentence.split(" ");
                    String [] words=methodNameSentence.trim().split("\\s+");
                    for (String word : words) {
                        if (!word.isEmpty()) { // 确保不是空字符串
                            result.append(Character.toUpperCase(word.charAt(0))); // 首字母大写
                            result.append(word.substring(1)); // 追加其余部分
                        }
                    }
                    String methodName= result.toString();
                    String methodType="";
                    Element methodElement=newDoc.createElement("method");

                    //<method name="createRawFood" returnType="Boolean" access="public"/>
                    //方法名
                    methodElement.setAttribute("name",methodName);
                    //方法返回类型
                    NodeList returntype=goal.getElementsByTagName("type");
                    if(returntype.getLength()>0){
                        methodType= goal.getElementsByTagName("type").item(0).getTextContent();
                        methodElement.setAttribute("returnType",methodType);
                    }else {
                        methodElement.setAttribute("returnType","void");
                    }
                    methodElement.setAttribute("access","public");

                    classElement.appendChild(methodElement);

                    //根据message生成属性信息
                    NodeList messages= goal.getElementsByTagName("message");
                    //System.out.println(messages.getLength());
                    for(int k = 0;k< messages.getLength();k++){
                        Element messgae=(Element)  messages.item(k);
                        String varName= messgae.getTextContent();
                        String varType=messgae.getAttribute("type");
                        if(!(varType.equals("")||varType.isEmpty())){
                            varType=varType;
                        }
                        else if(!(methodType.equals("")||methodType.isEmpty())){
                            varType=methodType;
                        }
                        else {
                            varType="String";
                        }
                        //<variable name="quantity" type="int" access="private"/>
                        Element var= newDoc.createElement("variable");
                        var.setAttribute("name",varName);
                        var.setAttribute("type",varType);
                        var.setAttribute("access","private");
                        classElement.appendChild(var);
                    }

                }

                //根据interaction标签生成方法
                NodeList interactions=agent.getElementsByTagName("interaction");
                for(int j = 0 ;j<interactions.getLength(); j ++ ){
                    Element interacton =(Element) interactions.item(j);
                    String methodName=interacton.getElementsByTagName("partner").item(0).getTextContent();
                    String methodType=interacton.getElementsByTagName("type").item(0).getTextContent();
                    if(methodType.equals("Alert")){
                        Element method= newDoc.createElement("method");
                        method.setAttribute("name",methodType+methodName);
                        method.setAttribute("returnType","void");
                        method.setAttribute("access","public");
                        classElement.appendChild(method);
                    }
                }

                // 将类元素添加到项目元素
                projectElement.appendChild(classElement);
            }

            System.out.println(" interfaceLength"+interfaces.size());
            for(String interfaceName:interfaces){
                //<interface name="Role" access="public">
                //        <!-- No specific methods in this example, but could be extended -->
                //    </interface>
                Element interface_ = newDoc.createElement("interface");
                interface_.setAttribute("name",interfaceName);
                interface_.setAttribute("access","public");
                projectElement.appendChild(interface_);
            }

            // 将新文档写入文件
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(newDoc);
            StreamResult result = new StreamResult(outputFile);
            transformer.transform(source, result);

            System.out.println("XML文件转换完成！");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        //将ADOM建模的XML转化为JAVA信息XML文件
        AOM2XML();

        //将写好JAVA类、接口（包含的属性、方法）信息的XML文件转化为相应的文件
        XML2UML();
    }


}
