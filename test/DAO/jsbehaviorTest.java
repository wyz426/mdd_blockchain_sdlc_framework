import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.acceleo.engine.AcceleoEngine;
import org.eclipse.acceleo.module.engine.AcceleoExecutionContext;
import org.eclipse.emf.ecore.xmi.XMIResourceFactoryImpl;

import java.io.File;
import java.io.IOException;

public class jsbehaviorTest {

    public static void main(String[] args) {
        // Initialize EMF resource set
        ResourceSetImpl resourceSet = new ResourceSetImpl();
        URI umlModelUri = URI.createFileURI("model.uml");  
        Resource umlResource = resourceSet.getResource(umlModelUri, true);
        AcceleoEngine acceleoEngine = new AcceleoEngine();

        URI acceleoTemplateUri = URI.createFileURI("testjsbehavior.mtl");  

        // Initialize the Acceleo context
        AcceleoExecutionContext context = new AcceleoExecutionContext();
        try {
            acceleoEngine.evaluateTemplate(umlResource, acceleoTemplateUri, context);
            System.out.println("Acceleo transformation completed successfully.");
            File jsFile = new File("path/to/output/test.js");
            context.getModelAdapter().save(jsFile); 
            File solFile = new File("path/to/output/testseq.sol");
            context.getModelAdapter().save(solFile); 
        } catch (IOException e) {
            System.err.println("Error during Acceleo transformation: " + e.getMessage());
        }
    }
}
