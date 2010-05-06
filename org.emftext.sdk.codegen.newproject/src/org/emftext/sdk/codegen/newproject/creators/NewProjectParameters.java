package org.emftext.sdk.codegen.newproject.creators;

public class NewProjectParameters {
	
	private String metamodelName;
	private String namespaceUri;
	private String namespacePrefix;
	private String projectName;
	private String basePackage;
	private String ecoreFile;
	private String syntaxFile;
	private String genmodelFile;
	private String metamodelFolder;
	private String syntaxName;
	private String srcFolder;

	public NewProjectParameters(String metamodelName, String namespaceUri,
			String namespacePrefix, String projectName, String basePackage,
			String ecoreFile, String syntaxFile, String genmodelFile,
			String metamodelFolder, String syntaxName, String srcFolder) {
		super();
		this.metamodelName = metamodelName;
		this.namespaceUri = namespaceUri;
		this.namespacePrefix = namespacePrefix;
		this.projectName = projectName;
		this.basePackage = basePackage;
		this.ecoreFile = ecoreFile;
		this.syntaxFile = syntaxFile;
		this.genmodelFile = genmodelFile;
		this.metamodelFolder = metamodelFolder;
		this.syntaxName = syntaxName;
		this.srcFolder = srcFolder;
	}

	public String getNamespaceUri() {
		return namespaceUri;
	}

	public void setNamespaceUri(String namespaceUri) {
		this.namespaceUri = namespaceUri;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getNamespacePrefix() {
		return namespacePrefix;
	}

	public void setNamespacePrefix(String namespacePrefix) {
		this.namespacePrefix = namespacePrefix;
	}

	public String getName() {
		return metamodelName;
	}

	public void setName(String name) {
		this.metamodelName = name;
	}

	public String getEcoreFile() {
		return ecoreFile;
	}

	public void setEcoreFile(String ecoreFile) {
		this.ecoreFile = ecoreFile;
	}

	public String getSyntaxFile() {
		return syntaxFile;
	}

	public void setSyntaxFile(String syntaxFile) {
		this.syntaxFile = syntaxFile;
	}

	public String getGenmodelFile() {
		return genmodelFile;
	}

	public void setGenmodelFile(String genmodelFile) {
		this.genmodelFile = genmodelFile;
	}

	public String getMetamodelFolder() {
		return metamodelFolder;
	}

	public void setMetamodelFolder(String metamodelFolder) {
		this.metamodelFolder = metamodelFolder;
	}

	public String getSyntaxName() {
		return syntaxName;
	}

	public void setSyntaxName(String syntaxName) {
		this.syntaxName = syntaxName;
	}

	public String getSrcFolder() {
		return srcFolder;
	}

	public void setSrcFolder(String srcFolder) {
		this.srcFolder = srcFolder;
	}

	public String getProjectName() {
		return projectName;
	}
}