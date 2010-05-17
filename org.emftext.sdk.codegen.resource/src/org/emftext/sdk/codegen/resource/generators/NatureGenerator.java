package org.emftext.sdk.codegen.resource.generators;

import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.CORE_EXCEPTION;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.I_COMMAND;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.I_PROJECT;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.I_PROJECT_DESCRIPTION;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.I_PROJECT_NATURE;

import org.emftext.sdk.codegen.ICodeGenerationComponent;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.composites.JavaComposite;
import org.emftext.sdk.codegen.composites.StringComposite;
import org.emftext.sdk.codegen.generators.GeneratorProvider;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.TextResourceArtifacts;
import org.emftext.sdk.codegen.util.NameUtil;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;

public class NatureGenerator extends JavaBaseGenerator<Object> {

	public final static GeneratorProvider<GenerationContext, Object> PROVIDER = 
		new GeneratorProvider<GenerationContext, Object>(new NatureGenerator());

	private final NameUtil nameUtil = new NameUtil();

	private NatureGenerator() {
		super();
	}

	private NatureGenerator(ICodeGenerationComponent parent, GenerationContext context) {
		super(parent, context, TextResourceArtifacts.NATURE);
	}

	public IGenerator<GenerationContext, Object> newInstance(ICodeGenerationComponent parent, GenerationContext context, Object parameters) {
		return new NatureGenerator(parent, context);
	}

	@Override
	public void generateJavaContents(JavaComposite sc) {
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.add("public class " + getResourceClassName() + " implements " + I_PROJECT_NATURE + " {");
		sc.addLineBreak();
		addFields(sc);
		addMethods(sc);
		sc.add("}");
	}

	private void addFields(JavaComposite sc) {
		ConcreteSyntax syntax = context.getConcreteSyntax();
		sc.add("public static final String NATURE_ID = \"" + nameUtil.getNatureID(syntax) + "\";");
		sc.addLineBreak();
		sc.add("private " + I_PROJECT + " project;");
		sc.addLineBreak();
		sc.addJavadoc("the IDs of all builders, IDs of additional builders can be added here");
		sc.add("public final static String[] BUILDER_IDS = {" + builderAdapterClassName + ".BUILDER_ID};");
		sc.addLineBreak();
	}

	private void addMethods(JavaComposite sc) {
		addActivateMethod(sc);
		addDeactivateMethod(sc);
		addHasNatureMethod(sc);
		addConfigureMethod(sc);
		addDeconfigureMethod(sc);
		addGetProjectMethod(sc);
		addSetProjectMethod(sc);
	}

