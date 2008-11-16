package org.reuseware.emftextedit.sdk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.ecore.EObject;
import org.reuseware.emftextedit.runtime.resource.TextResource;

public class MetamodelHelper {
	
	public final static String GEN_PACKAGE_FINDER_KEY = "GEN_PACKAGE_FINDER";
	
	public GenPackage findGenPackage(Map<?,?> options, String fragment, TextResource resource) {
		MetamodelManager mmManager = createMetaModelManager(options);
		return mmManager.findGenPackage(fragment, resource);
	}

	public EObject findConcreteSyntax(Map<?, ?> options, String fragment,
			GenPackage genPackage, TextResource resource) {
		MetamodelManager mmManager = createMetaModelManager(options);
		return mmManager.findConcreteSyntax(fragment, genPackage, resource);
	}

	private MetamodelManager createMetaModelManager(Map<?, ?> options) {
		MetamodelManager mmManager = new MetamodelManager();
		List<GenPackageFinder> finders = findGenPackageFinder(options);
		for (GenPackageFinder finder : finders) {
			mmManager.addGenPackageFinder(finder);
		}
		return mmManager;
	}

	private List<GenPackageFinder> findGenPackageFinder(Map<?, ?> options) {
		List<GenPackageFinder> finders = new ArrayList<GenPackageFinder>();
		if (options == null) {
			return finders;
		}
		if (!options.containsKey(GEN_PACKAGE_FINDER_KEY)) {
			return finders;
		}
		Object value = options.get(GEN_PACKAGE_FINDER_KEY);
		if (value == null) {
			return finders;
		}
		if (value instanceof GenPackageFinder) {
			finders.add((GenPackageFinder) value);
			return finders;
		}
		if (value instanceof List) {
			List<?> values = (List<?>) value;
			for (Object next : values) {
				if (next instanceof GenPackageFinder) {
					finders.add((GenPackageFinder) next);
				}
			}
		}
		return finders;
	}
}
