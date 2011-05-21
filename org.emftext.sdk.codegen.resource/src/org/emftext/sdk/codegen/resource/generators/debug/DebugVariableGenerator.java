package org.emftext.sdk.codegen.resource.generators.debug;

import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.DEBUG_EXCEPTION;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.I_DEBUG_TARGET;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.I_STATUS;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.I_VALUE;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.I_VARIABLE;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.STATUS;

import org.emftext.sdk.codegen.composites.JavaComposite;
import org.emftext.sdk.codegen.parameters.ArtifactParameter;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.generators.JavaBaseGenerator;

public class DebugVariableGenerator extends JavaBaseGenerator<ArtifactParameter<GenerationContext>> {

	public void generateJavaContents(JavaComposite sc) {
		if (!getContext().isDebugSupportEnabled()) {
			generateEmptyClass(sc);
			return;
		}
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.add("public class " + getResourceClassName() + " extends " + debugElementClassName + " implements " + I_VARIABLE + " {");
		sc.addLineBreak();
		addFields(sc);
		addConstructor(sc);
		addMethods(sc);
		sc.add("}");
	}

	private void addMethods(JavaComposite sc) {
		addSupportsValueModificationMethod(sc);
		addSetValueMethod1(sc);
		addSetValueMethod2(sc);
		addVerifyValueMethod1(sc);
		addVerifyValueMethod2(sc);
		addGetValueMethod(sc);
		addGetNameMethod(sc);
		addGetReferenceTypeNameMethod(sc);
		addHasValueChangedMethod(sc);
	}

	private void addFields(JavaComposite sc) {
		sc.add("private String name;");
		sc.add("private " + I_VALUE + " value;");
		sc.add("private String referenceTypeName;");
		sc.addLineBreak();
	}

	private void addConstructor(JavaComposite sc) {
		sc.add("public " + getResourceClassName() + "(" + I_DEBUG_TARGET + " debugTarget, String name, " + I_VALUE + " value, String referenceTypeName) {");
		sc.add("super(debugTarget);");
		sc.add("this.name = name;");
		sc.add("this.value = value;");
		sc.add("this.referenceTypeName = referenceTypeName;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addSupportsValueModificationMethod(JavaComposite sc) {
		sc.add("public boolean supportsValueModification() {");
		sc.add("return false;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addSetValueMethod2(JavaComposite sc) {
		sc.add("public void setValue(String expression) throws " + DEBUG_EXCEPTION + " {");
		sc.add("throw new " + DEBUG_EXCEPTION + "(new " + STATUS + "(" + I_STATUS + ".ERROR, " + pluginActivatorClassName + ".PLUGIN_ID, \"Can't set variable.\"));");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addSetValueMethod1(JavaComposite sc) {
		sc.add("public void setValue(" + I_VALUE + " value) throws " + DEBUG_EXCEPTION + " {");
		sc.add("throw new " + DEBUG_EXCEPTION + "(new " + STATUS + "(" + I_STATUS + ".ERROR, " + pluginActivatorClassName + ".PLUGIN_ID, \"Can't set variable.\"));");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addVerifyValueMethod2(JavaComposite sc) {
		sc.add("public boolean verifyValue(String expression) throws " + DEBUG_EXCEPTION + " {");
		sc.add("throw new " + DEBUG_EXCEPTION + "(new " + STATUS + "(" + I_STATUS + ".ERROR, " + pluginActivatorClassName + ".PLUGIN_ID, \"Can't set variable.\"));");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addVerifyValueMethod1(JavaComposite sc) {
		sc.add("public boolean verifyValue(" + I_VALUE + " value) throws " + DEBUG_EXCEPTION + " {");
		sc.add("throw new " + DEBUG_EXCEPTION + "(new " + STATUS + "(" + I_STATUS + ".ERROR, " + pluginActivatorClassName + ".PLUGIN_ID, \"Can't set variable.\"));");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetValueMethod(JavaComposite sc) {
		sc.add("public " + I_VALUE + " getValue() throws " + DEBUG_EXCEPTION + " {");
		sc.add("return value;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetNameMethod(JavaComposite sc) {
		sc.add("public String getName() throws " + DEBUG_EXCEPTION + " {");
		sc.add("return name;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetReferenceTypeNameMethod(JavaComposite sc) {
		sc.add("public String getReferenceTypeName() throws " + DEBUG_EXCEPTION + " {");
		sc.add("return referenceTypeName;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addHasValueChangedMethod(JavaComposite sc) {
		sc.add("public boolean hasValueChanged() throws " + DEBUG_EXCEPTION + " {");
		// TODO
		sc.add("return true;");
		sc.add("}");
		sc.addLineBreak();
	}
}