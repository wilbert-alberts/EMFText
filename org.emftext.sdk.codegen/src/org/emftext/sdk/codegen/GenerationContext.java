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
package org.emftext.sdk.codegen;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emftext.sdk.Constants;
import org.emftext.sdk.IPluginDescriptor;
import org.emftext.sdk.TextResourcePlugins;
import org.emftext.sdk.codegen.composites.StringComposite;
import org.emftext.sdk.codegen.generators.code_completion.helpers.Expectation;
import org.emftext.sdk.codegen.util.ConcreteSyntaxUtil;
import org.emftext.sdk.concretesyntax.CompleteTokenDefinition;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;
import org.emftext.sdk.concretesyntax.CsString;
import org.emftext.sdk.concretesyntax.Definition;
import org.emftext.sdk.concretesyntax.OptionTypes;
import org.emftext.sdk.concretesyntax.Placeholder;
import org.emftext.sdk.finders.GenClassFinder;

/**
 * A GenerationContext provides all information that is needed by the 
 * generators. This includes a resolved concrete syntax, 
 * a package name for parser and printer, a package name for resolvers 
 * (reference and token resolvers) and a resource target folder. Furthermore,
 * the context collects information about the generation process as it
 * is executed.
 * 
 * @see org.emftext.sdk.codegen.creators.ResourcePluginContentCreator
 * 
 * @author Sven Karol (Sven.Karol@tu-dresden.de)
 */
public abstract class GenerationContext {
	
	private static final String ANTRL_GRAMMAR_FILE_EXTENSION = ".g";

	private static final String DEFAULT_NEW_ICON_NAME = "default_new_icon.gif";
	private static final String DEFAULT_EDITOR_ICON_NAME = "editor_icon.gif";
	private static final String DEFAULT_ICON_DIR = "icons";
	public static final String DEFAULT_CSS_DIR = "css";
	private static final String SCHEMA_DIR = "schema";
	public static final String HOVER_STYLE_FILENAME = "hover_style.css";
	
	private final NameUtil nameUtil = new NameUtil();

	private final ConcreteSyntax concreteSyntax;
	private final IProblemCollector problemCollector;
	private final GenClassFinder genClassFinder = new GenClassFinder();
	
	private ConcreteSyntaxUtil csUtil = new ConcreteSyntaxUtil();

	private String licenceText;
	private boolean antlrGrammarHasChanged;
	
	/**
	 * A counter that is used to indicate the next free id in 'idMap'.
	 */
	private int idCounter = 0;
	
	/**
	 * A map that contains the terminal elements of the syntax specification
	 * (keywords and placeholders) to the name of the field that represents
	 * them.
	 */
	private Map<EObject, String> idMap = new LinkedHashMap<EObject, String>();
	
	/**
	 * A map that contains names of fields representing terminals and their
	 * follow set. This map is used to create the code that links terminals
	 * and the potential next elements (i.e., their follow set).
	 */
	private Map<String, Set<Expectation>> followSetMap = new LinkedHashMap<String, Set<Expectation>>();

	private int featureCounter = 0;
	private Map<GenFeature, String> eFeatureToConstantNameMap = new LinkedHashMap<GenFeature, String>();

	public GenerationContext(ConcreteSyntax concreteSyntax, IProblemCollector problemCollector) {
		if (concreteSyntax == null) {
			throw new IllegalArgumentException("A concrete syntax must be specified!");
		}
		this.concreteSyntax = concreteSyntax;
		this.problemCollector = problemCollector;
	}

	/**
	 * @return The concrete syntax to be processed and which is 
	 * assumed to contain all resolved information.
	 */
	public ConcreteSyntax getConcreteSyntax() {
		return concreteSyntax;
	}
	
	public abstract File getProjectFolder(IPluginDescriptor plugin);

	/**
	 * Returns the actual file which contains the CS specification.
	 */
	public File getConcreteSyntaxFile() {
		Resource resource = concreteSyntax.eResource();
		URI uri = resource.getURI();
		File file = new File(uri.toFileString());
		return file;
	}
	
