import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.acceleo.engine.AcceleoEngine;
import org.eclipse.acceleo.module.engine.AcceleoExecutionContext;
import org.eclipse.acceleo.module.engine.AcceleoEvaluationContext;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMIResourceFactoryImpl;
import org.eclipse.acceleo.engine.service.AcceleoService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GotoClassTest {

    public static void main(String[] args) {
        ResourceSetImpl resourceSet = new ResourceSetImpl();
        URI umlModelUri = URI.createFileURI("testgoclass.uml"); 
        Resource umlResource = resourceSet.getResource(umlModelUri, true);
        AcceleoEngine acceleoEngine = new AcceleoEngine();
        URI acceleoTemplateUri = URI.createFileURI("testgoclass.mtl"); 

        AcceleoExecutionContext context = new AcceleoExecutionContext();
        try {
            acceleoEngine.evaluateTemplate(umlResource, acceleoTemplateUri, context);
            System.out.println("Acceleo transformation completed successfully.");
            File goFile = new File("path/to/output/test.go");
            context.getModelAdapter().save(goFile); 
        } catch (IOException e) {
            System.err.println("Error during Acceleo transformation: " + e.getMessage());
        }
    }
}