	private void addSetProjectMethod(StringComposite sc) {
		sc.add("public void setProject(" + I_PROJECT + " project) {");
		sc.add("this.project = project;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetProjectMethod(StringComposite sc) {
		sc.add("public " + I_PROJECT + " getProject() {");
		sc.add("return project;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addDeconfigureMethod(StringComposite sc) {
		sc.add("public void deconfigure() throws " + CORE_EXCEPTION + " {");
		sc.add(I_PROJECT_DESCRIPTION + " description = getProject().getDescription();");
		sc.add(I_COMMAND + "[] commands = description.getBuildSpec();");
		sc.add(I_COMMAND + "[] newCommands = commands;");
		sc.add("for (int j = 0; j < BUILDER_IDS.length; j++) {");
		sc.add("for (int i = 0; i < newCommands.length; ++i) {");
		sc.add("if (newCommands[i].getBuilderName().equals(BUILDER_IDS[j])) {");
		sc.add(I_COMMAND + "[] tempCommands = new " + I_COMMAND + "[newCommands.length - 1];");
		sc.add("System.arraycopy(newCommands, 0, tempCommands, 0, i);");
		sc.add("System.arraycopy(newCommands, i + 1, tempCommands, i, newCommands.length - i - 1);");
		sc.add("newCommands = tempCommands;");
		sc.add("break;");
		sc.add("}");
		sc.add("}");
		sc.add("}");
		sc.add("if (newCommands != commands) {");
		sc.add("description.setBuildSpec(newCommands);");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addConfigureMethod(StringComposite sc) {
		sc.add("public void configure() throws " + CORE_EXCEPTION + " {");
		sc.add(I_PROJECT_DESCRIPTION + " desc = project.getDescription();");
		sc.add(I_COMMAND + "[] commands = desc.getBuildSpec();");
		sc.addLineBreak();
		sc.add("for (int i = 0; i < commands.length; ++i) {");
		sc.add("if (commands[i].getBuilderName().equals(" + builderAdapterClassName + ".BUILDER_ID)) {");
		sc.add("return;");
		sc.add("}");
		sc.add("}");
		sc.add(I_COMMAND + "[] newCommands = commands;");
		sc.add("outer: for (int j = 0; j < BUILDER_IDS.length; j++) {");
		sc.add("for (int i = 0; i < commands.length; ++i) {");
		sc.add("if (commands[i].getBuilderName().equals(BUILDER_IDS[j])) {");
		sc.add("continue outer;");
		sc.add("}");
		sc.add("}");
		sc.add(I_COMMAND + "[] tempCommands = new " + I_COMMAND + "[newCommands.length + 1];");
		sc.add("System.arraycopy(newCommands, 0, tempCommands, 0, newCommands.length);");
		sc.add(I_COMMAND + " command = desc.newCommand();");
		sc.add("command.setBuilderName(BUILDER_IDS[j]);");
		sc.add("tempCommands[tempCommands.length - 1] = command;");
		sc.add("newCommands = tempCommands;");
		sc.add("}");
		sc.add("if (newCommands != commands) {");
		sc.add("desc.setBuildSpec(newCommands);");
		sc.add("project.setDescription(desc, null);");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addHasNatureMethod(StringComposite sc) {
		sc.add("public static boolean hasNature(" + I_PROJECT + " project) {");
		sc.add("try {");
		sc.add(I_PROJECT_DESCRIPTION + " description = project.getDescription();");
		sc.add("String[] natures = description.getNatureIds();");
		sc.add("for (int i = 0; i < natures.length; ++i) {");
		sc.add("if (NATURE_ID.equals(natures[i])) {");
		sc.add("return true;");
		sc.add("}");
		sc.add("}");
		sc.add("} catch (" + CORE_EXCEPTION + " e) {");
		sc.add("}");
		sc.add("return false;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addDeactivateMethod(JavaComposite sc) {
		sc.add("public static void deactivate(" + I_PROJECT + " project) {");
		sc.add("try {");
		sc.add(I_PROJECT_DESCRIPTION + " description = project.getDescription();");
		sc.add("String[] natures = description.getNatureIds();");
		sc.addLineBreak();
		sc.add("for (int i = 0; i < natures.length; ++i) {");
		sc.add("if (NATURE_ID.equals(natures[i])) {");
		sc.addComment("Remove the nature");
		sc.add("String[] newNatures = new String[natures.length - 1];");
		sc.add("System.arraycopy(natures, 0, newNatures, 0, i);");
		sc.add("System.arraycopy(natures, i + 1, newNatures, i, natures.length - i - 1);");
		sc.add("description.setNatureIds(newNatures);");
		sc.add("project.setDescription(description, null);");
		sc.add("return;");
		sc.add("}");
		sc.add("}");
		sc.add("} catch (" + CORE_EXCEPTION + " e) {");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addActivateMethod(JavaComposite sc) {
		sc.add("public static void activate(" + I_PROJECT + " project) {");
		sc.add("try {");
		sc.add(I_PROJECT_DESCRIPTION + " description = project.getDescription();");
		sc.add("String[] natures = description.getNatureIds();");
		sc.addLineBreak();
		sc.add("for (int i = 0; i < natures.length; ++i) {");
		sc.add("if (NATURE_ID.equals(natures[i])) {");
		sc.addComment("already active");
		sc.add("return;");
		sc.add("}");
		sc.add("}");
		sc.addComment("Add the nature");
		sc.add("String[] newNatures = new String[natures.length + 1];");
		sc.add("System.arraycopy(natures, 0, newNatures, 0, natures.length);");
		sc.add("newNatures[natures.length] = NATURE_ID;");
		sc.add("description.setNatureIds(newNatures);");
		sc.add("project.setDescription(description, null);");
		sc.add("} catch (" + CORE_EXCEPTION + " e) {");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}
}
