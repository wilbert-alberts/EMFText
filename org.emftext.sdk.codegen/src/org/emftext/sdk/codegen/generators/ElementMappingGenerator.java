/*******************************************************************************
 * Copyright (c) 2006-2009 
 * Software Technology Group, Dresden University of Technology
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany 
 *      - initial API and implementation
 ******************************************************************************/
package org.emftext.sdk.codegen.generators;

import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.composites.StringComposite;

public class ElementMappingGenerator extends JavaBaseGenerator {

	public ElementMappingGenerator() {
		super();
	}

	private ElementMappingGenerator(GenerationContext context) {
		super(context, EArtifact.ELEMENT_MAPPING);
	}

	public boolean generateJavaContents(StringComposite sc) {
		
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.add("// A basic implementation of the IElementMapping interface.");
		sc.add("//");
		sc.add("// @param <ReferenceType> the type of the reference that can be mapped to");
		sc.add("//");
		sc.add("public class " + getResourceClassName() + "<ReferenceType> implements " + getClassNameHelper().getI_ELEMENT_MAPPING() + "<ReferenceType> {");
		sc.addLineBreak();
		sc.add("private final ReferenceType target;");
		sc.add("private String identifier;");
		sc.add("private String warning;");
		sc.addLineBreak();
		sc.add("public " + getResourceClassName() + "(String identifier, ReferenceType target, String warning) {");
		sc.add("super();");
		sc.add("this.target = target;");
		sc.add("this.identifier = identifier;");
		sc.add("this.warning = warning;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public ReferenceType getTargetElement() {");
		sc.add("return target;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public String getIdentifier() {");
		sc.add("return identifier;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public String getWarning() {");
		sc.add("return warning;");
		sc.add("}");
		sc.add("}");
		return true;
	}

	public IGenerator newInstance(GenerationContext context) {
		return new ElementMappingGenerator(context);
	}
}
