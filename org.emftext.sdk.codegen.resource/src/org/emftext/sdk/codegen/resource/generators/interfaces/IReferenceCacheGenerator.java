package org.emftext.sdk.codegen.resource.generators.interfaces;

import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.OBJECT;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.STRING;

import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.composites.JavaComposite;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.TextResourceArtifacts;
import org.emftext.sdk.codegen.resource.generators.JavaBaseGenerator;

public class IReferenceCacheGenerator extends JavaBaseGenerator<Object> {

	public IReferenceCacheGenerator() {
		super();
	}

	private IReferenceCacheGenerator(GenerationContext context) {
		super(context, TextResourceArtifacts.I_REFERENCE_CACHE);
	}

	public IGenerator<GenerationContext, Object> newInstance(GenerationContext context, Object parameters) {
		return new IReferenceCacheGenerator(context);
	}

	public boolean generateJavaContents(JavaComposite sc) {
		
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.add("public interface " + getResourceClassName() + " {");
		sc.add("public " + OBJECT + " get(" + STRING + " identifier);");
		sc.add("public void put(" + STRING + " identifier, " + OBJECT + " target);");
		sc.add("}");
		return true;
	}
}