	public File getOutputFolder(IPluginDescriptor plugin) {
		return new File(getProjectFolder(plugin).getAbsolutePath() + File.separator + "bin");
	}

	public String getPluginName(IPluginDescriptor plugin) {
		return plugin.getName(concreteSyntax);
	}

	public String getPackageName(ArtifactDescriptor artifact) {
		return getPackageName(artifact, concreteSyntax);
	}

	public String getPackageName(ArtifactDescriptor artifact, ConcreteSyntax syntax) {
		return nameUtil.getPackageName(syntax, artifact);
	}

	/**
	 * Returns the name of the package where token and reference resolvers 
	 * must go to.
	 */
	public String getResolverPackageName() {
		return csUtil.getResolverPackageName(concreteSyntax);
	}
	
	public IProblemCollector getProblemCollector() {
		return problemCollector;
	}
	
	public String getCapitalizedConcreteSyntaxName(ConcreteSyntax syntax) {
		return csUtil.getCapitalizedConcreteSyntaxName(syntax);
	}
	
	public String getCapitalizedConcreteSyntaxName() {
		return getCapitalizedConcreteSyntaxName(getConcreteSyntax());
	}
	
	public boolean isImportedWithSyntaxReference(GenFeature genFeature) {
		ConcreteSyntax containingSyntax = genClassFinder.getContainingSyntax(concreteSyntax, genFeature.getGenClass());
		if (containingSyntax == null) {
			return false;
		}
		if (containingSyntax == concreteSyntax) {
			return false;
		}
		return true;
	}

	public String getLexerName() {
		return getCapitalizedConcreteSyntaxName() + "Lexer";
	}

	public String getParserName() {
		return getCapitalizedConcreteSyntaxName() + "Parser";
	}

	/**
	 * Returns the qualified class name of the reference resolver for the given reference. If
	 * inImportedSyntax is set to true, the name of the resolver in the imported resource plug-in
	 * is returned if the rule containing the reference is imported. Otherwise (if the containing
	 * rule is not imported or inImportedSyntax is false), the name of the resolver in the currrent
	 * resource plug-in is returned.
	 * 
	 * @param proxyReference
	 * @param inImportedSyntax
	 * 
	 * @return the fully qualified reference resolver class name
	 */
	public String getQualifiedReferenceResolverClassName(GenFeature proxyReference, boolean inImportedSyntax) {
		return getResolverPackageName(proxyReference, inImportedSyntax) + "." + csUtil.getReferenceResolverClassName(proxyReference);
	}

	private String getResolverPackageName(GenFeature proxyReference, boolean inImportedSyntax) {
		return csUtil.getResolverPackageName(getConcreteSyntax(), proxyReference, inImportedSyntax);
	}

	public String getReferenceResolverAccessor(GenFeature genFeature) {
		String prefix = "getReferenceResolverSwitch() == null ? null : ";
		return prefix + "getReferenceResolverSwitch().get" + csUtil.getReferenceResolverClassName(genFeature) + "()";
	}

	public void addGetMetaInformationMethod(StringComposite sc) {
		sc.add("public " + getQualifiedClassName(TextResourceArtifacts.META_INFORMATION) + " getMetaInformation() {");
		sc.add("return new " + getQualifiedClassName(TextResourceArtifacts.META_INFORMATION) + "();");
		sc.add("}");
		sc.addLineBreak();
	}

	/**
	 * Returns the name of the project that contains the concrete 
	 * syntax definition. Note that this is usually NOT the text
	 * resource project.
	 */
	public abstract String getSyntaxProjectName();

	/**
	 * Returns the path of the concrete syntax definition
	 * file relative to the project that contains the
	 * file.
	 */
	public abstract String getProjectRelativePathToSyntaxFile();

