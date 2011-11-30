package org.emftext.sdk.codegen.resource.generators.util;

import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.PLATFORM;

import org.emftext.sdk.OptionManager;
import org.emftext.sdk.codegen.annotations.SyntaxDependent;
import org.emftext.sdk.codegen.composites.JavaComposite;
import org.emftext.sdk.codegen.parameters.ArtifactParameter;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.generators.JavaBaseGenerator;
import org.emftext.sdk.concretesyntax.OptionTypes;

@SyntaxDependent
public class RuntimeUtilGenerator extends JavaBaseGenerator<ArtifactParameter<GenerationContext>> {
	
	@Override
	public void generateJavaContents(JavaComposite sc) {

        sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
        
		sc.addJavadoc(
			"This utility class provides methods to obtain information about the current runtime, " + 
			"for example whether Eclipse is available or not."
		);
        sc.add("public class " + getResourceClassName() + " {");
        sc.addLineBreak();
   		addMethods(sc);
		sc.add("}");
    }

	private void addMethods(JavaComposite sc) {
		boolean removeEclipseDependentCode = OptionManager.INSTANCE.getBooleanOptionValue(getContext().getConcreteSyntax(), OptionTypes.REMOVE_ECLIPSE_DEPENDENT_CODE);
		addIsEclipsePlatformAvailableMethod(sc);
		addLogMethods(sc);
        
		if (!removeEclipseDependentCode) {
        	addIsEclipsePlatformRunningMethod(sc);
		}
	}

	private void addIsEclipsePlatformAvailableMethod(JavaComposite sc) {
		sc.addJavadoc(
			"Checks whether the class " + PLATFORM +
			"is available on the classpath. This can be used to " +
			"determine if Eclipse is available in the current runtime " +
			"environment."
		);
		sc.add("public boolean isEclipsePlatformAvailable() {");
		sc.add("try {");
		sc.add("Class.forName(\"" + PLATFORM + "\");");
		sc.add("return true;");
		sc.add("} catch (ClassNotFoundException cnfe) {");
		sc.add("}");
		sc.add("return false;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addIsEclipsePlatformRunningMethod(JavaComposite sc) {
		sc.addJavadoc(
			"Checks whether the Eclipse platform is running."
		);
		sc.add("public boolean isEclipsePlatformRunning() {");
		sc.add("if (!isEclipsePlatformAvailable()) {");
		sc.add("return false;");
		sc.add("}");
		sc.add("return " + PLATFORM + ".isRunning();");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addLogMethods(JavaComposite sc) {
		boolean removeEclipseDependentCode = OptionManager.INSTANCE.getBooleanOptionValue(getContext().getConcreteSyntax(), OptionTypes.REMOVE_ECLIPSE_DEPENDENT_CODE);

		addLogMethod(sc, "Error", removeEclipseDependentCode);
		addLogMethod(sc, "Warning", removeEclipseDependentCode);
	}

	private void addLogMethod(
			JavaComposite sc,
			String type,
			boolean removeEclipseDependentCode) {
		sc.addJavadoc(
			"Logs the given " + type.toLowerCase() + ". If Eclipse is running, the " + type.toLowerCase() + " is added to " +
			"the error log otherwise the message is printed to System.err."
		);
		sc.add("public void log" + type + "(String message, Throwable exception) {");
		if (!removeEclipseDependentCode) {
			sc.add("if (isEclipsePlatformAvailable()) {");
			sc.add(pluginActivatorClassName + ".log" + type + "(message, exception);");
			sc.add("return;");
			sc.add("}");
		}
		sc.add("System.err.println(message);");
		sc.add("if (exception != null) {");
		sc.add("exception.printStackTrace();");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}
}
