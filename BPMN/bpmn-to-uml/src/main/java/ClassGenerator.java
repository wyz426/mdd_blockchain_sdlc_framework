import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassGenerator {
    private static final Map<String, MethodSpec.Builder> methodBuilders = new HashMap<>();

    public static void generator(Map<String, List<MethodInfo>> classMethodsMap, String filePath) {
        classMethodsMap.forEach((className, methodInfoList) -> {
            methodInfoList.forEach(ClassGenerator::generateProcess);
            List<String> methodsName = methodInfoList.stream().map(MethodInfo::getMethodName).collect(Collectors.toList());
            List<MethodSpec> methods = new ArrayList<>();
            methodsName.forEach(methodName -> {
                MethodSpec.Builder methodBuilder = methodBuilders.get(methodName);
                methods.add(methodBuilder.build());
            });
            TypeSpec generatedClass = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethods(methods)
                    .build();
            JavaFile javaFile = JavaFile.builder("", generatedClass).build();
            try {
                File file = new File(filePath);
                Path path = Paths.get(filePath);
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                javaFile.writeTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("代码成功生成！");
    }

    private static void generateProcess(MethodInfo methodInfo) {
        if (methodInfo == null) {
            return;
        }

        String methodName = methodInfo.getMethodName();
        TypeName returnType = methodInfo.getReturnType();

        MethodSpec.Builder builder;
        if (methodBuilders.containsKey(methodName)) {
            builder = methodBuilders.get(methodName);
        } else {
            builder = MethodSpec.methodBuilder(methodName);
            methodBuilders.put(methodName, builder);
        }
        builder.addModifiers(Modifier.PUBLIC)
                .returns(returnType);

    }

//    private static String generateProcess(MethodInfo methodInfo) {
//        if (methodInfo == null) {
//            return "";
//        }
//
//        String methodName = methodInfo.getMethodName();
//        TypeName returnType = methodInfo.getReturnType();
//        List<MethodInfo> nextMethods = methodInfo.getNextMethods();
//
//        MethodSpec.Builder builder;
//        if (methodBuilders.containsKey(methodName)) {
//            builder = methodBuilders.get(methodName);
//        } else {
//            builder = MethodSpec.methodBuilder(methodName);
//            methodBuilders.put(methodName, builder);
//        }
//        builder.addModifiers(Modifier.PUBLIC)
//                .returns(returnType);
//
//        nextMethods.forEach(nextMethod -> {
//            String nextMethodName = generateProcess(nextMethod);
//            if (!nextMethodName.isEmpty()) {
//                builder.addStatement("$S", nextMethodName);
//            }
//        });
//
//        return methodName;
//    }

}
