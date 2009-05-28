/*******************************************************************************
 * Copyright (c) 2006-2009 
 * Software Technology Group, Dresden University of Technology
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * See the GNU Lesser General Public License for more details. You should have
 * received a copy of the GNU Lesser General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA  02111-1307 USA
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany 
 *   - initial API and implementation
 ******************************************************************************/
package org.emftext.sdk.finders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.EList;
import org.emftext.sdk.codegen.util.GenClassUtil;
import org.emftext.sdk.codegen.util.Pair;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;
import org.emftext.sdk.concretesyntax.Import;

/**
 * A GenClassFinder can be used to look up all generator classes that are 
 * available in a concrete syntax specification. This may include classes
 * from imported generator packages, from imported syntaxes and nested or 
 * used packages of both.
 */
public class GenClassFinder {
	
	public static final String DOT = ".";
	
	private static final GenClassUtil genClassUtil = new GenClassUtil();
	
	/**
	 * Returns all generator classes in the given syntax.
	 * 
	 * @param syntax the syntax to search in
	 * @param includingImports indicates whether included package shall be included in the search
	 * 
	 * @return a found classes
	 */
	public Set<GenClass> findAllGenClasses(ConcreteSyntax syntax, boolean includingImports, boolean includeUsedGeneratorModels) {
		List<Pair<String, GenClass>> foundClassesAndPrefixes = findAllGenClassesAndPrefixes(syntax, includingImports, includeUsedGeneratorModels);
		return convertToGenClassList(foundClassesAndPrefixes);
	}

	/**
	 * Returns all generator classes in the given syntax including their prefixes.
	 * 
	 * @param syntax the syntax to search in
	 * @param includingImports indicates whether included package shall be included in the search
	 * 
	 * @return a found classes
	 */
	public List<Pair<String, GenClass>> findAllGenClassesAndPrefixes(ConcreteSyntax syntax, boolean includingImports, boolean includeUsedGeneratorModels) {
		return findAllGenClassesAndPrefixes(null, syntax, includingImports, includeUsedGeneratorModels);
	}
	
	private List<Pair<String, GenClass>> findAllGenClassesAndPrefixes(String prefix, ConcreteSyntax syntax, boolean includingImports, boolean includeUsedGeneratorModels) {
		List<Pair<String, GenClass>> foundClasses = new ArrayList<Pair<String, GenClass>>();
		if (syntax == null) {
			return foundClasses;
		}
		// first add all generator classes contained in the generator package
		// that is referenced by the concrete syntax
		GenPackage genPackage = syntax.getPackage();
		if (genPackage == null) {
			return foundClasses;
		}
		foundClasses.addAll(findAllGenClassesAndPrefixes(prefix, genPackage));

		if (includeUsedGeneratorModels) {
			// second add classes from used generator packages
		    if (genPackage.eContainer() != null) {
			    final GenModel genModel = genPackage.getGenModel();
				for (GenPackage usedGenPackage : genModel.getUsedGenPackages()) {
			    	foundClasses.addAll(findAllGenClassesAndPrefixes(prefix, usedGenPackage));
				}
		    }
		}
		
		// then add the imported generator classes
	    if (includingImports) {
	    	foundClasses.addAll(findGenClassesAndPrefixesInImports(prefix, syntax));
	    }
		return foundClasses;
	}

	private Set<GenClass> convertToGenClassList(
			final List<Pair<String, GenClass>> foundClassesAndPrefixes) {
		Set<GenClass> foundClasses = new LinkedHashSet<GenClass>();
		for (Pair<String, GenClass> prefixAndGenClass : foundClassesAndPrefixes) {
			final GenClass right = prefixAndGenClass.getRight();
			if (!contains(foundClasses, right)) {
				foundClasses.add(right);
			}
		}
		return foundClasses;
	}
	
	public boolean contains(Collection<GenClass> genClasses, GenClass genClass) {
		for (GenClass next : genClasses) {
			if (next.getQualifiedInterfaceName().equals(genClass.getQualifiedInterfaceName())) {
				return true;
			}
		}
		return false;
	}

	private List<Pair<String, GenClass>> findGenClassesAndPrefixesInImports(String prefix, ConcreteSyntax syntax) {
		List<Pair<String, GenClass>> foundClasses = new ArrayList<Pair<String, GenClass>>();
		for (Import nextImport : syntax.getImports()) {
			final List<Pair<String, GenClass>> classesInImportedPackage = findAllGenClassesAndPrefixes((prefix == null ? "" : prefix + DOT) + nextImport.getPrefix(), nextImport.getPackage());
			foundClasses.addAll(classesInImportedPackage);
		}
		return foundClasses;
	}

	private List<Pair<String, GenClass>> findAllGenClassesAndPrefixes(String prefix, GenPackage genPackage) {
		List<Pair<String, GenClass>> foundClasses = new ArrayList<Pair<String, GenClass>>();
		if(genPackage == null) {
			return foundClasses;
		}
		// first add all generator classes in the package itself
		EList<GenClass> genClasses = genPackage.getGenClasses();
		for (GenClass genClass : genClasses) {
			foundClasses.add(new Pair<String, GenClass>(prefix == null ? "" : prefix + DOT, genClass));
		}
		// then add all generator classes contained in sub packages
		for (GenPackage subPackage : genPackage.getSubGenPackages()) {
			List<Pair<String, GenClass>> classesInSubPackage = findAllGenClassesAndPrefixes((prefix == null ? "" : prefix + DOT) + subPackage.getPrefix(), subPackage);
			foundClasses.addAll(classesInSubPackage);
		}
		return foundClasses;
	}

	public Map<String, Collection<String>> findAllSuperclasses(Collection<GenClass> allGenClasses) {
		Map<String, Collection<String>> genClassName2superNames = new HashMap<String, Collection<String>>();
	    
	    for (GenClass genClass : allGenClasses) {
			Collection<String> superClasses = new LinkedList<String>();
			for (GenClass superClass : genClass.getAllBaseGenClasses()) {
				superClasses.add(superClass.getQualifiedInterfaceName());
			}
			genClassName2superNames.put(genClass.getQualifiedInterfaceName(), superClasses);
		}
	    return genClassName2superNames;
	}

	public Collection<GenClass> findAllSubclasses(ConcreteSyntax syntax, GenClass superClass) {
		Collection<GenClass> foundSubclasses = new ArrayList<GenClass>();
	    
	    for (GenClass genClass : findAllGenClasses(syntax, true, true)) {
			if (genClassUtil .isSuperClass(superClass, genClass)) {
				foundSubclasses.add(genClass);
			}
		}
	    return foundSubclasses;
	}

	public ConcreteSyntax getContainingSyntax(ConcreteSyntax cs, GenClass genClass) {
		if (cs == null) return null;
		if (contains(findAllGenClasses(cs, false, false), genClass)) {
			return cs;
		}
		EList<Import> imports = cs.getImports();
		for (Import imported : imports) {
			ConcreteSyntax containingSyntax = getContainingSyntax(imported.getConcreteSyntax(), genClass);
			if (containingSyntax != null) {
				return containingSyntax;
			}
		}
		return null;
	}
}