	// TODO mseifert: delete these artifact specific methods and use the generic methods
	// for EArtifact instead. Maybe we need to add folders as distinct artifacts.
	public File getIconsDir() {
		return new File(getProjectFolder(TextResourcePlugins.RESOURCE_PLUGIN).getAbsolutePath() + File.separator + DEFAULT_ICON_DIR);
	}

	public File getCSSDir() {
		return new File(getProjectFolder(TextResourcePlugins.RESOURCE_PLUGIN).getAbsolutePath() + File.separator + DEFAULT_CSS_DIR);
	}

	public File getSchemaFolder() {
		return new File(getProjectFolder(TextResourcePlugins.RESOURCE_PLUGIN).getAbsolutePath() + File.separator + SCHEMA_DIR);
	}
	
	public File getNewIconFile() {
		return new File(getIconsDir().getAbsolutePath() + File.separator + DEFAULT_NEW_ICON_NAME);
	}

	public File getEditorIconFile() {
		return new File(getIconsDir().getAbsolutePath() + File.separator + DEFAULT_EDITOR_ICON_NAME);
	}

	public File getHoverStyleFile() {
		return new File(getCSSDir().getAbsolutePath() + File.separator + HOVER_STYLE_FILENAME);
	}

	public String getProjectRelativeNewIconPath() {
		// it is OK to use slashes here, because this path is put into
		// the plugin.xml
		return "/" + DEFAULT_ICON_DIR + "/" + DEFAULT_NEW_ICON_NAME;
	}

	public boolean isGenerateTestActionEnabled() {
		return OptionManager.INSTANCE.getBooleanOptionValue(getConcreteSyntax(), OptionTypes.GENERATE_TEST_ACTION);
	}

	public String getPackagePath(ArtifactDescriptor artifact) {
		OptionTypes overrideOption = artifact.getOverrideOption();
		boolean doOverride = overrideOption == null || OptionManager.INSTANCE.getBooleanOptionValue(getConcreteSyntax(), overrideOption);
		File targetFolder = getSourceFolder(artifact.getPlugin(), doOverride);
		IPath csPackagePath = new Path(getPackageName(artifact).replaceAll("\\.","/"));
		String targetFolderPath = targetFolder.getAbsolutePath();
		String packagePath = targetFolderPath + File.separator + csPackagePath + File.separator;
		return packagePath;
	}

	public File getSourceFolder(IPluginDescriptor plugin, boolean doOverride) {
		return csUtil.getSourceFolder(getConcreteSyntax(), doOverride, getProjectFolder(plugin).getAbsolutePath());
	}

	public File getResolverFile(GenFeature proxyReference) {
		OptionTypes overrideOption = OptionTypes.OVERRIDE_REFERENCE_RESOLVERS;
		boolean doOverride = overrideOption == null || OptionManager.INSTANCE.getBooleanOptionValue(getConcreteSyntax(), overrideOption);
		File resolverFile = new File(getSourceFolder(TextResourcePlugins.RESOURCE_PLUGIN, doOverride) + File.separator + getResolverPackagePath() + File.separator + csUtil.getReferenceResolverClassName(proxyReference) + Constants.JAVA_FILE_EXTENSION);
		return resolverFile;
	}

	private IPath getResolverPackagePath() {
		return csUtil.getResolverPackagePath(getConcreteSyntax());
	}

	public File getTokenResolverFile(ConcreteSyntax syntax, CompleteTokenDefinition tokenDefinition) {
		OptionTypes overrideOption = OptionTypes.OVERRIDE_TOKEN_RESOLVERS;
		boolean doOverride = overrideOption == null || OptionManager.INSTANCE.getBooleanOptionValue(getConcreteSyntax(), overrideOption);
		return new File(getSourceFolder(TextResourcePlugins.RESOURCE_PLUGIN, doOverride).getAbsolutePath() + File.separator + getResolverPackagePath() + File.separator + csUtil.getTokenResolverClassName(syntax, tokenDefinition) + Constants.JAVA_FILE_EXTENSION);
	}

