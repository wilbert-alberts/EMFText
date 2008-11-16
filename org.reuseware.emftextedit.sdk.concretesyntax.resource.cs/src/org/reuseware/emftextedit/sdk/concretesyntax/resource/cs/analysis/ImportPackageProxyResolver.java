package org.reuseware.emftextedit.sdk.concretesyntax.resource.cs.analysis; 

import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.reuseware.emftextedit.runtime.resource.TextResource;
import org.reuseware.emftextedit.runtime.resource.impl.ProxyResolverImpl;
import org.reuseware.emftextedit.sdk.MetamodelHelper;
import org.reuseware.emftextedit.sdk.concretesyntax.ConcreteSyntax;
import org.reuseware.emftextedit.sdk.concretesyntax.Import;

public class ImportPackageProxyResolver extends ProxyResolverImpl {

	private MetamodelHelper mmHelper = new MetamodelHelper();

	@Override
	protected EObject doResolve(InternalEObject proxy, EObject container,
			EReference reference, TextResource resource) {
		GenPackage genPackage = mmHelper.findGenPackage(getOptions(), proxy.eProxyURI().fragment(), resource);
		if (genPackage != null) {
			ConcreteSyntax cs = (ConcreteSyntax)((Import)container).eContainer();
			if(!cs.getPackage().equals(genPackage)&&!cs.getPackage().getNSURI().equals(genPackage.getNSURI()))
				cs.getPackage().getGenModel().getUsedGenPackages().add(genPackage);
			
		}
		return genPackage;
	}
	

	@Override
	protected String produceResolveErrorMessage(InternalEObject proxy,
			EObject container, EReference reference, TextResource resource) {
		String message = "Genarator model \"" + proxy.eProxyURI().fragment() + "\" could not be resolved";
		return message;
	}
	
	public String deResolve(EObject element, EObject container,EReference reference){
		GenPackage pck = (GenPackage)element;
		return pck.getNSURI();
	}

}
