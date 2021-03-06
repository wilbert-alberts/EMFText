/*******************************************************************************
 * Copyright (c) 2006-2014
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
package org.emftext.sdk.codegen.resource.ui.generators.ui;

import static de.devboost.codecomposers.java.ClassNameConstants.LINKED_HASH_MAP;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.BASIC_COMMAND_STACK;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.RESOURCE;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.ADAPTER_FACTORY;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.ADAPTER_FACTORY_EDITING_DOMAIN;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.EDITING_DOMAIN;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_EDITOR_INPUT;

import org.emftext.sdk.codegen.annotations.SyntaxDependent;
import org.emftext.sdk.codegen.parameters.ArtifactParameter;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.ui.generators.UIJavaBaseGenerator;

import de.devboost.codecomposers.java.JavaComposite;

@SyntaxDependent
public class EditingDomainProviderGenerator extends UIJavaBaseGenerator<ArtifactParameter<GenerationContext>> {

	@Override
	public void generateJavaContents(JavaComposite sc) {
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.addImportsPlaceholder();
		sc.addLineBreak();
		sc.addJavadoc(
			"The EditingDomainProvider is used by the editor to obtain an EMF editing domain. " +
			"This default implementation creates a new editing domain for each editor input."
		);
		sc.add("public class " + getResourceClassName() + " {");
		sc.addLineBreak();

		addMethods(sc);

		sc.add("}");
	}

	private void addMethods(JavaComposite sc) {
		addGetEditingDomainMethod(sc);
		addCreateEditingDomainMethod(sc);
	}

	private void addGetEditingDomainMethod(JavaComposite sc) {
		sc.add("public " + EDITING_DOMAIN(sc) + " getEditingDomain(" + I_EDITOR_INPUT(sc) + " editorInput) {");
		sc.add("return createEditingDomain();");
		sc.add("}");
		sc.addLineBreak();
	}
	
	private void addCreateEditingDomainMethod(JavaComposite sc) {
		sc.add("private " + EDITING_DOMAIN(sc) + " createEditingDomain() {");
		sc.add(ADAPTER_FACTORY(sc) + " adapterFactory = new " + adapterFactoryProviderClassName + "().getAdapterFactory();");
		sc.addLineBreak();
		sc.add(BASIC_COMMAND_STACK(sc) + " commandStack = new " + BASIC_COMMAND_STACK(sc) + "();");
		sc.addLineBreak();
		
		sc.addComment("Register resource factories (esp. for additional extensions).");
		sc.add("new " + metaInformationClassName + "().registerResourceFactory();");
		sc.addLineBreak();
		sc.add("return new " + ADAPTER_FACTORY_EDITING_DOMAIN(sc) + "(adapterFactory, commandStack, new " + LINKED_HASH_MAP(sc) + "<" + RESOURCE(sc) + ", Boolean>());");
		
		sc.add("}");
		sc.addLineBreak();
	}
}