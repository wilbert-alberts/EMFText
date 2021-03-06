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
package org.emftext.sdk.codegen.resource.generators.mopp;

import static org.emftext.sdk.codegen.resource.ClassNameConstants.I_PROGRESS_MONITOR;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.I_STATUS;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.STATUS;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.SUB_PROGRESS_MONITOR;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.URI;

import org.emftext.sdk.OptionManager;
import org.emftext.sdk.codegen.annotations.SyntaxDependent;
import org.emftext.sdk.codegen.parameters.ArtifactParameter;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.generators.JavaBaseGenerator;
import org.emftext.sdk.concretesyntax.OptionTypes;

import de.devboost.codecomposers.java.JavaComposite;

@SyntaxDependent
public class BuilderGenerator extends JavaBaseGenerator<ArtifactParameter<GenerationContext>> {

	@Override
	public void generateJavaContents(JavaComposite sc) {
		sc.add("package " + getResourcePackageName() + ";");sc.addLineBreak();sc.addImportsPlaceholder();
		sc.addLineBreak();
		sc.add("public class " + getResourceClassName() + " implements " + iBuilderClassName + " {");
		sc.addLineBreak();
		boolean removeEclipseDependentCode = OptionManager.INSTANCE.getBooleanOptionValue(getContext().getConcreteSyntax(), OptionTypes.REMOVE_ECLIPSE_DEPENDENT_CODE);
		if (!removeEclipseDependentCode) {
	addMethods(sc);
		} else {
			sc.addComment("This class is empty because option '" + OptionTypes.REMOVE_ECLIPSE_DEPENDENT_CODE.getLiteral() + "' is set to true.");
		}
		sc.add("}");
	}

	private void addMethods(JavaComposite sc) {
		addIsBuildingNeededMethod(sc);
		addBuildMethod(sc);
		addHandleDeletionMethod(sc);
	}

	private void addIsBuildingNeededMethod(JavaComposite sc) {
		sc.add("public boolean isBuildingNeeded(" + URI(sc) + " uri) {");
		sc.addComment("Change this to return true to enable building of all resources.");
		sc.add("return false;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addBuildMethod(JavaComposite sc) {
		sc.add("public " + I_STATUS(sc) + " build(" + textResourceClassName + " resource, " + I_PROGRESS_MONITOR(sc) + " monitor) {");
		sc.addComment("Set option '" + OptionTypes.OVERRIDE_BUILDER + "' to 'false' and then perform build here.");
		sc.addLineBreak();
		sc.addComment("We use one tick from the parent monitor because the BuilderAdapter reserves one tick for the Builder.");
		sc.add(SUB_PROGRESS_MONITOR(sc) + " subMonitor = new " + SUB_PROGRESS_MONITOR(sc) + "(monitor, 1);");
		sc.add("subMonitor.beginTask(\"Building \" + resource.getURI().lastSegment(), 10);");
		sc.addComment("The actual work of the builder can be performed here.");
		sc.add("subMonitor.worked(10);");
		sc.add("subMonitor.done();");
		sc.add("return " + STATUS(sc) + ".OK_STATUS;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addHandleDeletionMethod(JavaComposite sc) {
		sc.addJavadoc(
			"Handles the deletion of the given resource."
		);
		sc.add("public " + I_STATUS(sc) + " handleDeletion(" + URI(sc) + " uri, " + I_PROGRESS_MONITOR(sc) + " monitor) {");
		sc.addComment("by default nothing is done when a resource is deleted");
		sc.add("return " + STATUS(sc) + ".OK_STATUS;");
		sc.add("}");
		sc.addLineBreak();
	}
}
