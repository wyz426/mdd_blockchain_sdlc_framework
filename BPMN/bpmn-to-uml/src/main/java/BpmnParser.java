import com.squareup.javapoet.TypeName;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class BpmnParser {
    private static final String namespaceURI = "http://www.omg.org/spec/BPMN/20100524/MODEL";

    public static Map<String, List<MethodInfo>> parse(String filePath)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);  // 启用命名空间支持
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(new File(filePath));

        List<Pair<String, String>> identifyParticipant = identifyParticipant(document);
        if (identifyParticipant == null) {
            return null;
        }

        Map<String, List<MethodInfo>> classMethodMap = new HashMap<>();
        identifyParticipant.forEach(pair ->
                classMethodMap.put(pair.getKey(), identifyProcess(pair.getKey(), pair.getValue(), document)));

        return classMethodMap;
    }

    /**
     * 获取类的名称和id
     *
     * @return List<Pair < String, String>> (className, processRef)
     */
    private static List<Pair<String, String>> identifyParticipant(Document document) {
        NodeList participants = Optional.ofNullable(document.getElementsByTagNameNS(namespaceURI, "collaboration"))
                .filter(collaborations -> collaborations.getLength() >= 1)
                .map(collaborations -> (Element) collaborations.item(0))
                .map(collaboration -> collaboration.getElementsByTagNameNS(namespaceURI, "participant"))
                .orElse(null);

        if (participants == null || participants.getLength() == 0) {
            return null;
        }

        List<Pair<String, String>> res = new ArrayList<>();
        for (int i = 0; i < participants.getLength(); i++) {
            Element participant = (Element) participants.item(i);
            String className = participant.getAttribute("name");
            String processRef = participant.getAttribute("processRef");
            res.add(new Pair<>(className, processRef));
        }

        return res;
    }

    private static List<MethodInfo> identifyProcess(String className, String processRef, Document document) {
        NodeList processList = document.getElementsByTagNameNS(namespaceURI, "process");
        for (int i = 0; i < processList.getLength(); i++) {
            Element process = (Element) processList.item(i);
            if (!process.getAttribute("id").equals(processRef)) {
                continue;
            }

            NodeList tasks = process.getElementsByTagNameNS(namespaceURI, "task");
            List<MethodInfo> res = new ArrayList<>();
            for (int j = 0; j < tasks.getLength(); j++) {
                Element task = (Element) tasks.item(j);
                StringBuilder methodNameBuilder = new StringBuilder();
                for (String s : task.getAttribute("name").split(" ")) {
                    methodNameBuilder.append(s);
                }
                methodNameBuilder.setCharAt(0, String.valueOf(methodNameBuilder.charAt(0)).toLowerCase().charAt(0));
                MethodInfo methodInfo = new MethodInfo();
                methodInfo.setClassName(className);
                methodInfo.setReturnType(TypeName.VOID);
                methodInfo.setMethodName(methodNameBuilder.toString());
                res.add(methodInfo);
            }
            return res;
        }
        return null;
    }

//    private static List<MethodInfo> identifyProcess(Document document) {
//        NodeList startEvents = document.getElementsByTagNameNS(namespaceURI, "startEvent");
//        List<MethodInfo> res = new ArrayList<>();
//        for (int i = 0; i < startEvents.getLength(); i++) {
//            MethodInfo methodInfo = new MethodInfo();
//            methodInfo.setMethodName("startMethod");
//            Element startEvent = (Element) startEvents.item(i);
//            methodInfo.setClassName(getClassNameByElement(startEvent));
//            methodInfo.setReturnType(TypeName.VOID);
//            String startEventId = startEvent.getAttribute("id");
//
//            List<MethodInfo> nextMethods = new ArrayList<>();
//            NodeList outgoings = startEvent.getElementsByTagNameNS(namespaceURI, "outgoing");
//            for (int j = 0; j < outgoings.getLength(); j++) {
//                Element outgoingDocument = (Element) outgoings.item(j);
//                String sequenceFlowId = outgoingDocument.getTextContent();
//
//                nextMethods.add(buildNextMethods(document, sequenceFlowId));
//            }
//            methodInfo.setNextMethods(nextMethods);
//            res.add(methodInfo);
//        }
//        return res;
//    }
//
//    private static MethodInfo buildNextMethods(Document document, String sequenceFlowId) {
//        NodeList sequenceFlows = document.getElementsByTagNameNS(namespaceURI, "sequenceFlow");
////        for (int i = 0; i < sequenceFlows.getLength(); i++) {
////            Element method = (Element) sequenceFlows.item(i);
////            NodeList nextMethods = method.getElementsByTagNameNS(namespaceURI, "outgoing");
////            for (int j = 0; j < nextMethods.getLength(); j++) {
////                Element nextMethod = (Element) nextMethods.item(j);
////                nextMethod.getAttribute()
////            }
////        }
//        return new MethodInfo();
//    }
//
//    private static String getClassNameByElement(Element element) {
//        return null;
//    }

//
//    public static void main(String[] args) {
//        try {
//            BpmnParser.parse("src/main/resources/diagram.bpmn");
//        } catch (ParserConfigurationException | IOException | SAXException e) {
//            e.printStackTrace();
//        }
//    }
}