	public File getANTLRGrammarFile() {
		String antlrName = getCapitalizedConcreteSyntaxName();
		String packagePath = getPackagePath(TextResourceArtifacts.ANTLR_GRAMMAR);
  		File antlrFile = new File(packagePath + antlrName + ANTRL_GRAMMAR_FILE_EXTENSION);
		return antlrFile;
	}

	public String getDefaultResolverDelegateName() {
		return csUtil.getDefaultResolverDelegateName(getConcreteSyntax());
	}
	
	public String getQualifiedDefaultResolverDelegateName() {
		return getResolverPackageName() + "." + getDefaultResolverDelegateName();
	}

	public String getClassName(ArtifactDescriptor artifact) {
		return getClassName(artifact, getConcreteSyntax());
	}

	public String getClassName(ArtifactDescriptor artifact, ConcreteSyntax syntax) {
		return artifact.getClassNamePrefix() + getCapitalizedConcreteSyntaxName(syntax) + artifact.getClassNameSuffix();
	}

	public String getQualifiedClassName(ArtifactDescriptor artifact) {
		return getPackageName(artifact) + "." + getClassName(artifact);
	}

	public String getQualifiedClassName(ArtifactDescriptor artifact, ConcreteSyntax syntax) {
		return getPackageName(artifact, syntax) + "." + getClassName(artifact, syntax);
	}

	public File getFile(ArtifactDescriptor artifact) {
		return new File(getPackagePath(artifact) + getClassName(artifact) + Constants.JAVA_FILE_EXTENSION);
	}

	public String getLicenceText() {
		return this.licenceText;
	}

	public void setLicenceText(String text) {
		this.licenceText = text;
	}

	// TODO mseifert: this does not belong here
	public String getBuilderID() {
		String pluginID = TextResourcePlugins.RESOURCE_PLUGIN.getName(getConcreteSyntax());
		return pluginID + ".builder";
	}

	// TODO mseifert: this does not belong here
	public String getNatureID() {
		String pluginID = TextResourcePlugins.RESOURCE_PLUGIN.getName(getConcreteSyntax());
		return pluginID + ".nature";
	}

	public String getID(EObject expectedElement) {
		if (!idMap.containsKey(expectedElement)) {
			idMap.put(expectedElement, "TERMINAL_" + idCounter);
			idCounter++;
		}
		return idMap.get(expectedElement);
	}

	public void addToFollowSetMap(Definition definition, Set<Expectation> expectations) {
		// only terminals are important here
		if (definition instanceof Placeholder) {
			GenFeature feature = ((Placeholder) definition).getFeature();
			if (feature == ConcreteSyntaxUtil.ANONYMOUS_GEN_FEATURE) {
				return;
			}
			followSetMap.put(getID(definition), expectations);
		} else if (definition instanceof CsString) {
			followSetMap.put(getID(definition), expectations);
		}
	}

	public Map<EObject, String> getIdMap() {
		return idMap;
	}

	public Map<String, Set<Expectation>> getFollowSetMap() {
		return followSetMap;
	}

	public String getFeatureConstantFieldName(GenFeature genFeature) {
		if (!eFeatureToConstantNameMap.keySet().contains(genFeature)) {
			String featureConstantName = "FEATURE_" + featureCounter;
			featureCounter++;
			eFeatureToConstantNameMap.put(genFeature, featureConstantName);
		}
		return eFeatureToConstantNameMap.get(genFeature);
	}

	public Map<GenFeature, String> getFeatureToConstantNameMap() {
		return eFeatureToConstantNameMap;
	}

	/**
	 * The result of this method indicates whether generating 
	 * the ANTLR commons plug-in is required or not.
	 * 
	 * @return
	 */
	public abstract boolean getGenerateANTLRPlugin();

	public void setANTLRGrammarHasChanged() {
		antlrGrammarHasChanged = true;
	}

	public boolean getANTLRGrammarHasChanged() {
		return antlrGrammarHasChanged;
	}
}
