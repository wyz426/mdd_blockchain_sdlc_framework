import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.m2m.qvt.oml.ecore.QVTr;
import org.eclipse.m2m.qvt.oml.resources.TransformationExecutor;

import java.io.IOException;

public class RunQVTTransformation {

    public static void main(String[] args) {

        ResourceSetImpl resourceSet = new ResourceSetImpl();

        URI sourceUri = URI.createFileURI("");  
        Resource sourceResource = resourceSet.getResource(sourceUri, true);
        
        URI targetUri = URI.createFileURI("");  
        Resource targetResource = resourceSet.createResource(targetUri);

        URI qvtUri = URI.createFileURI("transformation.qvtr"); 
        resourceSet.getResource(qvtUri, true);

        TransformationExecutor executor = new TransformationExecutor();

        try {
            executor.executeTransformation(sourceResource, targetResource, qvtUri);
            
            targetResource.save(null);
            System.out.println("Transformation completed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Transformation failed.");
        }
    }
}
