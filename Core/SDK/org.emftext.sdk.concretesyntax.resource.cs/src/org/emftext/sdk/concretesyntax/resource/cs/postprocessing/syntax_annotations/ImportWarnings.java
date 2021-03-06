/*******************************************************************************
 * Copyright (c) 2006-2012
 * Software Technology Group, Dresden University of Technology
 * DevBoost GmbH, Berlin, Amtsgericht Charlottenburg, HRB 140026
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany;
 *   DevBoost GmbH - Berlin, Germany
 *      - initial API and implementation
 ******************************************************************************/
package org.emftext.sdk.concretesyntax.resource.cs.postprocessing.syntax_annotations;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;
import org.emftext.sdk.concretesyntax.Import;
import org.emftext.sdk.concretesyntax.resource.cs.postprocessing.AbstractPostProcessor;
import org.emftext.sdk.concretesyntax.resource.cs.postprocessing.IProblemWrapper;
import org.emftext.sdk.util.EObjectUtil;

/**
 * This class collects the warnings the were issued for imported syntax elements
 * and moves them to the import element of the main syntax.
 */
public class ImportWarnings extends AbstractPostProcessor {

	@Override
	public void analyse(ConcreteSyntax syntax) {
		Set<Import> handledImports = new LinkedHashSet<Import>();
		for (Import nextImport : syntax.getImports()) {
			movedWarningsFromImportsToMainSyntax(nextImport, nextImport, handledImports);
		}
	}

	private void movedWarningsFromImportsToMainSyntax(Import mainImport, Import currentImport, Set<Import> handledImports) {
		handledImports.add(currentImport);
		ConcreteSyntax importedSyntax = currentImport.getConcreteSyntax();
		if (importedSyntax == null) {
			return;
		}
		for (IProblemWrapper warning : getContext().getWarnings()) {
			EObject cause = warning.getCause();
			// warning is not caused by an EObject
			if (cause == null) {
				continue;
			}
			EObject root = EObjectUtil.findRootContainer(cause);
			if (root == importedSyntax) {
				warning.setCause(mainImport);
			}
		}
		for (Import nextImport : importedSyntax.getImports()) {
			if (handledImports.contains(nextImport)) {
				continue;
			}
			movedWarningsFromImportsToMainSyntax(mainImport, nextImport, handledImports);
		}
	}
}
