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
package org.emftext.sdk.codegen.generators;

import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.composites.StringComposite;

public class TokenResolveResultGenerator extends JavaBaseGenerator {

	public TokenResolveResultGenerator() {
		super();
	}

	private TokenResolveResultGenerator(GenerationContext context) {
		super(context, EArtifact.TOKEN_RESOLVE_RESULT);
	}

	public boolean generateJavaContents(StringComposite sc) {
		
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.add("// A basic implementation of the ITokenResolveResult interface.");
		sc.add("//");
		sc.add("public class " + getResourceClassName() + " implements " + getClassNameHelper().getI_TOKEN_RESOLVE_RESULT() + " {");
		sc.addLineBreak();
		sc.add("private String errorMessage;");
		sc.add("private Object resolvedToken;");
		sc.addLineBreak();
		sc.add("public " + getResourceClassName() + "() {");
		sc.add("super();");
		sc.add("clear();");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public String getErrorMessage() {");
		sc.add("return errorMessage;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public Object getResolvedToken() {");
		sc.add("return resolvedToken;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void setErrorMessage(String message) {");
		sc.add("errorMessage = message;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void setResolvedToken(Object resolvedToken) {");
		sc.add("this.resolvedToken = resolvedToken;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void clear() {");
		sc.add("errorMessage = \"Can't resolve token.\";");
		sc.add("resolvedToken = null;");
		sc.add("}");
		sc.add("}");
		return true;
	}

	public IGenerator newInstance(GenerationContext context) {
		return new TokenResolveResultGenerator(context);
	}
}
