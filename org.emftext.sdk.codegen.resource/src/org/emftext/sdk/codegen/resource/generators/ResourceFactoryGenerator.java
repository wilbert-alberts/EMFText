/*******************************************************************************
 * Copyright (c) 2006-2010 
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
package org.emftext.sdk.codegen.resource.generators;

import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.RESOURCE;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.URI;

import org.emftext.sdk.codegen.ICodeGenerationComponent;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.composites.JavaComposite;
import org.emftext.sdk.codegen.generators.GeneratorProvider;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.TextResourceArtifacts;

/**
 * Generates a factory for the TextResource which loads and stores generated resources. 
 * It can be registered to the EMF resource framework.
 * 
 * @see org.emftext.sdk.codegen.resource.generators.TextResourceGenerator
 * @see org.emftext.runtime.resource.ITextResource
 */
public class ResourceFactoryGenerator extends JavaBaseGenerator<Object> {
	
	public final static GeneratorProvider<GenerationContext, Object> PROVIDER = 
		new GeneratorProvider<GenerationContext, Object>(new ResourceFactoryGenerator());

	private ResourceFactoryGenerator() {
		super();
	}

	private ResourceFactoryGenerator(ICodeGenerationComponent parent, GenerationContext context) {
		super(parent, context, TextResourceArtifacts.RESOURCE_FACTORY);
	}

	@Override
	public void generateJavaContents(JavaComposite sc) {
		
        sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
        
        sc.add("public class " + getResourceClassName()+ " implements " + RESOURCE + ".Factory {");
        sc.addLineBreak();
        
		sc.add("public " + getResourceClassName() + "() {");
		sc.add("super();");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("public " + RESOURCE + " createResource(" + URI + " uri) {");
		sc.add("return new " + textResourceClassName + "(uri);");
		sc.add("}");
		
		sc.add("}");
    }

	public IGenerator<GenerationContext, Object> newInstance(ICodeGenerationComponent parent, GenerationContext context, Object parameters) {
		return new ResourceFactoryGenerator(parent, context);
	}
}
